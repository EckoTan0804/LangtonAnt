package edu.kit.informatik;

import java.util.*;

/**
 * This class is to model ant.
 * 
 * @author EckoTan
 * @version 1.0
 */
public abstract class Ant implements Comparable<Ant> {

    /**
     * Use a clock to count the steps.
     */
    protected static int clock = 1;

    private String name;
    private Position position;
    private int direction;
    private int[] rule;

    /**
     * Constructs an ant.
     * 
     * @param name
     *            name of the ant, is read from the file
     * @param iPos
     *            i-position of the ant
     * @param jPos
     *            j-position of the ant
     * @param field
     *            ants move inside
     * @param rule
     *            rule for the rotate-angle and the corresponding color
     */
    public Ant(String name, int iPos, int jPos, Field field, int[] rule) {
        if (name.matches("[A-Z]")) {
            this.name = name.toLowerCase();
            this.direction = 0;
        } else if (name.matches("[a-z]")) {
            this.name = name;
            this.direction = 180;
        } else {
            Terminal.printLine("Error, the name of the ant is not valid.");
            System.exit(1);
        }
        try {
            this.setPosition(iPos, jPos, field);
            this.setRule(rule);
        } catch (IllegalArgumentException e) {
            Terminal.printLine(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * 
     * @return name of the ant
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     * @return direction of the ant
     */
    public String getDirection() {
        int result = (this.direction / 45) % 8;
        switch (result) {
        case 0:
            return "N";
        case 1:
            return "NO";
        case 2:
            return "O";
        case 3:
            return "SO";
        case 4:
            return "S";
        case 5:
            return "SW";
        case 6:
            return "W";
        default:
            return "NW";
        }
    }

    /**
     * 
     * @param field
     *            field, the ants move inside
     * 
     */
    private void changeDirection(Field field) {
        int oldDirection = this.direction;
        int i = this.getPosition().getIPos();
        int j = this.getPosition().getJPos();
        int color = field.getGrid()[i][j].getColor();
        int change = this.rule[color];
        this.direction = oldDirection + change;
    }

    /**
     * 
     * @param iPos
     *            i-position to be set
     * @param jPos
     *            j-position to be set
     * @param field
     *            field, the ants move inside
     * @throws IllegalArgumentException
     *             if the given {@code iPos} and {@code jPos} doesn't match the
     *             conditions
     */
    public void setPosition(int iPos, int jPos, Field field)
            throws IllegalArgumentException {
        this.position = new Position(iPos, jPos, field);
    }

    /**
     * 
     * @return position of the ant
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Set the rule of the rotation for the ant.
     * 
     * @param rule
     *            is a array with 5 elements, contains the relationship between
     *            cell's color and the rotation-angle of the ant
     * 
     * @throws IllegalArgumentException
     *             if the length of {@code rule} is not 5 and the element of
     *             {@code rule} modulo 45 doesn't equal 0
     */
    private void setRule(int[] rule) throws IllegalArgumentException {
        if (rule.length != 5) {
            throw new IllegalArgumentException(
                    "Error, the given rule is not valid.");
        }
        for (int i = 0; i < rule.length; i++) {
            if (rule[i] != 45 && rule[i] != 90 && rule[i] != 270
                    && rule[i] != 315) {
                throw new IllegalArgumentException(
                        "Error, the given rule is not valid.");
            }
        }
        this.rule = rule;
    }

    /**
     * How the ants move in a step
     * 
     * @param field
     *            field, the ants move inside
     * @param antList
     *            list of the ants
     */
    protected void move(Field field, List<Ant> antList) {
        int oldIPos = this.getPosition().getIPos();
        int oldJPos = this.getPosition().getJPos();
        int newIPos = 0;
        int newJPos = 0;
        // Calculate the new coordinate according to the direction and the
        // current coordinate
        switch (this.getDirection()) {
        case "N":
            newIPos = oldIPos - 1;
            newJPos = oldJPos;
            break;
        case "NO":
            newIPos = oldIPos - 1;
            newJPos = oldJPos + 1;
            break;
        case "O":
            newIPos = oldIPos;
            newJPos = oldJPos + 1;
            break;
        case "SO":
            newIPos = oldIPos + 1;
            newJPos = oldJPos + 1;
            break;
        case "S":
            newIPos = oldIPos + 1;
            newJPos = oldJPos;
            break;
        case "SW":
            newIPos = oldIPos + 1;
            newJPos = oldJPos - 1;
            break;
        case "W":
            newIPos = oldIPos;
            newJPos = oldJPos - 1;
            break;
        default:
            newIPos = oldIPos - 1;
            newJPos = oldJPos - 1;
            break;
        }
        // If new coordinate is valid and the cell in new coordinate can be
        // entered, the ant's coordinate will be changed to the new coordinate.
        // If new coordinate isn't valid, the ant's coordinate won't be changed.
        if (Position.isPositionValid(newIPos, newJPos, field)) {
            if (field.getGrid()[newIPos][newJPos].isEnterable()) {
                this.setPosition(newIPos, newJPos, field);
                field.getGrid()[oldIPos][oldJPos].hasAntChange();
                field.getGrid()[newIPos][newJPos].hasAntChange();
                // this.changeDirection(field);
                // field.getGrid()[this.getPosition().getIPos()][this
                // .getPosition().getJPos()].changeColor();
            }
            this.changeDirection(field);
            field.getGrid()[this.getPosition().getIPos()][this.getPosition()
                    .getJPos()].changeColor();

        } else {
            field.getGrid()[oldIPos][oldJPos].hasAntChange();
            throw new IllegalArgumentException();
        }

    }

    /**
     * Different types of ants have their own frequency to move.
     * 
     * @param speedup
     *            accelerate-factor
     * @param field
     *            field, ants move inside
     * @param antList
     *            list of the ants
     */
    public abstract void move(int speedup, Field field, List<Ant> antList);

    @Override
    public int compareTo(Ant o) {
        if (this.getName().compareTo(o.getName()) < 0) {
            return -1;
        } else if (this.getName().compareTo(o.getName()) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
