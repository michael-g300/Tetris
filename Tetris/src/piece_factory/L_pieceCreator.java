package piece_factory;

import pieces.L_piece;
import pieces.Piece;

public class L_pieceCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new L_piece();
    }
}
