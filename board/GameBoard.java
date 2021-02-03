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
    SafeZone safeZone;
    ImpassableTerritory impassableTerritory;
    StartingCoordinate startingCoordinate;
    UnexploredTerritory unexploredTerritory;

    public static Piece[][] gameBoard;
    private Piece currentPick;
    private int numberOfSafeZones = 8;
    private int numberOfImpassableTerritories = 5;
    private int currentPositionX, currentPositionY, babaYagaRow, babaYagaCol, lastPositionX, lastPositionY;

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

        while (isBoardPieceUnexploredTerritory(currentPositionX, currentPositionY)){
                Random rand = new Random();
                int randomNumber = rand.nextInt(5);
                if(randomNumber == 3){
                    currentPick = new ImpassableTerritory(currentPositionX, currentPositionY);
                }
                else {
                    currentPick = new VisitedTiles(currentPositionX, currentPositionY);
                }
                gameBoard[currentPositionX][currentPositionY] = currentPick;
                break;
        }

        //При следващия commit ще съм направил метод, който да изкарва модален прозорец, при откриване на Баба Яга
        if (currentPositionX == babaYagaRow && currentPositionY == babaYagaCol) System.out.println("Откри ме");

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

    @Override
    public void paint(Graphics g){
        for(int row = 0; row < NUMBER_OF_SIDE_TILES; row++){
            for (int col = 0; col < NUMBER_OF_SIDE_TILES; col++){
                renderGamePiece(g, row, col);
                if(gameBoard[row][col] == null){
                    gameBoard [row][col] = new UnexploredTerritory(row, col);
                    renderGamePiece(g, row, col);
                }
                /*
                if(gameBoard[currentPositionX][currentPositionY] != null && isPositionYouClickedRight(currentPositionX, currentPositionY)){
                    if((currentPositionX >= 1 && 6 <= currentPositionX) && (currentPositionY >= 1 && 6 <= currentPositionY)){
                        gameBoard[currentPositionX + 1][currentPositionY].drawingXOnTiles(g);
                        gameBoard[currentPositionX - 1][currentPositionY].drawingXOnTiles(g);
                        gameBoard[currentPositionX][currentPositionY + 1].drawingXOnTiles(g);
                        gameBoard[currentPositionX][currentPositionY - 1].drawingXOnTiles(g);
                    }
                }
                 */
            }
        }
        gameBoard[3][4].drawingQuestionMarkOnTiles(g);
    }

    private void startingPosition(){
        int row = rand.nextInt(2);
        int col = rand.nextInt(2);

        if(row == 1) row = 7;
        if(col == 1) col = 7;

        gameBoard[row][col] = new StartingCoordinate(row, col);
    }

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

    private boolean isPositionYouClickedRight(int row, int col){
        return isBoardPieceStartingPosition(row, col) || isBoardPieceVisitedTile(row, col);
    }

    //TODO: Да се довърши!
    private void youCantMoveAnymore(int row, int col){
        boolean isRowInTheBoard = (row >= 0 && 8 > row);
        boolean isColInTheBoard = (col >= 0 && 8 > col);
        boolean positioningIsInTheBoard = isRowInTheBoard && isColInTheBoard;
    }

    private void positionsYouCanGoTo(int row, int col){

    }
}
