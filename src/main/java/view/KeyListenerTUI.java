package view;

import controller.Controller;

import java.util.Objects;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class KeyListenerTUI {
    final int SHOW_DELAY = 400;
    Scanner scan;
    Controller controller;
    private static boolean running = true;
    Timer timer = new Timer();
    public KeyListenerTUI(Controller _controller) {
        scan = new Scanner(System.in);
        controller = _controller;
        run();
    }
    public void setRunning(boolean _running) {
        running = _running;
    }
    public void run() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    String input = scan.nextLine();
                    if (Objects.equals(input, "x")) controller.down();
                    if (Objects.equals(input, "w")) controller.up();
                    if (Objects.equals(input, "a")) controller.left();
                    if (Objects.equals(input, "d")) controller.right();
                    if (Objects.equals(input, "q")) controller.clickExit();
                    if (Objects.equals(input, " ")) controller.clickNewGame();
                    if (Objects.equals(input, "e")) controller.clickAbout();
                    if (Objects.equals(input, "r")) controller.clickHighScores();
                }
            }
        };
        timer.schedule(timerTask, 0L, SHOW_DELAY);
    }
}
