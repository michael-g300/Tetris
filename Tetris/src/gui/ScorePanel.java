package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ScorePanel extends JPanel {
    private static final Color BCKG_COLOR = Color.lightGray;
    private final JLabel m_finishedRows;
    private final JLabel m_level;
    public ScorePanel() {
        this.setBackground(BCKG_COLOR);
        this.setBorder(new LineBorder(Color.BLACK, 2));
        this.setLayout(null);

        m_finishedRows = new JLabel("lines       0");
        m_finishedRows.setVerticalTextPosition(JLabel.TOP);
        m_finishedRows.setBackground(BCKG_COLOR);
        m_finishedRows.setForeground(Color.RED);
        m_finishedRows.setOpaque(true);
        m_finishedRows.setFont(new Font("Tahoma", Font.BOLD, 20));
        m_finishedRows.setBounds(20, 20, 100, 50);

        m_level = new JLabel("Level      0");
        m_level.setVerticalTextPosition(JLabel.BOTTOM);
        m_level.setBackground(BCKG_COLOR);
        m_level.setForeground(Color.RED);
        m_level.setOpaque(true);
        m_level.setFont(new Font("Tahoma", Font.BOLD, 20));
        m_level.setBounds(20, 80, 100, 50);

        this.add(m_level);
        this.add(m_finishedRows);
        this.setVisible(true);
    }
    public void update(final int rowsFinished) {
        m_finishedRows.setText("lines       " + rowsFinished);
        m_level.setText("Level      " + rowsFinished / 10);
    }
}
