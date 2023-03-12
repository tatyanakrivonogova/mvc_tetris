package model;

public class Block {
    private int x;
    private int y;
    public Block(int _x, int _y) {
        x = _x;
        y = _y;
    }
    void setX(int _x) { x = _x; }
    void setY(int _y) { y = _y; }
    public int getX() { return x; }
    public int getY() { return y; }
}