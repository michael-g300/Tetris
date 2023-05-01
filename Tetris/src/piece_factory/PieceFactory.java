package piece_factory;

import pieces.Piece;

import java.util.HashMap;
import java.util.Map;

public class PieceFactory {
    private final Map<Integer, PieceCreator> m_creators = new HashMap<>();
    public void register(final int key, final PieceCreator pieceCreator) {
        m_creators.put(key, pieceCreator);
    }
    public Piece create(final int key) {
        return m_creators.get(key).create();
    }
}
