package piece_factory;

import pieces.I_piece;
import pieces.Piece;

public class I_pieceCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new I_piece();
    }
}
