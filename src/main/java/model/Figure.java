package model;
import java.awt.*;

import exceptions.factoryexceptions.FactoryException;
import model.factory.Factory;
import model.figuretemplates.FigureTemplate;

import java.util.ArrayList;
import java.util.Random;

public class Figure {
    final int FIELD_WIDTH = 10;
    final int LEFT = 37;
    final int RIGHT = 39;
    final static int NUMBER_OF_TYPES = 7;
    final static Random random = new Random();
    private final ArrayList<Block> figure = new ArrayList<>();
    int [][] shape;
    int size;
    int color;
    int[][] field;
    private int x = 3, y = 0; // starting left up corner

    Figure(int[][] _field) throws FactoryException {
        field = _field;
        int type = random.nextInt(NUMBER_OF_TYPES);
        FigureTemplate template = (FigureTemplate) Factory.getFigure(type);
        shape = template.getShape();
        size = template.getSize();
        color = template.getColor();
        createFromTemplate();
    }

    private void createFromTemplate() {
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                if (shape[y][x] == 1) figure.add(new Block(x + this.x, y + this.y));
    }

    public boolean isTouchGround() {
        for (Block block : figure) if (field[block.getY() + 1][block.getX()] > 0) return true;
        return false;
    }

    public boolean isCrossGround() {
        for (Block block : figure) if (field[block.getY()][block.getX()] > 0) return true;
        return false;
    }

    public void leaveOnTheGround() {
        for (Block block : figure) field[block.getY()][block.getX()] = color;
    }

    public boolean isTouchWall(int direction) {
        for (Block block : figure) {
            if (direction == LEFT && (block.getX() == 0 || field[block.getY()][block.getX() - 1] > 0)) return true;
            if (direction == RIGHT && (block.getX() == FIELD_WIDTH - 1 || field[block.getY()][block.getX() + 1] > 0)) return true;
        }
        return false;
    }

    public void move(int direction) {
        if (!isTouchWall(direction)) {
            int dx = direction - 38; // LEFT = -1, RIGHT = 1
            for (Block block : figure) block.setX(block.getX() + dx);
            x += dx;
        }
    }

    public void stepDown() {
        for (Block block : figure) block.setY(block.getY() + 1);
        y++;
    }

    public void drop() { while (!isTouchGround()) stepDown(); }

    private boolean isWrongPosition() {
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                if (shape[y][x] == 1) {
                    if (y + this.y < 0) return true;
                    if (x + this.x < 0 || x + this.x > FIELD_WIDTH - 1) return true;
                    if (field[y + this.y][x + this.x] > 0) return true;
                }
        return false;
    }

    private void rotateShape(int direction) {
        for (int i = 0; i < size/2; i++)
            for (int j = i; j < size-1-i; j++)
                if (direction == RIGHT) { // clockwise
                    int tmp = shape[size-1-j][i];
                    shape[size-1-j][i] = shape[size-1-i][size-1-j];
                    shape[size-1-i][size-1-j] = shape[j][size-1-i];
                    shape[j][size-1-i] = shape[i][j];
                    shape[i][j] = tmp;
                } else { // counterclockwise
                    int tmp = shape[i][j];
                    shape[i][j] = shape[j][size-1-i];
                    shape[j][size-1-i] = shape[size-1-i][size-1-j];
                    shape[size-1-i][size-1-j] = shape[size-1-j][i];
                    shape[size-1-j][i] = tmp;
                }
    }

    public void rotate() {
        rotateShape(RIGHT);
        if (!isWrongPosition()) {
            figure.clear();
            createFromTemplate();
        } else
            rotateShape(LEFT);
    }

    public void paint(Graphics g) {
        for (Block block : figure) block.paint(g, color);
    }
}
