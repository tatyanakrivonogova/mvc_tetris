package view;

import controller.Controller;

import java.util.Objects;
import java.util.Scanner;

public class KeyListenerTUI extends Thread {
    final int DELAY = 10;
    Scanner scan;
    Controller controller;
    private static boolean running = true;
    public KeyListenerTUI(Controller _controller) {
        scan = new Scanner(System.in);
        controller = _controller;
//        run();
        this.start();
    }
    public void setRunning(boolean _running) {
        running = _running;
    }
    @Override
    public void run() {
        System.out.println("KEY LISTENER RUN");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                System.out.println("Game has been interrupted");
                interrupt();
            }
            System.out.println(running ? "TRUE" : "FALSE");
            if (running) {
                String input = scan.nextLine();
                if (Objects.equals(input, "x") || Objects.equals(input, "X")) controller.down();
                if (Objects.equals(input, "w") || Objects.equals(input, "W")) controller.up();
                if (Objects.equals(input, "a") || Objects.equals(input, "A")) controller.left();
                if (Objects.equals(input, "d") || Objects.equals(input, "D")) controller.right();
                if (Objects.equals(input, "q") || Objects.equals(input, "Q")) controller.clickExit();
                if (Objects.equals(input, " ")) controller.clickNewGame();
                if (Objects.equals(input, "e") || Objects.equals(input, "E")) controller.clickAbout();
                if (Objects.equals(input, "r") || Objects.equals(input, "R")) controller.clickHighScores();
            }
        }
        System.out.println("END");
    }
}