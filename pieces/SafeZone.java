package pieces;

import java.awt.*;

public class SafeZone extends Piece{
    static Color colorGreen = new Color(115, 238, 86);

    public SafeZone(int x, int y){
        super(x, y, colorGreen);
    }
}
