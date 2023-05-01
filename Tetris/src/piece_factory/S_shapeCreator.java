package piece_factory;

import pieces.I_shape;
import pieces.Piece;
import pieces.S_shape;

public class S_shapeCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new S_shape();
    }
}
