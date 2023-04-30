package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TestGame {
    private Action downAction;
    private Action upAction;
    private Action rightAction;
    private Action leftAction;
    private PieceSquare square;
    public TestGame() {
        var testFrame = new TestFrame();
        testFrame.setBounds(0, 0, 500, 500);
        var panel = new JPanel();
        panel.setBounds(0, 0, 300, 300);
        panel.setBackground(Color.lightGray);

        downAction = new DownAction();
        upAction = new UpAction();
        rightAction = new RightAction();
        leftAction = new LeftAction();

        square = new PieceSquare(Color.BLACK);

        square.getInputMap().put(KeyStroke.getKeyStroke("UP"),"upAction");
        square.getActionMap().put("upAction", upAction);
        square.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        square.getActionMap().put("downAction", downAction);
        square.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        square.getActionMap().put("rightAction", rightAction);
        square.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        square.getActionMap().put("leftAction", leftAction);

        panel.add(square);
        testFrame.add(panel);
        testFrame.setVisible(true);
    }
    public class DownAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("down action");
            square.setLocation(0, square.getY() - 10);
        }
    }
    public class UpAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            square.setLocation(0, square.getY() + 10);
        }
    }
    public class RightAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            square.setLocation(square.getX() + 10, 0);
        }
    }
    public class LeftAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            square.setLocation(square.getX() - 10, 0);
        }
    }
}
