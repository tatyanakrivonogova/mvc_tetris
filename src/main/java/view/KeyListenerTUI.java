package view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import controller.Controller;

import java.io.IOException;
import java.util.Objects;

public class KeyListenerTUI {
    volatile boolean running = true;
    Controller controller;
    private final TUI keyStrokeProducer;
    Thread thread = null;
    public KeyListenerTUI(TUI keyStrokeProducer, Controller _controller) {
        controller = _controller;
        Objects.requireNonNull(keyStrokeProducer);
        this.keyStrokeProducer = keyStrokeProducer;
        listen();
    }
    public void setRunning(boolean value) {
        running = value;
//        if (!value) {
//            thread.interrupt();
//        }
    }
    void listen() {
        thread = new Thread(() -> {
            KeyStroke keyStroke = null;
            while(true) {
                if (!running) return;
                try {
                    keyStroke = keyStrokeProducer.readInput();
                } catch (IOException e) {
                    System.out.println("Error while reading");
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
        thread.setName("My Listener");
        thread.start();
    }
    public boolean getReply() {
        running = false;
        try {
            KeyStroke keyStroke = keyStrokeProducer.readInput();
            if (keyStroke.getCharacter() != null) {
                return keyStroke.getCharacter().equals('y') || keyStroke.getCharacter().equals('н');
            } else {
                getReply();
            }
        } catch (IOException e) {
            System.out.println("Error during reply");
        }
        running = true;
        return false;
    }
    public void getAnyKey() {
        running = false;
        try {
            keyStrokeProducer.readInput();
        } catch (IOException e) {
            System.out.println("Error during reply");
        }
        running = true;
    }
    public String getName() {
        running = false;
        StringBuilder name = new StringBuilder();
        KeyStroke keyStroke = null;
        try {
            keyStroke = keyStrokeProducer.readInput();
        } catch (IOException e) {
            System.out.println("Error while getting name");
        }
        while (keyStroke != null && !(keyStroke.getKeyType() == KeyType.Enter)) {
            if (keyStroke.getCharacter() != null) {
                name.append(keyStroke.getCharacter());
                keyStrokeProducer.printChar(keyStroke.getCharacter());
                keyStrokeProducer.addToScreen();
            }
            try {
                keyStroke = keyStrokeProducer.readInput();
            } catch (IOException e) {
                System.out.println("Error while getting name");
            }
        }
        running = true;
        return name.toString();
    }
}
