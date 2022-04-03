package andrews.table_top_craft.objects.blocks;

import andrews.table_top_craft.registry.TTCBlocks;
import andrews.table_top_craft.screens.piece_figure.menus.ChessPieceFigureSettingsScreen;
import andrews.table_top_craft.tile_entities.ChessPieceFigureBlockEntity;
import andrews.table_top_craft.util.Reference;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
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
        properties.strength(1.0F);
        properties.sound(SoundType.STONE);

        return properties;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        return this.defaultBlockState().setValue(ROTATION, Mth.floor((double) (pContext.getRotation() * 16.0F / 360.0F) + 0.5D) & 15);
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
//                if (shulkerboxblockentity.hasCustomName()) {
//                    itemstack.setHoverName(shulkerboxblockentity.getCustomName());
//                }

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
            if (compoundtag.contains("PieceType", Tag.TAG_INT))
            {
                switch (compoundtag.getInt("PieceType"))
                {
                    case 1 -> tooltip.add(new TextComponent("Piece Type: Pawn").withStyle(ChatFormatting.DARK_GRAY));//TODO replace with text component
                    case 2 -> tooltip.add(new TextComponent("Piece Type: Rook").withStyle(ChatFormatting.DARK_GRAY));
                    case 3 -> tooltip.add(new TextComponent("Piece Type: Bishop").withStyle(ChatFormatting.DARK_GRAY));
                    case 4 -> tooltip.add(new TextComponent("Piece Type: Knight").withStyle(ChatFormatting.DARK_GRAY));
                    case 5 -> tooltip.add(new TextComponent("Piece Type: King").withStyle(ChatFormatting.DARK_GRAY));
                    case 6 -> tooltip.add(new TextComponent("Piece Type: Queen").withStyle(ChatFormatting.DARK_GRAY));
                }
            }
        }
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