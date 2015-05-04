package edu.kit.informatik;

/**
 * This class is to model the position of the ant.
 * 
 * @author EckoTan
 * @version 1.0
 */
public class Position {

    /**
     * line-number
     */
    private int iPos;
    /**
     * column-number
     */
    private int jPos;

    /**
     * Constructs the position of the ant.
     * 
     * @param iPos
     *            i-position
     * @param jPos
     *            j-position
     * @param field
     *            field, the ants move inside
     * @throws IllegalArgumentException
     *             if the given {@code iPos} and {@code jPos} don't match the
     *             conditions
     */
    public Position(int iPos, int jPos, Field field)
            throws IllegalArgumentException {
        this.setIPos(iPos, field.getGrid().length);
        this.setJPos(jPos, field.getGrid()[iPos].length);
    }

    /**
     * 
     * @return i-position
     */
    public int getIPos() {
        return this.iPos;
    }

    /**
     * 
     * @param iPos
     *            i-position to be set
     * @param length
     *            length of the field
     * @throws IllegalArgumentException
     *             if the given {@code iPos} doesn't match the condition
     */
    public void setIPos(int iPos, int length) throws IllegalArgumentException {
        if (iPos >= 0 && iPos <= length - 1) {
            this.iPos = iPos;
        } else {
            throw new IllegalArgumentException(
                    "Error, the given coordinate is not valid.");
        }
    }

    /**
     * 
     * @return j-position
     */
    public int getJPos() {
        return jPos;
    }

    /**
     * 
     * @param jPos
     *            j-position to be set
     * @param width
     *            width of the field
     * 
     * @throws IllegalArgumentException
     *             if the given {@code jPos} doesn't match the condition
     */
    public void setJPos(int jPos, int width) throws IllegalArgumentException {
        if (jPos >= 0 && jPos <= width - 1) {
            this.jPos = jPos;
        } else {
            throw new IllegalArgumentException(
                    "Error, the given coordinate is not valid.");
        }
    }

    /**
     * Determine whether the position ({@code iPos}, {@code jPos}) is valid or
     * not
     * 
     * @param iPos
     *            i-position
     * @param jPos
     *            j-position
     * @param field
     *            field, the ants move inside
     * @return true, if ({@code iPos}, {@code jPos}) matches the conditions <br>
     *         false, otherwise
     */
    public static Boolean isPositionValid(int iPos, int jPos, Field field) {
        if (iPos >= 0 && iPos <= field.getGrid().length - 1) {
            if (jPos >= 0 && jPos <= field.getGrid()[iPos].length - 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getIPos() + "," + this.getJPos();
    }

}
