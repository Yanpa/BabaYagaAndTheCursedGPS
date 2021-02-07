package board;

import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class GameBoard extends JFrame implements MouseListener {
    public final int NUMBER_OF_SIDE_TILES = 8;
    Random rand = new Random();

    public static Piece[][] gameBoard;
    private Piece currentPick;
    private int numberOfSafeZones = 8;
    private int numberOfImpassableTerritories = 5;
    private int currentPositionX, currentPositionY, babaYagaRow, babaYagaCol;
    private boolean youClickedYellowishTile, youClickedRedTile, youClickedGreenTile;

    /**
     * Constructor that creates the window for the game to be played
     */
    public GameBoard(){
        this.gameBoard = new Piece[NUMBER_OF_SIDE_TILES][NUMBER_OF_SIDE_TILES];
        this.startingPosition();
        this.impassableTerritoriesPosition();
        this.safeZonesPosition();

        this.setSize(800, 830);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / 100;
        int y = (e.getY() - 30)/ 100;
        currentPositionX = x;
        currentPositionY = y;

        youClickedYellowishTile = isPositionYouClickedYellowish(currentPositionX, currentPositionY);
        youClickedRedTile = isBoardPieceUnexploredTerritory(currentPositionX, currentPositionY);
        youClickedGreenTile = isBoardPieceSafeZone(currentPositionX, currentPositionY);

        if (currentPositionX == babaYagaRow && currentPositionY == babaYagaCol) {
            Modal modal = new Modal(this, "Winner Winner Chicken Dinner", "Откри баба Яга и вече си свободен");
        }

        boolean endOfTheGame = youCantMoveAnymore();
        if(endOfTheGame){
            Modal modal = new Modal("Losing streak", "Сблъска се с непреодолима пречка", this);
            if(Modal.isClicked){
                new GameBoard();
            }
        }
        this.repaint();
    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Painting the whole board
     * @param g
     */
    @Override
    public void paint(Graphics g){
        for(int row = 0; row < NUMBER_OF_SIDE_TILES; row++){
            for (int col = 0; col < NUMBER_OF_SIDE_TILES; col++){
                renderGamePiece(g, row, col);
                if(gameBoard[row][col] == null){
                    gameBoard [row][col] = new UnexploredTerritory(row, col);
                    renderGamePiece(g, row, col);
                }
            }
        }
        if(youClickedYellowishTile) {
            positionsYouCanGoTo(g, currentPositionX, currentPositionY);
        }
        else if (currentPositionX != babaYagaRow && currentPositionY != babaYagaCol){
            twentyPercentChanceToBeStuck(currentPositionX, currentPositionY);
        }
        if(youClickedGreenTile || youClickedRedTile) {
            gameBoard[currentPositionX][currentPositionY].drawingQuestionMarkOnTiles(g);
            gameBoard[currentPositionX][currentPositionY].questionMarkIsDrawn = true;
            if(gameBoard[currentPositionX][currentPositionY].questionMarkIsDrawn){
                twentyPercentChanceToBeStuck(currentPositionX, currentPositionY);
            }
        }
    }

    /**
     * Creating the starting position in one of the four corners
     */
    private void startingPosition(){
        int row = rand.nextInt(2);
        int col = rand.nextInt(2);

        if(row == 1) row = 7;
        if(col == 1) col = 7;

        gameBoard[row][col] = new StartingCoordinate(row, col);
    }

    /**
     * Creating and putting all the safe zones, as well babaYaga on the board on random positions
     */
    private void safeZonesPosition(){
        int row, col;
        int positionOfBabaYaga = rand.nextInt(numberOfSafeZones);
        while(numberOfSafeZones != 0){
            row = rand.nextInt(8);
            col = rand.nextInt(8);

            if(positionOfBabaYaga == numberOfSafeZones){
                babaYagaRow = row;
                babaYagaCol = col;
            }
            if(gameBoard[row][col] == null){
                gameBoard[row][col] = new SafeZone(row, col);
                numberOfSafeZones--;
            }
        }
    }

    /**
     * Creating and putting all the blue tiles on random
     */
    private void impassableTerritoriesPosition(){
        int row, col;
        while (numberOfImpassableTerritories != 0){
            row = rand.nextInt(8);
            col = rand.nextInt(8);

            if(gameBoard[row][col] == null){
                gameBoard[row][col] = new ImpassableTerritory(row, col);
                numberOfImpassableTerritories--;
            }
        }
    }

    /**
     * Giving the 20% chance to create an Impassable territory
     * @param row
     * @param col
     */
    private void twentyPercentChanceToBeStuck(int row, int col){
        while ((youClickedRedTile || youClickedGreenTile) && gameBoard[row][col].questionMarkIsDrawn){
            Random rand = new Random();
            int randomNumber = rand.nextInt(5);
            if(randomNumber == 3){
                currentPick = new ImpassableTerritory(row, col);
            }
            else {
                currentPick = new VisitedTiles(row, col);
            }
            gameBoard[row][col] = currentPick;
            gameBoard[row][col].questionMarkIsDrawn = false;
            break;
        }
    }

    private Piece getBoardPiece(int row, int col) {
        return this.gameBoard[row][col];
    }

    private boolean hasBoardPiece(int row, int col) {
        return this.getBoardPiece(row, col) != null;
    }

    private void renderGamePiece(Graphics g, int row, int col) {
        if(this.hasBoardPiece(row, col)) {
            Piece p = this.getBoardPiece(row, col);
            p.renderPiece(g);
        }
    }

    /**
     * The next 6 methods share the same property and it's that the identify the class, by the given name
     * @param row
     * @param col
     * @return
     */
    private boolean isBoardPieceStartingPosition(int row, int col){
        return getBoardPiece(row, col).getClass().getSimpleName().equals("StartingCoordinate");
    }

    private boolean isBoardPieceVisitedTile(int row, int col){
        return getBoardPiece(row, col).getClass().getSimpleName().equals("VisitedTiles");
    }

    private boolean isBoardPieceSafeZone(int row, int col){
        return getBoardPiece(row, col).getClass().getSimpleName().equals("SafeZone");
    }

    private boolean isBoardPieceImpassableTerritory(int row, int col){
        return getBoardPiece(row, col).getClass().getSimpleName().equals("ImpassableTerritory");
    }

    private boolean isBoardPieceUnexploredTerritory(int row, int col){
        return getBoardPiece(row, col).getClass().getSimpleName().equals("UnexploredTerritory");
    }

    private boolean isPositionYouClickedYellowish(int row, int col){
        return isBoardPieceStartingPosition(row, col) || isBoardPieceVisitedTile(row, col);
    }

    /**
     * Takes the clicked tile and shows the user the possible moves he can make marking them with an "X"
     * @param g Graphics
     * @param row current row
     * @param col current col
     */
    private void positionsYouCanGoTo(Graphics g, int row, int col){
        boolean thereIsTerritoryDown = (row + 1 < 8);
        boolean thereIsTerritoryUp = (row - 1 >= 0);
        boolean thereIsTerritoryRight = (col + 1 < 8);
        boolean thereIsTerritoryLeft = (col - 1 >= 0);
        if(thereIsTerritoryDown && (isBoardPieceUnexploredTerritory(row + 1, col) || isBoardPieceSafeZone(row + 1, col))){
            gameBoard[row + 1][col].drawingXOnTiles(g);
        }
        if(thereIsTerritoryLeft && (isBoardPieceUnexploredTerritory(row, col - 1) || isBoardPieceSafeZone(row, col - 1))){
            gameBoard[row][col - 1].drawingXOnTiles(g);
        }
        if(thereIsTerritoryUp && (isBoardPieceUnexploredTerritory(row - 1, col) || isBoardPieceSafeZone(row - 1, col))){
            gameBoard[row - 1][col].drawingXOnTiles(g);
        }
        if(thereIsTerritoryRight && (isBoardPieceUnexploredTerritory(row, col + 1) || isBoardPieceSafeZone(row, col + 1))){
            gameBoard[row][col + 1].drawingXOnTiles(g);
        }
    }

    /**
     * This massive method runs tru the whole game board to see if there is more possible moves to be done
     * @return Value of true or false so the program knows when the game is over
     */
    private boolean youCantMoveAnymore(){
        int countOfYellowTiles = 0, countOfImmovableYellowTiles = 0;
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                boolean thereIsTerritoryDown = (row + 1 < 8);
                boolean thereIsTerritoryUp = (row - 1 >= 0);
                boolean thereIsTerritoryRight = (col + 1 < 8);
                boolean thereIsTerritoryLeft = (col - 1 >= 0);
                boolean territoryDownIsImpassable = thereIsTerritoryDown && (isBoardPieceImpassableTerritory(row + 1, col) || isPositionYouClickedYellowish(row + 1, col));
                boolean territoryLeftIsImpassable = thereIsTerritoryLeft && (isBoardPieceImpassableTerritory(row, col - 1) || isPositionYouClickedYellowish(row, col - 1));
                boolean territoryUpIsImpassable = thereIsTerritoryUp && (isBoardPieceImpassableTerritory(row - 1, col) || isPositionYouClickedYellowish(row - 1, col));
                boolean territoryRightIsImpassable = thereIsTerritoryRight && (isBoardPieceImpassableTerritory(row, col + 1) || isPositionYouClickedYellowish(row, col + 1));

                if(isPositionYouClickedYellowish(row, col)){
                    countOfYellowTiles++;
                }
                if(isPositionYouClickedYellowish(row, col) && !((row == 0) || (col == 0)) && territoryDownIsImpassable && territoryLeftIsImpassable && territoryUpIsImpassable && territoryRightIsImpassable){
                    countOfImmovableYellowTiles++;
                }
                if(row == 0 && territoryDownIsImpassable && territoryLeftIsImpassable && territoryRightIsImpassable){
                    countOfImmovableYellowTiles++;
                }
                if(col == 0 && territoryDownIsImpassable && territoryRightIsImpassable && territoryUpIsImpassable){
                    countOfImmovableYellowTiles++;
                }
                if(row == 7 && territoryLeftIsImpassable && territoryUpIsImpassable && territoryRightIsImpassable){
                    countOfImmovableYellowTiles++;
                }
                if(col == 7 && territoryDownIsImpassable && territoryLeftIsImpassable && territoryUpIsImpassable){
                    countOfImmovableYellowTiles++;
                }
            }
        }
        return countOfYellowTiles == countOfImmovableYellowTiles;
    }
}
