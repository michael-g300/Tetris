package piece_factory;

import components.Position;
import pieces.I_shape;
import pieces.Piece;

import java.util.List;

public class I_shapeCreator implements PieceCreator {
    @Override
    public Piece create() {
        return new I_shape();
    }
}
