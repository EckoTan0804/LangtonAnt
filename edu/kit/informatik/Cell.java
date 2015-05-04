package edu.kit.informatik;

/**
 * This class is to model the cell in the grid.
 * 
 * @author EckoTan
 * @version 1.0
 */
public class Cell {

    private int color;
    private boolean hasAnt;
    private boolean isBlock;

    /**
     * Constructs a Cell
     * 
     * @param read
     *            is read from the file
     */
    public Cell(String read) {
        if (read.matches("[0-4]")) {
            this.color = Integer.parseInt(read);
        } else if (read.matches("[a-zA-Z]")) {
            this.color = 0;
            this.hasAnt = true;
        } else if (read.equals("*")) {
            this.isBlock = true;
        } else {
            Terminal.printLine("Error, the given data is not valid.");
            System.exit(1);
        }
    }

    /**
     * 
     * @return true, if there is an ant in the cell
     */
    public Boolean getHasAnt() {
        return this.hasAnt;
    }

    /**
     * 
     * @return true, if the cell is marked as a block
     */
    public Boolean getIsBlock() {
        return this.isBlock;
    }

    /**
     * 
     * @return color of the cell
     */
    public int getColor() {
        return this.color;
    }

    /**
     * If an ant leaves the cell, then change {@code hasAnt} to false; <br>
     * If an ant goes into the cell, then change {@code hasAnt} to true.
     */
    public void hasAntChange() {
        if (this.hasAnt == true) {
            this.hasAnt = false;
        } else {
            this.hasAnt = true;
        }
    }

    /**
     * 
     * @return true, if there is no ant in the cell and this cell is not a block <br>
     *         false, otherwise
     */
    public Boolean isEnterable() {
        if (this.hasAnt == false && this.isBlock == false) {
            return true;
        }
        return false;
    }

    /**
     * Change the color according to: new color = (4 * old-color + 23) mod 5
     */
    public void changeColor() {
        this.color = (4 * this.color + 23) % 5;
    }

    @Override
    public String toString() {
        if (this.isBlock == true) {
            return "*";
        } else {
            return String.valueOf(this.getColor());
        }
    }
}
