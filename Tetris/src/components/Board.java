package components;

import pieces.Piece;

public interface Board {
    boolean[][] getCells();
    int getFinishedRows();
    boolean addPiece(Piece piece);
    void removePiece(Piece piece);
    boolean rotate(Piece piece);
    boolean moveDown(Piece piece);
    boolean moveLeft(Piece piece);
    boolean moveRight(Piece piece);
    void checkFinishedRows();
}
