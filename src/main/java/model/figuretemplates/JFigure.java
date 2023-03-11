package model.figuretemplates;

public class JFigure implements FigureTemplate {
    final int[][] shape = {{1,0,0,0}, {1,1,1,0}, {0,0,0,0}, {0,0,0,0}};
    final int size = 3;
    final int color = 0x0000f0;

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
