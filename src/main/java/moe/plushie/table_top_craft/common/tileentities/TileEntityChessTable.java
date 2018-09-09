package moe.plushie.table_top_craft.common.tileentities;

import moe.plushie.table_top_craft.common.games.chess.ChessGame;
import net.minecraft.tileentity.TileEntity;

public class TileEntityChessTable extends TileEntity {
    
    private ChessGame chessGame;
    
    public TileEntityChessTable() {
        chessGame = new ChessGame();
    }

    public ChessGame getChessGame() {
        return chessGame;
    }
}
