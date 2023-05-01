package piece_factory;

import pieces.J_piece;
import pieces.Piece;

public class J_pieceCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new J_piece();
    }
}
