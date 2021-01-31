package pieces;

import java.awt.*;

public class ImpassableTerritory extends Piece{
    static Color colorBlue = new Color(31, 73, 248, 255);

    public ImpassableTerritory(int x, int y){
        super(x, y, colorBlue);
    }
}
