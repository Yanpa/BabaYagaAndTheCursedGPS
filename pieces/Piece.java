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
    public boolean questionMarkIsDrawn;

    public Piece(){

    }

    /**
     * Constructor that creates peace based on x and y coordinates, also setting the color of the tile
     * @param x
     * @param y
     * @param color
     */
    public Piece(int x, int y, Color color){
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.currentColor = color;

        this.x = xCoordinate * TILE_SIDE_SIZE;
        this.y = yCoordinate * TILE_SIDE_SIZE;
    }

    /**
     * Simply rendering the piece
     * @param g
     */
    public void renderPiece(Graphics g){
        g.setColor(currentColor);
        g.fillRect(x, y + UPPER_BAR_SIZE, TILE_SIDE_SIZE, TILE_SIDE_SIZE);

        g.setColor(Color.WHITE);
        g.drawRect(x, y + UPPER_BAR_SIZE, TILE_SIDE_SIZE, TILE_SIDE_SIZE);
    }

    /**
     * Drawing a "X" symbol on the tile
     * @param g
     */
    public void drawingXOnTiles(Graphics g){
        g.setColor(Color.BLUE);
        g.setFont(g.getFont().deriveFont(42f));

        g.drawString("X", x + 35, y + UPPER_BAR_SIZE + 60);
    }

    /**
     * Drawing a "?" on the tile and changing its color so you know its the chosen one
     * @param g
     */
    public void drawingQuestionMarkOnTiles(Graphics g){
        g.setFont(g.getFont().deriveFont(42f));
        this.currentColor = new Color(239, 228, 156, 255);
        renderPiece(g);
        g.setColor(new Color(57, 14, 95));
        g.drawString("?", x + 40, y + UPPER_BAR_SIZE + 60);
    }
}
