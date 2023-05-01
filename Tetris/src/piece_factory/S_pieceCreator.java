package piece_factory;

import pieces.Piece;
import pieces.S_piece;

public class S_pieceCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new S_piece();
    }
}
