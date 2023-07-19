import gui.MenuFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class GameStarter {
    private final MenuFrame m_menuFrame;
    private final JPanel m_buttonsPanel;
    private final JButton m_startButton;
    private final JSlider m_levelPicker;
    private final JRadioButton m_regularWidth;
    private final JRadioButton m_bigWidth;
    private boolean m_regularWidthGame = true;
    public GameStarter() {
        m_menuFrame = new MenuFrame();
        m_buttonsPanel = new JPanel();
        m_buttonsPanel.setBounds(10, 10, 495, 470);

        m_startButton = new JButton();
        m_startButton.setBounds(200, 100, 200 ,100);
        m_startButton.setText("PLAY");
        m_startButton.setBorder(BorderFactory.createEtchedBorder());

        m_levelPicker = new JSlider(0,10,1);
        m_levelPicker.setPreferredSize(new Dimension(400,200));
        m_levelPicker.setPaintTicks(true);
        m_levelPicker.setPaintTrack(true);
        m_levelPicker.setMajorTickSpacing(1);
        m_levelPicker.setPaintLabels(true);
        m_levelPicker.setFont(new Font("MV Boli", Font.PLAIN, 15));

        JLabel levelLabel = new JLabel();
        levelLabel.setText("start at level: " + m_levelPicker.getValue());

        m_levelPicker.addChangeListener(e -> {
            levelLabel.setText("start at level: " + m_levelPicker.getValue());
        });

        m_regularWidth = new JRadioButton("Normal game", true);
        m_regularWidth.addActionListener(e -> {
            m_regularWidthGame = true;
        });
        m_bigWidth = new JRadioButton("Wide game");
        m_bigWidth.addActionListener(e -> {
            m_regularWidthGame = false;
        });
        ButtonGroup widthButtonGroup = new ButtonGroup();
        widthButtonGroup.add(m_regularWidth);
        widthButtonGroup.add(m_bigWidth);

        m_startButton.addActionListener(e ->{
            Thread thread = new Thread(() -> {
                GameManager manager = new GameManager(m_regularWidthGame, m_levelPicker.getValue());
            });
            m_menuFrame.dispose();
            thread.start();
        } );

        m_buttonsPanel.add(m_startButton);
        m_buttonsPanel.add(m_levelPicker);
        m_buttonsPanel.add(levelLabel);
        m_buttonsPanel.add(m_regularWidth);
        m_buttonsPanel.add(m_bigWidth);
        m_menuFrame.add(m_buttonsPanel);
        m_menuFrame.setVisible(true);
    }
}
