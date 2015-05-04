package edu.kit.informatik;

import java.util.*;

/**
 * This class is to simulate this game.
 * 
 * @author EckoTan
 * @version 1.0
 */
public class Simulation {

    private List<Ant> antList;
    private Field field;
    private int speedup;

    /**
     * Constructs simulation.
     * 
     * @param length
     *            length of {@code field}
     * @param width
     *            width of {@code field}
     * @param speedup
     *            accelerate-factor
     */
    public Simulation(int length, int width, int speedup) {
        this.antList = new LinkedList<Ant>();
        this.field = new Field(length, width);
        this.speedup = speedup;
    }

    /**
     * Sort the {@code antList} according to the alphabet's sequence, in other
     * words, a is the first and z is the last.
     */
    public void sortAntList() {
        Collections.sort(this.antList);
    }

    /**
     * 
     * @return ant-list
     */
    public List<Ant> getAntList() {
        return antList;
    }

    /**
     * 
     * @return field, the ants move inside
     */
    public Field getField() {
        return field;
    }

    /**
     * Execute ({@code moves}) steps
     * 
     * @param moves
     *            number of the steps to be executed
     * @param antList
     *            list of ants
     */
    public void execute(int moves, List<Ant> antList) {
        if (!this.antList.isEmpty()) {
            for (int i = 1; i <= moves; i++) {
                for (Iterator<Ant> it = this.antList.iterator(); it.hasNext();) {
                    Ant temp = it.next();
                    try {
                        temp.move(this.speedup, this.field, this.antList);
                    } catch (IllegalArgumentException e) {
                        it.remove();
                        if (this.antList.isEmpty()) {
                            System.exit(0);
                        }
                    }
                }
            }
        } else {
            // If there isn't any ant, the program ends.
            System.exit(0);
        }
    }

    /**
     * Find ant according to the {@code name}
     * 
     * @param name
     *            name of the ant
     * @return the specified ant, if it exists
     */
    public Ant findAnt(String name) {
        if (!name.matches("[a-zA-Z]")) {
            throw new IllegalArgumentException();
        }
        if (!this.antList.isEmpty()) {
            for (Iterator<Ant> it = this.antList.iterator(); it.hasNext();) {
                Ant tempAnt = it.next();
                if (tempAnt.getName().equals(name.toLowerCase())) {
                    return tempAnt;
                }
            }
        }
        return null;
    }

    /**
     * Determine whether an ant, whose name is {@code name}, exists in
     * {@code antList}
     * 
     * @param name
     *            name of the ant
     * @return true, if it exists
     */
    public Boolean isAntInList(String name) {
        if (this.findAnt(name) != null) {
            return true;
        }
        return false;
    }

    /**
     * Determine whether an ant, whose position is ({@code iPos},{@code jPos})
     * exists in {@code antList}
     * 
     * @param iPos
     *            i-position
     * @param jPos
     *            j-position
     * @return specified ant, if it exists
     */
    public Ant isAntInList(int iPos, int jPos) {
        if ((!this.antList.isEmpty())
                && Position.isPositionValid(iPos, jPos, this.field)) {
            for (Iterator<Ant> it = this.antList.iterator(); it.hasNext();) {
                Ant tempAnt = it.next();
                if (tempAnt.getPosition().getIPos() == iPos
                        && tempAnt.getPosition().getJPos() == jPos) {
                    return tempAnt;
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param name
     *            name of the ant
     * @return the position of the specified ant, whose name is {@code name}
     */
    public String antPosition(String name) {
        if (this.findAnt(name) != null) {
            return this.findAnt(name).getPosition().toString();
        }
        return "Error, the ant with the name " + name + " doesn't exist.";
    }

    /**
     * 
     * @param name
     *            name of the ant
     * @return the direction of the specified ant, whose name is {@code name}
     */
    public String antDirection(String name) {
        if (this.findAnt(name) != null) {
            return this.findAnt(name).getDirection();
        }
        return "Error, the ant with the name " + name + " doesn't exist.";
    }

    /**
     * Remove the ant, whose name is {@code name}, if it exists
     * 
     * @param name
     *            name of the ant to be removed
     */
    public void removeAnt(String name) {
        if (this.findAnt(name) != null) {
            this.field.getGrid()[this.findAnt(name).getPosition().getIPos()][this
                    .findAnt(name).getPosition().getJPos()].hasAntChange();
            this.antList.remove(this.findAnt(name));
            if (this.antList.isEmpty()) {
                System.exit(0);
            }
        } else {
            Terminal.printLine("Error, the ant with the name " + name
                    + " doesn't exist.");
        }
    }

    /**
     * Create an ant according to the given coordinate, if {@code name} and
     * coordinate ({@code iPos}, {@code jPos}) is valid
     * 
     * @param name
     *            name of the ant to be
     * @param iPos
     *            i-position
     * @param jPos
     *            j-position
     * @param rule
     *            rule for the rotate-angle and the corresponding color
     */
    public void createAnt(String name, int iPos, int jPos, int[] rule) {
        Ant ant;
        if (this.findAnt(name) == null) {
            if (Position.isPositionValid(iPos, jPos, this.field)) {
                if (this.getField().getGrid()[iPos][jPos].isEnterable()) {
                    if (name.matches("[a-hA-H]")) {
                        ant = new StandardAnt(name, iPos, jPos, this.field,
                                rule);
                    } else if (name.matches("[i-qI-Q]")) {
                        ant = new SportAnt(name, iPos, jPos, this.field, rule);
                    } else {
                        ant = new LazyAnt(name, iPos, jPos, this.field, rule);
                    }
                    this.antList.add(ant);
                    this.field.getGrid()[iPos][jPos].hasAntChange();
                } else {
                    Terminal.printLine("Error, can not create an ant in ("
                            + iPos + "," + jPos + ").");
                }
            } else {
                Terminal.printLine("Error, the coordinate is not valid.");
            }
        } else {
            Terminal.printLine("Error, the ant with the name " + name
                    + " is alrdady exist.");
        }
    }

    /**
     * Print the ant-list
     */
    public void printAntList() {
        if (!this.antList.isEmpty()) {
            this.sortAntList();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.antList.size(); i++) {
                if (i < this.antList.size() - 1) {
                    sb.append(this.antList.get(i).getName() + ",");
                } else {
                    sb.append(this.antList.get(i).getName());
                }
            }
            Terminal.printLine(sb.toString());
        } else {
            Terminal.printLine("Error, there is no ants.");
        }
    }

    /**
     * Get the status of the cell, whose coordinate is ({@code iPos},{@code jPos}
     * )
     * 
     * @param iPos
     *            i-position
     * @param jPos
     *            j-position
     * @return <li>the ant's name, if there is an ant in the cell <li>color of
     *         the cell. if there isn't an ant in the cell and this cell is not
     *         a block <li>*, if this cell is a block and marked with "*"
     */
    public String cell(int iPos, int jPos) {
        if (Position.isPositionValid(iPos, jPos, this.field)) {
            if (this.isAntInList(iPos, jPos) != null) {
                return this.isAntInList(iPos, jPos).getName();
            }
            return this.getField().getGrid()[iPos][jPos].toString();
        }
        return "Error, the given coordinate is not valid.";
    }

    /**
     * Print the field
     */
    public void printField() {
        for (int i = 0; i < this.getField().getGrid().length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < this.getField().getGrid()[i].length; j++) {
                sb.append(this.cell(i, j));
            }
            Terminal.printLine(sb.toString());
        }
    }

}
