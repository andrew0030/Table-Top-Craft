package andrews.table_top_craft.tile_entities.render;

import andrews.table_top_craft.events.DrawScreenEvent;
import andrews.table_top_craft.game_logic.chess.pieces.BasePiece;
import andrews.table_top_craft.objects.blocks.ChessPieceFigureBlock;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.tile_entities.model.piece_figure.ChessPieceFigureStandModel;
import andrews.table_top_craft.util.Color;
import andrews.table_top_craft.util.NBTColorSaving;
import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.TTCRenderTypes;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
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

public class ChessPieceFigureTileEntityRenderer implements BlockEntityRenderer<ChessPieceFigureBlockEntity>
{
    // Dynamic Texture
    private static final NativeImage image = new NativeImage(NativeImage.Format.RGBA, 1, 1, true);
    private static final DynamicTexture texture = new DynamicTexture(image);
    private static ResourceLocation resourceLocation = null;
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
                poseStack.mulPose(Vector3f.YP.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTicks));
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
        poseStack.mulPose(Vector3f.YN.rotationDegrees(rotation * 22.5F));
        // Continuously rotates the piece if it was enabled
        if (blockEntity.getRotateChessPieceFigure())
            poseStack.mulPose(Vector3f.YN.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTicks));
        // We invert the model because Minecraft renders shit inside out.
        poseStack.scale(3.0F, -3.0F, -3.0F);

        if(blockEntity.hasLevel())
        {
            float scale = (float) blockEntity.getPieceScale();
            poseStack.scale(scale, scale, scale);
        }

        poseStack.pushPose();
        RenderType type = TTCRenderTypes.getChessPieceSolid(resourceLocation);
        type.setupRenderState();
        BufferHelpers.setupRender(RenderSystem.getShader(), lightU, lightV);
        ShaderInstance shaderinstance = RenderSystem.getShader();
        // We get the colors the Piece should have
        float red = NBTColorSaving.getRed(blockEntity.getPieceColor()) / 255F;
        float green = NBTColorSaving.getGreen(blockEntity.getPieceColor()) / 255F;
        float blue = NBTColorSaving.getBlue(blockEntity.getPieceColor()) / 255F;
        // We set the colors
        RenderSystem.setShaderColor(red, green, blue, 1.0F);
        BufferHelpers.updateColor(shaderinstance);
        poseStack.pushPose();
        if (shaderinstance.MODEL_VIEW_MATRIX != null)
        {
            if (isInGui || isHeldOrHead)
            {
                Matrix4f mat4f = RenderSystem.getModelViewMatrix().copy();
                PoseStack stk = new PoseStack();
                stk.last().pose().multiply(mat4f);
                stk.last().pose().multiply(initialMatrix);
                stk.translate(8 * 0.0625F, 2 * 0.0625F, 8 * 0.0625F);
                if (blockEntity.getRotateChessPieceFigure())
                    stk.mulPose(Vector3f.YN.rotationDegrees(Minecraft.getInstance().player.tickCount + partialTicks));
                // The scale of the Pieces, if rendered in on Head, Third Person Left/Right we make them slightly smaller to fit the ones in the Level
                stk.scale(isHeldOrHead ? 3 : 4, isHeldOrHead ? -3 : -4, isHeldOrHead ? -3 : -4);
                shaderinstance.MODEL_VIEW_MATRIX.set(stk.last().pose());
            }
            else
            {
                shaderinstance.MODEL_VIEW_MATRIX.set(poseStack.last().pose());
            }
        }
        if (shaderinstance.PROJECTION_MATRIX != null)
            shaderinstance.PROJECTION_MATRIX.set(RenderSystem.getProjectionMatrix());
        switch (blockEntity.getPieceSet())
        {
            case 1:
                switch (blockEntity.getPieceType())
                {
                    case 1 -> {
                        VertexBuffer pawnBuffer = DrawScreenEvent.pawnBuffer;
                        BufferHelpers.draw(pawnBuffer, shaderinstance);
                    }
                    case 2 -> {
                        VertexBuffer rookBuffer = DrawScreenEvent.rookBuffer;
                        BufferHelpers.draw(rookBuffer, shaderinstance);
                    }
                    case 3 -> {
                        VertexBuffer bishopBuffer = DrawScreenEvent.bishopBuffer;
                        BufferHelpers.draw(bishopBuffer, shaderinstance);
                    }
                    case 4 -> {
                        VertexBuffer knightBuffer = DrawScreenEvent.knightBuffer;
                        BufferHelpers.draw(knightBuffer, shaderinstance);
                    }
                    case 5 -> {
                        VertexBuffer kingBuffer = DrawScreenEvent.kingBuffer;
                        BufferHelpers.draw(kingBuffer, shaderinstance);
                    }
                    case 6 -> {
                        VertexBuffer queenBuffer = DrawScreenEvent.queenBuffer;
                        BufferHelpers.draw(queenBuffer, shaderinstance);
                    }
                }
                break;
            case 2:
                switch (blockEntity.getPieceType())
                {
                    case 1 -> {
                        VertexBuffer classicPawnBuffer = DrawScreenEvent.classicPawnBuffer;
                        BufferHelpers.draw(classicPawnBuffer, shaderinstance);
                    }
                    case 2 -> {
                        VertexBuffer classicRookBuffer = DrawScreenEvent.classicRookBuffer;
                        BufferHelpers.draw(classicRookBuffer, shaderinstance);
                    }
                    case 3 -> {
                        VertexBuffer classicBishopBuffer = DrawScreenEvent.classicBishopBuffer;
                        BufferHelpers.draw(classicBishopBuffer, shaderinstance);
                    }
                    case 4 -> {
                        VertexBuffer classicKnightBuffer = DrawScreenEvent.classicKnightBuffer;
                        BufferHelpers.draw(classicKnightBuffer, shaderinstance);
                    }
                    case 5 -> {
                        VertexBuffer classicKingBuffer = DrawScreenEvent.classicKingBuffer;
                        BufferHelpers.draw(classicKingBuffer, shaderinstance);
                    }
                    case 6 -> {
                        VertexBuffer classicQueenBuffer = DrawScreenEvent.classicQueenBuffer;
                        BufferHelpers.draw(classicQueenBuffer, shaderinstance);
                    }
                }
                break;
            case 3:
                switch (blockEntity.getPieceType())
                {
                    case 1 -> {
                        VertexBuffer pandorasCreaturesPawnBuffer = DrawScreenEvent.pandorasCreaturesPawnBuffer;
                        BufferHelpers.draw(pandorasCreaturesPawnBuffer, shaderinstance);
                    }
                    case 2 -> {
                        VertexBuffer pandorasCreaturesRookBuffer = DrawScreenEvent.pandorasCreaturesRookBuffer;
                        BufferHelpers.draw(pandorasCreaturesRookBuffer, shaderinstance);
                    }
                    case 3 -> {
                        VertexBuffer pandorasCreaturesBishopBuffer = DrawScreenEvent.pandorasCreaturesBishopBuffer;
                        BufferHelpers.draw(pandorasCreaturesBishopBuffer, shaderinstance);
                    }
                    case 4 -> {
                        VertexBuffer pandorasCreaturesKnightBuffer = DrawScreenEvent.pandorasCreaturesKnightBuffer;
                        BufferHelpers.draw(pandorasCreaturesKnightBuffer, shaderinstance);
                    }
                    case 5 -> {
                        VertexBuffer pandorasCreaturesKingBuffer = DrawScreenEvent.pandorasCreaturesKingBuffer;
                        BufferHelpers.draw(pandorasCreaturesKingBuffer, shaderinstance);
                    }
                    case 6 -> {
                        VertexBuffer pandorasCreaturesQueenBuffer = DrawScreenEvent.pandorasCreaturesQueenBuffer;
                        BufferHelpers.draw(pandorasCreaturesQueenBuffer, shaderinstance);
                    }
                }
        }
        poseStack.popPose();
        poseStack.popPose();
        type.clearRenderState();
        poseStack.popPose();
    }
}