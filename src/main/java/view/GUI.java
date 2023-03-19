package view;

import controller.Controller;
import model.Figure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;

public class GUI extends JFrame implements View {
    final int BLOCK_SIZE = 25;
    final int WINDOW_WIDTH = 15;
    final int WINDOW_HEIGHT = 18;
    final int FIELD_WIDTH = 10;
    final int FIELD_HEIGHT = 18;
    final int START_LOCATION = 180;
    final int FIELD_DX = 15;
    final int FIELD_DY = 15;
    final int LEFT = 37;
    final int UP = 38;
    final int RIGHT = 39;
    final int DOWN = 40;
    private final Canvas canvas;
    JPanel panel = new JPanel();
    JLabel stateLabel;
    JLabel scoreLabel;
    Controller controller;

    public GUI(Controller _controller) {
        super();
        controller = _controller;
        canvas = new Canvas();

        setTitle("TETRIS 0");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION,
                WINDOW_WIDTH * BLOCK_SIZE + FIELD_DX, WINDOW_HEIGHT * BLOCK_SIZE + FIELD_DY);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        canvas.setBackground(Color.black);
        add(BorderLayout.CENTER, canvas);

        panel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == DOWN) controller.down();
                if (e.getKeyCode() == UP) controller.up();
                if (e.getKeyCode() == LEFT) controller.left();
                if (e.getKeyCode() == RIGHT) controller.right();
            }
        });

        stateLabel = new JLabel("STATE: ");
        stateLabel.setForeground(Color.pink);

        scoreLabel = new JLabel("SCORE: ");
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
        panel.add(stateLabel);
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
    public void update(int[][] field, boolean gameOver, Figure figure, int scores, boolean state) {
        changeScores(scores);
        changeState(state);
        canvas.update(field, figure, gameOver);
    }
    public void changeScores(int scores) {
        scoreLabel.setText("SCORES: " + scores);
    }
    public void changeState(boolean state) {
        if (state) {
            stateLabel.setText("STATE: RUN");
        }
        else {
            stateLabel.setText("STATE: PAUSE");
        }
    }

    public void closeGame() {
        dispose();
    }
    @Override
    public void changeTitle(String title) {
        setTitle(title);
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
    public void showHighScores(Properties properties) {

        LeaderBoardCreator creator = new LeaderBoardCreator();
        JOptionPane.showMessageDialog(new JFrame(), creator.createLeaderBoard(properties), "High records",
                JOptionPane.INFORMATION_MESSAGE);
    }
    public void showNewGame() {
        String message = "Let's start new game!";
        JOptionPane.showMessageDialog(new JFrame(), message, "New game",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
