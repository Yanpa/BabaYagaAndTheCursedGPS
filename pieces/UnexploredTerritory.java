package pieces;

import java.awt.*;

public class UnexploredTerritory extends Piece{
    static Color colorRed = new Color(250, 77, 77);

    public UnexploredTerritory(int x, int y) {
        super(x, y, colorRed);
    }
}
