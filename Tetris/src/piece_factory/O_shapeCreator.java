package piece_factory;

import pieces.I_shape;
import pieces.O_shape;
import pieces.Piece;

public class O_shapeCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new O_shape();
    }
}
