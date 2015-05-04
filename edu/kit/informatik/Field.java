package edu.kit.informatik;

/**
 * This class is to model a play-field and the ants move inside.
 * 
 * @author EckoTan
 * @version 1.0
 */
public class Field {

    // Use a two-dimensional array to simulate the grid
    private Cell[][] grid;

    /**
     * Constructs a field
     * 
     * @param length
     *            length of the field
     * @param width
     *            width of the field
     */
    public Field(int length, int width) {
        this.grid = new Cell[length][width];
    }

    /**
     * 
     * @return {@code grid} of the field
     */
    public Cell[][] getGrid() {
        return this.grid;
    }
    
    
}
