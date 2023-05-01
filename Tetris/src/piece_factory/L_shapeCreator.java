package piece_factory;

import pieces.I_shape;
import pieces.L_shape;
import pieces.Piece;

public class L_shapeCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new L_shape();
    }
}
