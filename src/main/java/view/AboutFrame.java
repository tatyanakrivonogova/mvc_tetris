package view;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JFrame {
    public AboutFrame() {
        super("About");

        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
        aboutPanel.add(new JLabel("Control:", SwingConstants.CENTER));
        aboutPanel.add(new JLabel("UP - Rotate", SwingConstants.CENTER));
        aboutPanel.add(new JLabel("LEFT - Move left", SwingConstants.CENTER));
        aboutPanel.add(new JLabel("RIGHT - Move right", SwingConstants.CENTER));
        aboutPanel.add(new JLabel("DOWN - Drop", SwingConstants.CENTER));

        this.setLayout(new BorderLayout());
        this.add(aboutPanel, BorderLayout.CENTER);
        this.setSize(200, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
}