package andrews.table_top_craft.objects.blocks;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.screens.piece_figure.menus.ChessPieceFigureSettingsScreen;
import andrews.table_top_craft.block_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.util.TranslationHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class ChessPieceFigureBlock extends Block implements EntityBlock
{
    // Voxel Shape
    private static final VoxelShape CHESS_PIECE_FIGURE_BLOCK_AABB = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 2.0D, 13.0D);
    // Rotation
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

    public ChessPieceFigureBlock()
    {
        super(getProperties());
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0));
    }

    /**
     * @return The properties for this Block
     */
    private static Properties getProperties()
    {
        Properties properties = Block.Properties.of(Material.STONE);
        properties.strength(0.5F);
        properties.sound(SoundType.STONE);

        return properties;
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return this.defaultBlockState().setValue(ROTATION, Mth.floor((double) (pContext.getRotation() * 16.0F / 360.0F) + 0.5D) & 15);
    }

    /**
     * Called by BlockItem after this block has been placed.
     */
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        if(stack.hasCustomHoverName())
        {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
            {
                chessPieceFigureBlockEntity.setPieceName(stack.getHoverName().getString());
                chessPieceFigureBlockEntity.setChanged();
            }
        }
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
     * this block
     */
    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
        {
            if (!level.isClientSide && player.isCreative())// && !chessPieceFigureBlockEntity.isEmpty()
            {
                ItemStack itemstack = new ItemStack(TTCBlocks.CHESS_PIECE_FIGURE.get().asItem());
                blockentity.saveToItem(itemstack);
                if(chessPieceFigureBlockEntity.getPieceName() != null)
                    itemstack.setHoverName(Component.literal(chessPieceFigureBlockEntity.getPieceName()));

                ItemEntity itementity = new ItemEntity(level, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder)
    {
        List<ItemStack> items = super.getDrops(state, builder);
        BlockEntity blockentity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
        {
            ItemStack chessPieceFigure = items.get(0);
            blockentity.saveToItem(chessPieceFigure);
            items.set(0, chessPieceFigure);
        }
        return items;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, tooltip, flag);
        CompoundTag compoundtag = BlockItem.getBlockEntityData(stack);
        if (compoundtag != null)
        {
            if(Screen.hasShiftDown())
            {
                if (compoundtag.contains("PieceSet", Tag.TAG_INT))
                {
                    String pieceSetPath = "tooltip.table_top_craft.chess_piece_figure.piece_set";
                    switch (compoundtag.getInt("PieceSet"))
                    {
                        case 1 -> TranslationHelper.getToolTipWithTextFromLang(tooltip, pieceSetPath, "tooltip.table_top_craft.chess_piece_figure.set.standard");
                        case 2 -> TranslationHelper.getToolTipWithTextFromLang(tooltip, pieceSetPath, "tooltip.table_top_craft.chess_piece_figure.set.classic");
                        case 3 -> TranslationHelper.getToolTipWithTextFromLang(tooltip, pieceSetPath, "tooltip.table_top_craft.chess_piece_figure.set.pandoras_creatures");
                    }
                }
                if (compoundtag.contains("PieceType", Tag.TAG_INT))
                {
                    String pieceTypePath = "tooltip.table_top_craft.chess_piece_figure.piece_type";
                    switch (compoundtag.getInt("PieceType"))
                    {
                        case 1 -> TranslationHelper.getToolTipWithTextFromLang(tooltip, pieceTypePath, "tooltip.table_top_craft.chess_piece_figure.type.pawn");
                        case 2 -> TranslationHelper.getToolTipWithTextFromLang(tooltip, pieceTypePath, "tooltip.table_top_craft.chess_piece_figure.type.rook");
                        case 3 -> TranslationHelper.getToolTipWithTextFromLang(tooltip, pieceTypePath, "tooltip.table_top_craft.chess_piece_figure.type.bishop");
                        case 4 -> TranslationHelper.getToolTipWithTextFromLang(tooltip, pieceTypePath, "tooltip.table_top_craft.chess_piece_figure.type.knight");
                        case 5 -> TranslationHelper.getToolTipWithTextFromLang(tooltip, pieceTypePath, "tooltip.table_top_craft.chess_piece_figure.type.king");
                        case 6 -> TranslationHelper.getToolTipWithTextFromLang(tooltip, pieceTypePath, "tooltip.table_top_craft.chess_piece_figure.type.queen");
                    }
                }
                if (compoundtag.contains("RotateChessPieceFigure", Tag.TAG_INT))
                {
                    String shouldRotatePath = "tooltip.table_top_craft.chess_piece_figure.should_rotate";
                    switch (compoundtag.getInt("RotateChessPieceFigure"))
                    {
                        case 0 -> TranslationHelper.getToolTipWithTextFromLang(tooltip, shouldRotatePath, "tooltip.table_top_craft.chess_piece_figure.toggle.disabled");
                        case 1 -> TranslationHelper.getToolTipWithTextFromLang(tooltip, shouldRotatePath, "tooltip.table_top_craft.chess_piece_figure.toggle.enabled");
                    }
                }
                if (compoundtag.contains("PieceColor", Tag.TAG_STRING))
                {
                    String pieceColorText = Component.translatable("tooltip.table_top_craft.chess_piece_figure.piece_color").getString();
                    String colorDescriptionText = compoundtag.getString("PieceColor");
                    pieceColorText = pieceColorText.replaceAll("#c", "§");
                    colorDescriptionText = colorDescriptionText.substring(0, colorDescriptionText.length() - 4);

                    tooltip.add(Component.literal(pieceColorText + colorDescriptionText));
                }
                if (compoundtag.contains("PieceScale", Tag.TAG_DOUBLE))
                {
                    double chessPieceScale = compoundtag.getDouble("PieceScale");
                    String pieceScaleText = Component.translatable("tooltip.table_top_craft.chess_piece_figure.scale", Component.literal(String.valueOf(chessPieceScale))).getString();
                    TranslationHelper.getTooltipFromLang(tooltip, pieceScaleText);
                }
                TranslationHelper.addEnchantmentSeparationLine(tooltip, stack);
            }
            else
            {
                TranslationHelper.getTooltipFromLang(tooltip, "tooltip.table_top_craft.chess_piece_figure.more_info");
                TranslationHelper.addEnchantmentSeparationLine(tooltip, stack);
            }
        }
        else
        {
            TranslationHelper.getTooltipFromLang(tooltip, "tooltip.table_top_craft.chess_piece_figure.data_none");
            TranslationHelper.addEnchantmentSeparationLine(tooltip, stack);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player)
    {
        ItemStack stack = super.getCloneItemStack(state, target, level, pos, player);
        if(level.getBlockEntity(pos) != null)
        {
            if(level.getBlockEntity(pos) instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
            {
                chessPieceFigureBlockEntity.saveToItem(stack);
                if(chessPieceFigureBlockEntity.getPieceName() != null)
                    stack.setHoverName(Component.literal(chessPieceFigureBlockEntity.getPieceName()));
            }
        }
        return stack;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        if (player.isShiftKeyDown())
        {
            if (level.getBlockEntity(pos) instanceof ChessPieceFigureBlockEntity chessPieceFigureBlockEntity)
            {
                if (level.isClientSide)
                    ChessPieceFigureSettingsScreen.open(chessPieceFigureBlockEntity);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return CHESS_PIECE_FIGURE_BLOCK_AABB;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new ChessPieceFigureBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(ROTATION);
    }
}