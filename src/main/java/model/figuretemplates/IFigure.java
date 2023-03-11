package model.figuretemplates;

public class IFigure implements FigureTemplate {
    final int[][] shape = {{0,0,0,0}, {1,1,1,1}, {0,0,0,0}, {0,0,0,0}};
    final int size = 4;
    final int color = 0x00f0f0;

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
