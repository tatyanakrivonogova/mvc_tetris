package model.figuretemplates;

public class SFigure implements FigureTemplate {
    final int[][] shape = {{0,1,1,0}, {1,1,0,0}, {0,0,0,0}, {0,0,0,0}};
    final int size = 3;
    final int color = 0x00f000;

    @Override
    public int[][] getShape() {
        return shape;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getColor() {
        return color;
    }
}
