package piece_factory;

import pieces.O_piece;
import pieces.Piece;

public class O_pieceCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new O_piece();
    }
}
