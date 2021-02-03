package pieces;

import java.awt.*;

public abstract class Piece {
    public final int TILE_SIDE_SIZE = 100;
    final int UPPER_BAR_SIZE = 30;
    private int xCoordinate;
    private int yCoordinate;
    private Color currentColor;
    int x;
    int y;

    public Piece(){

    }

    public Piece(int x, int y, Color color){
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.currentColor = color;

        this.x = xCoordinate * TILE_SIDE_SIZE;
        this.y = yCoordinate * TILE_SIDE_SIZE;
    }

    public void renderPiece(Graphics g){
        g.setColor(currentColor);
        g.fillRect(x, y + UPPER_BAR_SIZE, TILE_SIDE_SIZE, TILE_SIDE_SIZE);

        g.setColor(Color.WHITE);
        g.drawRect(x, y + UPPER_BAR_SIZE, TILE_SIDE_SIZE, TILE_SIDE_SIZE);
    }

    public void drawingXOnTiles(Graphics g){
        g.setColor(Color.BLUE);
        g.setFont(g.getFont().deriveFont(42f));

        g.drawString("X", x + 35, y + UPPER_BAR_SIZE + 60);
    }

    public void drawingQuestionMarkOnTiles(Graphics g){
        g.setFont(g.getFont().deriveFont(42f));
        this.currentColor = new Color(239, 228, 156, 255);
        renderPiece(g);
        g.setColor(new Color(57, 14, 95));
        g.drawString("?", x + 40, y + UPPER_BAR_SIZE + 60);
    }
}
