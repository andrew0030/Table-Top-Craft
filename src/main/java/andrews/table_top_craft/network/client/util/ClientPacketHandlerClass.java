package andrews.table_top_craft.network.client.util;

import andrews.table_top_craft.animation.system.core.AdvancedAnimationState;
import andrews.table_top_craft.animation.system.core.Animation;
import andrews.table_top_craft.animation.system.core.BasicKeyframe;
import andrews.table_top_craft.animation.system.core.KeyframeGroup;
import andrews.table_top_craft.animation.system.core.bulders.AnimationBuilder;
import andrews.table_top_craft.animation.system.core.bulders.EasingBuilder;
import andrews.table_top_craft.animation.system.core.types.EasingTypes;
import andrews.table_top_craft.animation.system.core.types.TransformTypes;
import andrews.table_top_craft.animation.system.core.types.util.EasingType;
import andrews.table_top_craft.block_entities.ChessBlockEntity;
import andrews.table_top_craft.game_logic.chess.board.Board;
import andrews.table_top_craft.network.client.*;
import andrews.table_top_craft.objects.blocks.ChessBlock;
import andrews.table_top_craft.particles.options.ChessShatterParticleOptions;
import andrews.table_top_craft.registry.TTCParticles;
import andrews.table_top_craft.screens.chess.menus.ChessPawnPromotionScreen;
import andrews.table_top_craft.screens.chess.menus.ChessPieceSelectionScreen;
import andrews.table_top_craft.block_entities.ConnectFourBlockEntity;
import andrews.table_top_craft.util.NBTColorSaving;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class ClientPacketHandlerClass
{
    public static void handleOpenChessPieceSelectionPacket(MessageClientOpenChessPieceSelectionScreen msg, Supplier<NetworkEvent.Context> ctx)
    {
        BlockPos pos = msg.pos;
        boolean isStandardSetUnlocked = msg.isStandardSetUnlocked;
        boolean isClassicSetUnlocked = msg.isClassicSetUnlocked;
        boolean isPandorasCreaturesSetUnlocked = msg.isPandorasCreaturesSetUnlocked;
        if(Minecraft.getInstance().player.getLevel().getBlockEntity(pos) != null && Minecraft.getInstance().player.getLevel().getBlockEntity(pos) instanceof ChessBlockEntity chessBlockEntity)
        {
            Minecraft.getInstance().setScreen(new ChessPieceSelectionScreen(chessBlockEntity, isStandardSetUnlocked, isClassicSetUnlocked, isPandorasCreaturesSetUnlocked));
        }
    }

    public static void handleOpenChessPromotionPacket(MessageClientOpenChessPromotionScreen msg, Supplier<NetworkEvent.Context> ctx)
    {
        BlockPos pos = msg.pos;
        boolean isWhite = msg.isWhite;
        if(Minecraft.getInstance().player.getLevel().getBlockEntity(pos) != null && Minecraft.getInstance().player.getLevel().getBlockEntity(pos) instanceof ChessBlockEntity chessBlockEntity)
        {
            Minecraft.getInstance().setScreen(new ChessPawnPromotionScreen(chessBlockEntity, isWhite));
        }
    }

    public static void handlePlayChessTimerSoundPacket(MessageClientPlayChessTimerSound msg, Supplier<NetworkEvent.Context> ctx)
    {
        BlockPos pos = msg.pos;
        byte id = msg.id;
        if(Minecraft.getInstance().level != null)
        {
            switch (id)
            {
                case 0 -> Minecraft.getInstance().level.playLocalSound(pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 1.0F, 0.6F, false);
                case 1 -> {
                    Minecraft.getInstance().level.playLocalSound(pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 1.0F, 0.6F, false);
                    Minecraft.getInstance().level.playLocalSound(pos, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.BLOCKS, 3.0F, 1.2F, false);
                }
            }
        }
    }

    public static void handlePlayChessParticlesPacket(MessageClientChessParticles msg, Supplier<NetworkEvent.Context> ctx)
    {
        BlockPos pos = msg.pos;
        byte destCord = msg.destCord;
        boolean isWhite = msg.isWhite;
        float xSpeed = msg.xSpeed;
        float ySpeed = msg.ySpeed;
        float zSpeed = msg.zSpeed;
        if(Minecraft.getInstance().level != null)
        {
            double xOffset = 0.0;
            double zOffset = 0.0;
            switch (Minecraft.getInstance().level.getBlockState(pos).getValue(ChessBlock.FACING))
            {
                case NORTH -> {
                    xOffset = (destCord % 8) * 0.125 + 0.0625;
                    zOffset = (destCord / 8) * 0.125 + 0.0625;
                }
                case SOUTH -> {
                    xOffset = 1 - (destCord % 8) * 0.125 - 0.0625;
                    zOffset = 1 - (destCord / 8) * 0.125 - 0.0625;
                }
                case WEST -> {
                    zOffset = 1 - (destCord % 8) * 0.125 - 0.0625;
                    xOffset = (destCord / 8) * 0.125 + 0.0625;
                }
                case EAST -> {
                    zOffset = (destCord % 8) * 0.125 + 0.0625;
                    xOffset = 1 - (destCord / 8) * 0.125 - 0.0625;
                }
            }

            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ChessBlockEntity blockEntity)
            {
                String color = isWhite ? blockEntity.getWhitePiecesColor() : blockEntity.getBlackPiecesColor();
                int red = NBTColorSaving.getRed(color);
                int green = NBTColorSaving.getGreen(color);
                int blue = NBTColorSaving.getBlue(color);

                for(int i = 0; i < 10; i++)
                    Minecraft.getInstance().level.addParticle(TTCParticles.TINY_POOF.get(), pos.getX() + xOffset, pos.getY() + (0.0625F * 13.5F), pos.getZ() + zOffset, 0, 0, 0);
                for(int i = 0; i < 14; i++)
                    Minecraft.getInstance().level.addParticle(new ChessShatterParticleOptions(red, green, blue), pos.getX() + xOffset, pos.getY() + (0.0625F * 12.5F), pos.getZ() + zOffset, xSpeed, ySpeed, zSpeed);
            }
        }
    }

    public static void handleChessAnimationPacket(MessageClientChessAnimationState msg, Supplier<NetworkEvent.Context> ctx)
    {
        BlockPos pos = msg.pos;
        byte actionType = msg.actionType;
        if(Minecraft.getInstance().player.getLevel().getBlockEntity(pos) instanceof ChessBlockEntity chessBlockEntity)
        {
            if(actionType == 0)
            {
                chessBlockEntity.placedState.start(chessBlockEntity.getTicksExisted());
                // We reset the state to null
                chessBlockEntity.moveState = null;
                chessBlockEntity.clearMoveTransitionsCache();
                chessBlockEntity.setCachedPiece(null);
            }
            else if(actionType == 1)
            {
                chessBlockEntity.placedState.stop();
                chessBlockEntity.placedState.interpolateAndStart(0.2F, EasingBuilder.type(EasingType.EASE_OUT_QUAD).argument(0.2F).build(), false, chessBlockEntity.getTicksExisted());
            }
            else if(actionType == 2)
            {
                chessBlockEntity.currentCord = msg.currentCord;
                chessBlockEntity.destCord = msg.destCord;

                Animation animation = ClientPacketHandlerClass.generateAnimation(chessBlockEntity, chessBlockEntity.currentCord, chessBlockEntity.destCord);
                chessBlockEntity.moveState = new AdvancedAnimationState(new AtomicReference<>(animation));
                chessBlockEntity.moveState.start(chessBlockEntity.getTicksExisted());
            }
        }
    }

    public static void handleConnectFourAnimationPacket(MessageClientConnectFourAnimationState msg, Supplier<NetworkEvent.Context> ctx)
    {
        BlockPos pos = msg.pos;
        byte destCord = msg.destCord;
        if(Minecraft.getInstance().player.getLevel().getBlockEntity(pos) instanceof ConnectFourBlockEntity blockEntity)
        {
            // We update the value of the falling piece on the clients
            blockEntity.movingPiece = destCord;
            // We get the height of the piece to adjust the Animation accordingly
            int heightMod = destCord % 6;
            // We create the Animation
            float animLength = 1.0F - (heightMod * 0.1F);
            AnimationBuilder builder = AnimationBuilder.withLength(animLength);
            builder.addAnimation("root", new KeyframeGroup(TransformTypes.POSITION,
                    new BasicKeyframe(0.2F, KeyframeAnimations.posVec(0F, 13F - (heightMod * 2), 0F), EasingTypes.LINEAR),
                    new BasicKeyframe(animLength, KeyframeAnimations.posVec(0F, 0F, 0F), EasingBuilder.type(EasingType.EASE_IN_BOUNCE).argument(0.2F - (0.2F * (heightMod * 0.15F))).build())));
            builder.addAnimation("root", new KeyframeGroup(TransformTypes.ROTATION,
                    new BasicKeyframe(0.0F, KeyframeAnimations.degreeVec(0F, 90F, 0F), EasingTypes.LINEAR),
                    new BasicKeyframe(0.3F, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
            builder.addAnimation("root", new KeyframeGroup(TransformTypes.SCALE,
                    new BasicKeyframe(0.0F, KeyframeAnimations.scaleVec(0.1F, 0.1F, 0.1F), EasingTypes.LINEAR),
                    new BasicKeyframe(0.3F, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.LINEAR)));
            Animation animation = builder.build();
            blockEntity.moveState = new AdvancedAnimationState(new AtomicReference<>(animation));
            blockEntity.moveState.start(blockEntity.getTicksExisted());
        }
    }

    public static Animation generateAnimation(ChessBlockEntity blockEntity, byte currentCord, byte destCord)
    {
        Board board = blockEntity.getBoard();
        int startX = currentCord % 8;
        int startY = currentCord / 8;
        int destX = destCord % 8;
        int destY =  destCord / 8;

        int deltaY = startY - destY;
        deltaY = board.getTile(currentCord).getPiece().getPieceColor().isWhite() ? deltaY : -deltaY;
        int deltaX = startX - destX;
        deltaX = board.getTile(currentCord).getPiece().getPieceColor().isWhite() ? -deltaX : deltaX;

        float time = 1F;

        switch (board.getTile(currentCord).getPiece().getPieceType())
        {
            case PAWN -> {
                time = (deltaY == 2 || deltaY == -2) ? 1.0F : 0.75F;
                // En Passant Move
                if((board.getTile(currentCord).getPiece().getPieceColor().isWhite() && startY == 3) || (board.getTile(currentCord).getPiece().getPieceColor().isBlack() && startY == 4)) { // En Passant start position
                    if(deltaX == -1 || deltaX == 1) { // Make sure it was a diagonal move
                        if (board.getTile(destCord).getPiece() == null) { // If there was no piece on the diagonal it was no normal attack move
                            time = 1.0F;
                            AnimationBuilder builder = AnimationBuilder.withLength(time);
                            builder.addAnimation("moved", new KeyframeGroup(TransformTypes.POSITION,
                                    new BasicKeyframe(0, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                                    new BasicKeyframe(0.2F, KeyframeAnimations.posVec(0F, 1F, 0F), EasingTypes.LINEAR),
                                    new BasicKeyframe((time / 4) * 2.5F, KeyframeAnimations.posVec(deltaX * 2F, 1F, deltaY * 2F), EasingTypes.LINEAR),
                                    new BasicKeyframe((time / 4) * 3, KeyframeAnimations.posVec(deltaX * 2F, 1F, deltaY * -0.2F), EasingTypes.EASE_OUT_CUBIC),
                                    new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX * 2F, 0F, deltaY * 2F), EasingTypes.LINEAR)));
                            builder.addAnimation("moved", new KeyframeGroup(TransformTypes.ROTATION,
                                    new BasicKeyframe(0.0F, KeyframeAnimations.degreeVec(0F, 0.0F, 0F), EasingTypes.LINEAR),
                                    new BasicKeyframe((time / 4) * 2, KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                                    new BasicKeyframe((time / 4) * 2.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR),
                                    new BasicKeyframe((time / 4) * 3, KeyframeAnimations.degreeVec(-60F, 0F, 0F), EasingTypes.LINEAR),
                                    new BasicKeyframe(time, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                            builder.addAnimation("affected", new KeyframeGroup(TransformTypes.POSITION,
                                    new BasicKeyframe((time / 4) * 2.7F, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                                    new BasicKeyframe(time, KeyframeAnimations.posVec(0F, 8F, 8F), EasingTypes.LINEAR)));
                            builder.addAnimation("affected", new KeyframeGroup(TransformTypes.ROTATION,
                                    new BasicKeyframe((time / 4) * 2.7F, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR),
                                    new BasicKeyframe(time, KeyframeAnimations.degreeVec(-300F, 0F, 0F), EasingTypes.LINEAR)));
                            builder.addAnimation("affected", new KeyframeGroup(TransformTypes.SCALE,
                                    new BasicKeyframe((time / 4) * 3.5F, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.LINEAR),
                                    new BasicKeyframe(time, KeyframeAnimations.scaleVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                            return builder.build();
                        }
                    }
                }
                // Normal Moves
                AnimationBuilder builder = AnimationBuilder.withLength(time);
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.POSITION,
                        new BasicKeyframe(0, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe(0.2F, KeyframeAnimations.posVec(0F, 1F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 3) * 2, KeyframeAnimations.posVec(deltaX * 2F, board.getTile(destCord).getPiece() != null ? 2.5F : 1F, deltaY * 2F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX * 2F, 0F, deltaY * 2F), EasingTypes.LINEAR)));
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.ROTATION,
                        new BasicKeyframe(0.0F, KeyframeAnimations.degreeVec(0F, 0.0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4) * 2, KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                builder.addAnimation("affected", new KeyframeGroup(TransformTypes.SCALE,
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.scaleVec(1F, 0.1F, 1F), EasingTypes.LINEAR)));
                return builder.build();
            }
            case BISHOP -> {
                time = Math.abs(deltaX) == 1 ? 0.75F : (Math.abs(deltaX) > 3 ? 1.25F : time);
                AnimationBuilder builder = AnimationBuilder.withLength(time);
                if(Math.abs(deltaX) >= 4 && board.getTile(destCord).getPiece() != null) { // If the distance is long enough we do a YEET attack
                    builder.addAnimation("moved", new KeyframeGroup(TransformTypes.POSITION,
                            new BasicKeyframe(0, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(0.2F, KeyframeAnimations.posVec(0F, 1F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 3) * 2, KeyframeAnimations.posVec(deltaX * 2F, 1F, deltaY * 2F), EasingTypes.EASE_IN_CUBIC),
                            new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX * 2F, 0F, deltaY * 2F), EasingTypes.LINEAR)));
                    builder.addAnimation("moved", new KeyframeGroup(TransformTypes.ROTATION,
                            new BasicKeyframe(0, KeyframeAnimations.degreeVec(0F, 0.0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4), KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4) * 2.5F, KeyframeAnimations.degreeVec(getRookSlamRotation(deltaX, deltaY, 40F), getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4) * 2.8F, KeyframeAnimations.degreeVec(getRookSlamRotation(deltaX, deltaY, -60F), getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.POSITION,
                            new BasicKeyframe((time / 4) * 2.5F, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX == 0 ? 0 : deltaX > 0 ? -10 : 10, 7F, deltaY == 0 ? 0F : deltaY > 0 ? -10 : 10), EasingTypes.LINEAR)));
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.ROTATION,
                            new BasicKeyframe((time / 4) * 2.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.degreeVec(deltaY != 0 ? -300F : 0F, 0F, deltaX != 0 ? -300F : 0F), EasingTypes.LINEAR)));
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.SCALE,
                            new BasicKeyframe((time / 4) * 3.5F, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.scaleVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                    return builder.build();
                }
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.POSITION,
                        new BasicKeyframe(0, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe(0.2F, KeyframeAnimations.posVec(0F, 1F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 3) * 2, KeyframeAnimations.posVec(deltaX * 2F, board.getTile(destCord).getPiece() != null ? 2.5F : 1F, deltaY * 2F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX * 2F, 0F, deltaY * 2F), EasingTypes.LINEAR)));
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.ROTATION,
                        new BasicKeyframe(0.0F, KeyframeAnimations.degreeVec(0F, 0.0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4), KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                builder.addAnimation("affected", new KeyframeGroup(TransformTypes.SCALE,
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.scaleVec(1F, 0.1F, 1F), EasingTypes.LINEAR)));
                return builder.build();
            }
            case KNIGHT -> {
                AnimationBuilder builder = AnimationBuilder.withLength(time);
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.POSITION,
                        new BasicKeyframe(0.0F, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4) * 2, KeyframeAnimations.posVec(0F, 2.5F, 0F), EasingTypes.CATMULLROM),
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.posVec(deltaX * 2F, 2.5F, deltaY * 2F), EasingTypes.CATMULLROM),
                        new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX * 2F, 0F, deltaY * 2F), EasingTypes.CATMULLROM)));
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.ROTATION,
                        new BasicKeyframe(0.0F, KeyframeAnimations.degreeVec(0F, 0.0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4) * 2, KeyframeAnimations.degreeVec(0F, getKnightRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.degreeVec(0F, getKnightRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                builder.addAnimation("affected", new KeyframeGroup(TransformTypes.SCALE,
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.scaleVec(1F, 0.1F, 1F), EasingTypes.LINEAR)));
                return builder.build();
            }
            case KING -> {
                time = (deltaX == 2 || deltaX == -2) ? 1.0F : 0.75F;
                AnimationBuilder builder = AnimationBuilder.withLength(time);
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.POSITION,
                        new BasicKeyframe(0.0F, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe(0.2F, KeyframeAnimations.posVec(0F, 1F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4) * 3F, KeyframeAnimations.posVec(deltaX * 2F, board.getTile(destCord).getPiece() != null ? 2.5F : 1F, deltaY * 2F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX * 2F, 0F, deltaY * 2F), EasingTypes.LINEAR)));
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.ROTATION,
                        new BasicKeyframe(0.0F, KeyframeAnimations.degreeVec(0F, 0.0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4) * 2, KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                if(board.getTile(destCord).getPiece() != null)
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.SCALE,
                            new BasicKeyframe((time / 4) * 3, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.scaleVec(1F, 0.1F, 1F), EasingTypes.LINEAR)));
                if(deltaX == 2 || deltaX == -2)// Casting
                {
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.POSITION,
                            new BasicKeyframe(0.0F, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4) * 2, KeyframeAnimations.posVec(0F, 2.5F, 0F), EasingTypes.CATMULLROM),
                            new BasicKeyframe((time / 4) * 3, KeyframeAnimations.posVec(-deltaX * (destX == 2 ? 3F : 2F), 2.5F, 0F), EasingTypes.CATMULLROM),
                            new BasicKeyframe(time, KeyframeAnimations.posVec(-deltaX * (destX == 2 ? 3F : 2F), 0F, 0F), EasingTypes.CATMULLROM)));
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.ROTATION,
                            new BasicKeyframe(0.0F, KeyframeAnimations.degreeVec(0F, 0.0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4) * 2, KeyframeAnimations.degreeVec(0F, getKingRotation(-deltaX, deltaY), 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4) * 3, KeyframeAnimations.degreeVec(0F, getKingRotation(-deltaX, deltaY), 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                }
                return builder.build();
            }
            case QUEEN -> {
                time = (Math.abs(deltaX) == 1 || Math.abs(deltaY) == 1) ? 0.75F : (Math.abs(deltaX) > 3 || Math.abs(deltaY) > 3) ? 1.25F : time;
                AnimationBuilder builder = AnimationBuilder.withLength(time);
                if(((Math.abs(deltaX) >= 4 && Math.abs(deltaY) == 0) ||
                    (Math.abs(deltaY) >= 4 && Math.abs(deltaX) == 0) ||
                    (Math.abs(deltaX) >= 4 && Math.abs(deltaY) >= 4)) &&
                    board.getTile(destCord).getPiece() != null)
                { // If the distance is long enough we do a YEET attack
                    builder.addAnimation("moved", new KeyframeGroup(TransformTypes.POSITION,
                            new BasicKeyframe(0, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(0.2F, KeyframeAnimations.posVec(0F, 1F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 3) * 2, KeyframeAnimations.posVec(deltaX * 2F, 1F, deltaY * 2F), EasingTypes.EASE_IN_CUBIC),
                            new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX * 2F, 0F, deltaY * 2F), EasingTypes.LINEAR)));
                    builder.addAnimation("moved", new KeyframeGroup(TransformTypes.ROTATION,
                            new BasicKeyframe(0, KeyframeAnimations.degreeVec(0F, 0.0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4), KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4) * 2.5F, KeyframeAnimations.degreeVec(getRookSlamRotation(deltaX, deltaY, 40F), getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4) * 2.8F, KeyframeAnimations.degreeVec(getRookSlamRotation(deltaX, deltaY, -60F), getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.POSITION,
                            new BasicKeyframe((time / 4) * 2.5F, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX == 0 ? 0 : deltaX > 0 ? -10 : 10, 7F, deltaY == 0 ? 0F : deltaY > 0 ? -10 : 10), EasingTypes.LINEAR)));
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.ROTATION,
                            new BasicKeyframe((time / 4) * 2.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.degreeVec(deltaY != 0 ? -300F : 0F, 0F, deltaX != 0 ? -300F : 0F), EasingTypes.LINEAR)));
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.SCALE,
                            new BasicKeyframe((time / 4) * 3.5F, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.scaleVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                    return builder.build();
                }
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.POSITION,
                        new BasicKeyframe(0, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe(0.2F, KeyframeAnimations.posVec(0F, 1F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 3) * 2, KeyframeAnimations.posVec(deltaX * 2F, board.getTile(destCord).getPiece() != null ? 2.5F : 1F, deltaY * 2F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX * 2F, 0F, deltaY * 2F), EasingTypes.LINEAR)));
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.ROTATION,
                        new BasicKeyframe(0.0F, KeyframeAnimations.degreeVec(0F, 0.0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4), KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                builder.addAnimation("affected", new KeyframeGroup(TransformTypes.SCALE,
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.scaleVec(1F, 0.1F, 1F), EasingTypes.LINEAR)));
                return builder.build();
            }
            case ROOK -> {
                time = (Math.abs(deltaX) == 1 || Math.abs(deltaY) == 1) ? 0.75F : (Math.abs(deltaX) > 3 || Math.abs(deltaY) > 3) ? 1.25F : time;

                AnimationBuilder builder = AnimationBuilder.withLength(time);
                if((Math.abs(deltaX) >= 5 || Math.abs(deltaY) >= 5) && board.getTile(destCord).getPiece() != null) { // If the distance is long enough we do a YEET attack
                    builder.addAnimation("moved", new KeyframeGroup(TransformTypes.POSITION,
                            new BasicKeyframe(0, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(0.2F, KeyframeAnimations.posVec(0F, 1F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 3) * 2, KeyframeAnimations.posVec(deltaX * 2F, 1F, deltaY * 2F), EasingTypes.EASE_IN_CUBIC),
                            new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX * 2F, 0F, deltaY * 2F), EasingTypes.LINEAR)));
                    builder.addAnimation("moved", new KeyframeGroup(TransformTypes.ROTATION,
                            new BasicKeyframe(0, KeyframeAnimations.degreeVec(0F, 0.0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4), KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4) * 2.5F, KeyframeAnimations.degreeVec(getRookSlamRotation(deltaX, deltaY, 40F), getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                            new BasicKeyframe((time / 4) * 2.8F, KeyframeAnimations.degreeVec(getRookSlamRotation(deltaX, deltaY, -60F), getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.POSITION,
                            new BasicKeyframe((time / 4) * 2.5F, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX == 0 ? 0 : deltaX > 0 ? -10 : 10, 7F, deltaY == 0 ? 0F : deltaY > 0 ? -10 : 10), EasingTypes.LINEAR)));
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.ROTATION,
                            new BasicKeyframe((time / 4) * 2.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.degreeVec(deltaY != 0 ? -300F : 0F, 0F, deltaX != 0 ? -300F : 0F), EasingTypes.LINEAR)));
                    builder.addAnimation("affected", new KeyframeGroup(TransformTypes.SCALE,
                            new BasicKeyframe((time / 4) * 3.5F, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.LINEAR),
                            new BasicKeyframe(time, KeyframeAnimations.scaleVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                    return builder.build();
                }
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.POSITION,
                        new BasicKeyframe(0, KeyframeAnimations.posVec(0F, 0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe(0.2F, KeyframeAnimations.posVec(0F, 1F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 3) * 2, KeyframeAnimations.posVec(deltaX * 2F, board.getTile(destCord).getPiece() != null ? 2.5F : 1F, deltaY * 2F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.posVec(deltaX * 2F, 0F, deltaY * 2F), EasingTypes.LINEAR)));
                builder.addAnimation("moved", new KeyframeGroup(TransformTypes.ROTATION,
                        new BasicKeyframe(0.0F, KeyframeAnimations.degreeVec(0F, 0.0F, 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4), KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.degreeVec(0F, getKingRotation(deltaX, deltaY), 0F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.degreeVec(0F, 0F, 0F), EasingTypes.LINEAR)));
                builder.addAnimation("affected", new KeyframeGroup(TransformTypes.SCALE,
                        new BasicKeyframe((time / 4) * 3, KeyframeAnimations.scaleVec(1F, 1F, 1F), EasingTypes.LINEAR),
                        new BasicKeyframe(time, KeyframeAnimations.scaleVec(1F, 0.1F, 1F), EasingTypes.LINEAR)));
                return builder.build();
            }
        }
        return null;
    }

    private static float getRookSlamRotation(int deltaX, int deltaY, float rotation)
    {
        if (deltaY != 0)
            return deltaY > 0 ? -rotation : rotation;
        if(deltaX != 0)
            return -rotation;
        return 0;
    }

    private static float getKnightRotation(int deltaX, int deltaY)
    {
        if(deltaX > 0 && deltaY > 0)
            return deltaX == 2 ? 53F : 26.5F;
        if(deltaX < 0 && deltaY > 0)
            return deltaX == -2 ? -53F : -26.5F;
        if(deltaX < 0 && deltaY < 0)
            return deltaX == -2 ? 53F : 26.5F;
        if(deltaX > 0 && deltaY < 0)
            return deltaX == 2 ? -53F : -26.5F;
        return 0;
    }

    private static float getKingRotation(int deltaX, int deltaY)
    {
        if(deltaX > 0 && deltaY > 0)
            return 45F;
        if(deltaX > 0 && deltaY == 0)
            return 90F;
        if(deltaX < 0 && deltaY > 0)
            return -45F;
        if(deltaX < 0 && deltaY == 0)
            return -90F;
        if(deltaX < 0)
            return 45F;
        if(deltaX > 0)
            return -45F;
        return 0;
    }
}