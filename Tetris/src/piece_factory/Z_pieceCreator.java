package piece_factory;

import pieces.Piece;
import pieces.Z_piece;

public class Z_pieceCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new Z_piece();
    }
}
