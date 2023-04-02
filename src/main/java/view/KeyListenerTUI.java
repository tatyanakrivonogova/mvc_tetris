////package view;
////
////import controller.Controller;
////
////import java.util.Objects;
////import java.util.Scanner;
////import jcurses.system.InputChar;
////import jcurses.system.Toolkit;
////
////public class KeyListenerTUI extends Thread {
////    final int DELAY = 10;
////    Scanner scan;
////    Controller controller;
////    private static boolean running = true;
////    public KeyListenerTUI(Controller _controller) {
////        scan = new Scanner(System.in);
////        controller = _controller;
////        Toolkit.init();
////        this.start();
////    }
////    public void setRunning(boolean _running) {
////        running = _running;
////    }
////    @Override
////    public void run() {
////        System.out.println("KEY LISTENER RUN");
////        while (!Thread.currentThread().isInterrupted()) {
////            try {
////                sleep(DELAY);
////            } catch (InterruptedException e) {
////                System.out.println("Game has been interrupted");
////                interrupt();
////            }
////            System.out.println(running ? "TRUE" : "FALSE");
////            if (running) {
////                //String input = scan.nextLine();
////                InputChar input = Toolkit.readCharacter();
////                if (input.getCharacter() == 'x' || input.getCharacter() == 'X') controller.down();
////                if (input.getCharacter() == 'w' || input.getCharacter() == 'W') controller.up();
////                if (input.getCharacter() == 'a' || input.getCharacter() == 'A') controller.left();
////                if (input.getCharacter() == 'd' || input.getCharacter() == 'D') controller.right();
////                if (input.getCharacter() == 'q' || input.getCharacter() == 'Q') controller.clickExit();
////                if (input.getCharacter() == ' ') controller.clickNewGame();
////                if (input.getCharacter() == 'e' || input.getCharacter() == 'E') controller.clickAbout();
////                if (input.getCharacter() == 'r' || input.getCharacter() == 'R') controller.clickHighScores();
////            }
////        }
////        System.out.println("END");
////        Toolkit.shutdown();
////    }
////}
//
//package view;
//import controller.Controller;
//
//import javax.swing.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.util.Objects;
//import java.util.Scanner;
//import java.util.Timer;
//import java.util.TimerTask;
//public class KeyListenerTUI {
//    final int SHOW_DELAY = 1;
//    final int LEFT = 37;
//    final int UP = 38;
//    final int RIGHT = 39;
//    final int DOWN = 40;
//    //Scanner scan;
//    JFrame frame;
//    Controller controller;
//    private static boolean running = true;
//    Timer timer = new Timer();
//    public KeyListenerTUI(Controller _controller) {
//        //scan = new Scanner(System.in);
//        frame = new JFrame();
//        frame.addKeyListener(new KeyAdapter() {
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == DOWN) controller.down();
//                if (e.getKeyCode() == UP) controller.up();
//                if (e.getKeyCode() == LEFT) controller.left();
//                if (e.getKeyCode() == RIGHT) controller.right();
//            }
//        });
//        controller = _controller;
//        run();
//    }
//    public void setRunning(boolean _running) {
//        running = _running;
//    }
//    private String safeNextLine() {
//        synchronized (System.out) {
//            //return scan.nextLine();
//            return "";
//        }
//    }
//    public void run() {
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                if (running) {
//                    //System.out.println("LISTEN**********************************************");
//                    //if (!scan.hasNextLine()) return;
//                    String input = safeNextLine();
//                    if (Objects.equals(input, "x") || Objects.equals(input, "X")) controller.down();
//                    if (Objects.equals(input, "w") || Objects.equals(input, "W")) controller.up();
//                    if (Objects.equals(input, "a") || Objects.equals(input, "A")) controller.left();
//                    if (Objects.equals(input, "d") || Objects.equals(input, "D")) controller.right();
//                    if (Objects.equals(input, "q") || Objects.equals(input, "Q")) controller.clickExit();
//                    if (Objects.equals(input, " ")) controller.clickNewGame();
//                    if (Objects.equals(input, "e")) controller.clickAbout();
//                    if (Objects.equals(input, "r")) controller.clickHighScores();
//                    if (Objects.equals(input, "e") || Objects.equals(input, "E")) controller.clickAbout();
//                    if (Objects.equals(input, "r") || Objects.equals(input, "R")) controller.clickHighScores();
//                }
//            }
//        };
//        timer.schedule(timerTask, 0L, SHOW_DELAY);
//    }
//}
//

package view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import controller.Controller;

import java.io.IOException;
import java.util.Objects;

public class KeyListenerTUI {
    boolean running = true;
    Controller controller;
    private final TUI keyStrokeProducer;
    public KeyListenerTUI(TUI keyStrokeProducer, Controller _controller) {
        controller = _controller;
        Objects.requireNonNull(keyStrokeProducer);
        this.keyStrokeProducer = keyStrokeProducer;
        listenForInputEvents();
    }
    public void setRunning(boolean value) {
        running = value;
    }
    private void listenForInputEvents() {
        Thread thread = new Thread(() -> {
            KeyStroke keyStroke = null;
            while(true) {
                 if (!running) return;
                try {
                    keyStroke = keyStrokeProducer.readInput();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (keyStroke != null) {
                    if (keyStroke.getKeyType() == KeyType.ArrowLeft) controller.left();
                    if (keyStroke.getKeyType() == KeyType.ArrowUp) controller.up();
                    if (keyStroke.getKeyType() == KeyType.ArrowDown) controller.down();
                    if (keyStroke.getKeyType() == KeyType.ArrowRight) controller.right();

                    if (keyStroke.getKeyType() == KeyType.Escape) controller.clickExit();
                    if (keyStroke.getKeyType() == KeyType.Enter) controller.clickNewGame();
                    if (keyStroke.getCharacter() != null && keyStroke.getCharacter().equals(' ')) controller.clickHighScores();
                    if (keyStroke.getCharacter() != null && keyStroke.getCharacter().equals('/')) controller.clickAbout();
                }
            }
        });

        thread.start();
    }
    public boolean getReply() {
        try {
            KeyStroke keyStroke = keyStrokeProducer.readInput();
            return keyStroke.getCharacter().equals('y');
        } catch (IOException e) {
            System.out.println("Error during reply");
        }
        return false;
    }
    public void getAnyKey() {
        try {
            keyStrokeProducer.readInput();
        } catch (IOException e) {
            System.out.println("Error during reply");
        }
    }
}
