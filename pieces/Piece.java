package pieces;

import java.awt.*;

public abstract class Piece {
    public final int TILE_SIDE_SIZE = 100;
    final int UPPER_BAR_SIZE = 30;
    private int xCoordinate;
    private int yCoordinate;
    private Color currentColor;

    public Piece(int x, int y, Color color){
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.currentColor = color;
    }

    public void renderPiece(Graphics g){
        int x = xCoordinate * TILE_SIDE_SIZE;
        int y = yCoordinate * TILE_SIDE_SIZE;

        g.setColor(currentColor);
        g.fillRect(x, y + UPPER_BAR_SIZE, TILE_SIDE_SIZE, TILE_SIDE_SIZE);

        g.setColor(Color.WHITE);
        g.drawRect(x, y + UPPER_BAR_SIZE, TILE_SIDE_SIZE, TILE_SIDE_SIZE);
    }
}
