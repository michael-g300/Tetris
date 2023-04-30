package components;

public class Position {
    private int m_row;
    private int m_col;
    public Position(final int row, final int col) {
        m_col = col;
        m_row = row;
    }
    public int row() {
        return m_row;
    }
    public int col() {
        return m_col;
    }
    public void setRow(final int newRow) {
        m_row = newRow;
    }
    public void setCol(final int newCol) {
        m_col = newCol;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(m_row);
        builder.append(",");
        builder.append(m_col);
        builder.append("]");
        return builder.toString();
    }
}
