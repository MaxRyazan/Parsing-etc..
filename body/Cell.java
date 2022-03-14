package Ylab.Game_Lesson2.body;

public final class Cell {
    private final int row;
    private final int col;
    private int cellNumber;

    public Cell(final int row, final int col) {
        this.row = row;
        this.col = col;
    }

    public Cell(int row, int col, int cellNumber) {
        this.row = row;
        this.col = col;
        this.cellNumber = cellNumber;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getCellNumber() {return cellNumber;}

}