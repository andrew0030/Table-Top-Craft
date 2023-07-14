package andrews.table_top_craft.tile_entities.render;

import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.objects.blocks.ChessPieceFigureBlock;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.model.piece_figure.ChessPieceFigureStandModel;
import andrews.table_top_craft.util.*;
import andrews.table_top_craft.util.shader_compat.ShaderCompatHandler;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix4f;

public class ChessPieceFigureTileEntityRenderer implements BlockEntityRenderer<ChessPieceFigureBlockEntity>
{
    // Dynamic Texture
    private static final NativeImage image = new NativeImage(NativeImage.Format.RGBA, 1, 1, true);
    private static final DynamicTexture texture = new DynamicTexture(image);
    private static final ResourceLocation resourceLocation;
    // Shader Compat texture
    public static final ResourceLocation SHADER_COMPAT_WHITE = new ResourceLocation(Reference.MODID, "textures/tile/compat/full_white.png");
    // Chess Piece Stand texture and model
    public static final ResourceLocation CHESS_PIECE_FIGURE_TEXTURE = new ResourceLocation(Reference.MODID, "textures/tile/chess_piece_figure/chess_piece_figure.png");
    private static ChessPieceFigureStandModel chessPieceFigureStandModel;
    private Color color = new Color(0, 0, 0);

    static
    {
        // We create the dummy texture
        image.setPixelRGBA(0, 0, 16777215);
        texture.upload();
        resourceLocation = Minecraft.getInstance().getTextureManager().register("table_top_craft_dummy", texture);
    }

    public ChessPieceFigureTileEntityRenderer(BlockEntityRendererProvider.Context context)
    {
        chessPieceFigureStandModel = new ChessPieceFigureStandModel(context.bakeLayer(ChessPieceFigureStandModel.CHESS_PIECE_FIGURE_LAYER));
    }

    @Override
    public void render(ChessPieceFigureBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay)
    {
        if(blockEntity.getPieceName() != null && blockEntity.getPieceName().equals("andrew_"))
        {
            int tickCount = Minecraft.getInstance().player.tickCount;
            int value = (tickCount % 180) * 2;
            color = color.fromHSV(value, 1.0F, 1.0F);
            blockEntity.setPieceColor(color.getRed() + "/" + color.getGreen() + "/" + color.getBlue() + "/255");
        }
        renderChessPieceFigure(blockEntity, poseStack, bufferSource, false, false, partialTicks, packedLight, packedOverlay);
    }

