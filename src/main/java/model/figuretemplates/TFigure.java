package model.figuretemplates;

public class TFigure implements FigureTemplate {
    final int[][] shape = {{1,1,1,0}, {0,1,0,0}, {0,0,0,0}, {0,0,0,0}};
    final int size = 3;
    final int color = 0xa000f0;

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
