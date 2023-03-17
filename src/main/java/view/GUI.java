package view;

import controller.Controller;
import model.Figure;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

public class GUI extends JFrame implements View {
    final int BLOCK_SIZE = 25;
    final int WINDOW_WIDTH = 15;
    final int WINDOW_HEIGHT = 18;
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int START_LOCATION = 180;
    final int FIELD_DX = 15;
    final int FIELD_DY = 15;
    private Canvas canvas;
    JPanel panel = new JPanel();
    Model game;
    Controller controller;

    public GUI(Model _game) {
        super();
        game = _game;
        canvas = new Canvas(game);

        setTitle("TETRIS 0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION,
                WINDOW_WIDTH * BLOCK_SIZE + FIELD_DX, WINDOW_HEIGHT * BLOCK_SIZE + FIELD_DY);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        canvas.setBackground(Color.black);
        add(BorderLayout.CENTER, canvas);

        JLabel scoreLabel = new JLabel("SCORE: " + game.getGameScore());
        scoreLabel.setForeground(Color.PINK);

        setLayout( new FlowLayout(FlowLayout.LEFT) );
        Button buttonAbout = new Button("ABOUT");
        Button buttonHighScores = new Button("HIGH SCORES");
        Button buttonNewGame = new Button("NEW GAME");
        Button buttonExit = new Button("EXIT");

        buttonAbout.setBackground(Color.ORANGE);
        buttonHighScores.setBackground(Color.ORANGE);
        buttonNewGame.setBackground(Color.ORANGE);
        buttonExit.setBackground(Color.ORANGE);

        buttonAbout.addActionListener(e -> {
            controller.clickButtonAbout();
            buttonAbout.setFocusable(false);
        });
        buttonHighScores.addActionListener(e -> {
            controller.clickButtonHighScores();
            buttonHighScores.setFocusable(false);
        });
        buttonNewGame.addActionListener(e -> {
            controller.clickButtonNewGame();
            buttonNewGame.setFocusable(false);
        });
        buttonExit.addActionListener(e -> {
            controller.clickButtonExit();
            buttonExit.setFocusable(false);
        });

        panel.setBounds(START_LOCATION, START_LOCATION,
                WINDOW_WIDTH * BLOCK_SIZE + FIELD_DX, WINDOW_HEIGHT * BLOCK_SIZE + FIELD_DY);
        panel.setBackground(Color.BLACK);
        panel.setLayout(new GridLayout(0, 1, 0, 20));
        panel.add(scoreLabel);
        panel.add(buttonAbout);
        panel.add(buttonHighScores);
        panel.add(buttonNewGame);
        panel.add(buttonExit);

        canvas.setPreferredSize(new Dimension(FIELD_WIDTH*BLOCK_SIZE+FIELD_DX, FIELD_HEIGHT*BLOCK_SIZE+FIELD_DY));
        panel.setFocusable(true);
        canvas.setVisible(true);

        add(canvas);
        add(panel);
        pack();
        setVisible(true);
    }
    @Override
    public void setController(Controller _controller) {
        controller = _controller;
    }
    public void addListener(KeyListener _l) {
        panel.addKeyListener(_l);
    }
    @Override
    public void update(int[][] field, boolean gameOver, Figure figure) {
        canvas.repaint();
    }
    @Override
    public void changeTitle(String title) {
        setTitle(title);
    }
    @Override
    public void clear() {
        canvas = new Canvas(game);
        add(canvas);
        //canvas.repaint();
    }
    public void showAbout() {
        String message = """
            UP
                rotate figure
            RIGHT
                move figure to right side
            LEFT
                move figure to left side
            DOWN
                drop figure
        """;
        JOptionPane.showMessageDialog(new JFrame(), message, "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
    public void showHighRecords() {
        String message = """
            John: 10000
            Max: 100
            Jacob: 3
        """;
        JOptionPane.showMessageDialog(new JFrame(), message, "High records",
                JOptionPane.INFORMATION_MESSAGE);
    }
    public void showNewGame() {
        String message = "Let's start new game!";
        JOptionPane.showMessageDialog(new JFrame(), message, "New game",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