    public static void renderChessPieceFigure(ChessPieceFigureBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource bufferSource, boolean isInGui, boolean isHeldOrHead, float partialTicks, int packedLight, int packedOverlay)
    {
        // Renders the Stand for the "Chess Piece Figure" block
        Matrix4f initialMatrix = poseStack.last().pose();
        if(!blockEntity.hasLevel())
        {
            poseStack.pushPose();
            poseStack.translate(0.5D, 1.5D, 0.5D);
            poseStack.scale(1.0F, -1.0F, -1.0F);
            if (blockEntity.getRotateChessPieceFigure() && isInGui)
                poseStack.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTicks));
            VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.entitySolid(CHESS_PIECE_FIGURE_TEXTURE));
            chessPieceFigureStandModel.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }

        // Gets the rotation the Figure should have when rendered in the world
        int rotation = 0;
        if (blockEntity.hasLevel())
        {
            BlockState blockstate = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos());
            if (blockstate.getBlock() instanceof ChessPieceFigureBlock)
            {
                rotation = blockstate.getValue(ChessPieceFigureBlock.ROTATION);
            }
        }
        // The light for the Figure
        int lightU = LightTexture.block(packedLight);
        int lightV = LightTexture.sky(packedLight);

        poseStack.pushPose();
        // Moves the piece to the center and onto the Base Plate
        poseStack.translate(8 * 0.0625F, 2 * 0.0625F, 8 * 0.0625F);
        // Rotates the piece based on the rotation
        poseStack.mulPose(Axis.YN.rotationDegrees(rotation * 22.5F));
        // Continuously rotates the piece if it was enabled
        if (blockEntity.getRotateChessPieceFigure())
            poseStack.mulPose(Axis.YN.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTicks));
        // We invert the model because Minecraft renders shit inside out.
        poseStack.scale(3.0F, -3.0F, -3.0F);

        if(blockEntity.getPieceName() != null && blockEntity.getPieceName().equals("Lyzantra"))
        {
            poseStack.translate(0.0D, -0.2D * blockEntity.getPieceScale(), 0.0D);
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        }

        if(blockEntity.hasLevel())
        {
            float scale = (float) blockEntity.getPieceScale();
            poseStack.scale(scale, scale, scale);
        }

        // We get the colors the Piece should have
        float red = NBTColorSaving.getRed(blockEntity.getPieceColor()) / 255F;
        float green = NBTColorSaving.getGreen(blockEntity.getPieceColor()) / 255F;
        float blue = NBTColorSaving.getBlue(blockEntity.getPieceColor()) / 255F;
        // Piece Set and Type
        BasePiece.PieceModelSet set = BasePiece.PieceModelSet.get(blockEntity.getPieceSet());
        BasePiece.PieceType piece = BasePiece.PieceType.get(blockEntity.getPieceType());

        if (ShaderCompatHandler.isShaderActive()) // Shader Compat Mode
        {
            poseStack.pushPose();
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.entitySolid(SHADER_COMPAT_WHITE));
            if (isInGui || isHeldOrHead)
            {
                poseStack.scale(isHeldOrHead ? 1.3F : 1.4F, isHeldOrHead ? 1.3F : 1.4F, isHeldOrHead ? 1.3F : 1.4F);
                if (blockEntity.getPieceName() != null && blockEntity.getPieceName().equals("Lyzantra"))
                {
                    poseStack.translate(0.0D, 0.05D, 0.0D);
                }
            }
            DrawScreenHelper.CHESS_PIECE_MODEL.render(poseStack, consumer, piece, set, red, green, blue, packedLight);
            poseStack.popPose();
        } else { // Performance Mode
            poseStack.pushPose();
            RenderType type = TTCRenderTypes.getChessPieceSolid(resourceLocation);
            type.setupRenderState();
            ShaderInstance shaderinstance = RenderSystem.getShader();
            if (shaderinstance.PROJECTION_MATRIX != null)
                shaderinstance.PROJECTION_MATRIX.set(RenderSystem.getProjectionMatrix());
            BufferHelpers.setupRender(RenderSystem.getShader(), lightU, lightV);
            // We set the colors
            BufferHelpers.updateColor(shaderinstance, new float[]{red, green, blue, 1.0F});
            poseStack.pushPose();
            if (shaderinstance.MODEL_VIEW_MATRIX != null)
            {
                if (isInGui || isHeldOrHead)
                {
                    Matrix4f mat4f = new Matrix4f(RenderSystem.getModelViewMatrix());
                    PoseStack stk = new PoseStack();
                    stk.last().pose().mul(mat4f);
                    stk.last().pose().mul(initialMatrix);
                    stk.translate(8 * 0.0625F, 2 * 0.0625F, 8 * 0.0625F);
                    if (blockEntity.getRotateChessPieceFigure())
                        stk.mulPose(Axis.YN.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTicks));
                    if(blockEntity.getPieceName() != null && blockEntity.getPieceName().equals("Lyzantra"))
                    {
                        stk.translate(0.0D, 0.85D, 0.0D);
                        stk.mulPose(Axis.ZN.rotationDegrees(180));
                    }
                    // The scale of the Pieces, if rendered in on Head, Third Person Left/Right we make them slightly smaller to fit the ones in the Level
                    stk.scale(isHeldOrHead ? 3 : 4, isHeldOrHead ? -3 : -4, isHeldOrHead ? -3 : -4);
                    shaderinstance.MODEL_VIEW_MATRIX.set(stk.last().pose());
                }
                else
                {
                    shaderinstance.MODEL_VIEW_MATRIX.set(poseStack.last().pose());
                }
            }

            VertexBuffer pawnBuffer = DrawScreenHelper.getBuffer(set, piece);

            // setup render state
            TTCRenderTypes.getChessPieceSolid(resourceLocation).setupRenderState();
            shaderinstance.apply();
            BufferHelpers.draw(pawnBuffer);
            // clear render state
            VertexBuffer.unbind();
            shaderinstance.clear();
            type.clearRenderState();

            poseStack.popPose();
            poseStack.popPose();
        }
        poseStack.popPose();
    }
}