package piece_factory;

import pieces.Piece;
import pieces.T_piece;

public class T_pieceCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new T_piece();
    }
}
