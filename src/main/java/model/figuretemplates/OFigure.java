package model.figuretemplates;

public class OFigure implements FigureTemplate {
    final int[][] shape = {{0,0,0,0}, {0,1,1,0}, {0,1,1,0}, {0,0,0,0}};
    final int size = 4;
    final int color = 0xf0f000;

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
