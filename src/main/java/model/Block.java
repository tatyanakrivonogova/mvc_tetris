package model;
import java.awt.*;

class Block {
    private int x;
    private int y;
    final static int BLOCK_SIZE = 25;
    final static int ARC_RADIUS = 6;

    public Block(int _x, int _y) {
        x = _x;
        y = _y;
    }
    void setX(int _x) { x = _x; }
    void setY(int _y) { y = _y; }
    int getX() { return x; }
    int getY() { return y; }

    void paint(Graphics g, int color) {
        g.setColor(new Color(color));
        g.drawRoundRect(x*BLOCK_SIZE+1, y*BLOCK_SIZE+1, BLOCK_SIZE-2, BLOCK_SIZE-2, ARC_RADIUS, ARC_RADIUS);
    }
}