package pieces;

import java.awt.*;

public class VisitedTiles extends Piece{
    static Color colorYellowish = new Color(239, 228, 156, 255);

    public VisitedTiles(int x, int y){
        super(x, y, colorYellowish);
    }
}
