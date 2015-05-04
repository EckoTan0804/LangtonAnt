package edu.kit.informatik;

import java.util.List;

/**
 * This class is to model sport-ant.
 * 
 * @author EckoTan
 * @version 1.0
 */
public class SportAnt extends Ant {

    /**
     * Constructs a sport-ant.
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
    public SportAnt(String name, int iPos, int jPos, Field field, int[] rule) {
        super(name, iPos, jPos, field, rule);
    }

    @Override
    public void move(int speedup, Field field, List<Ant> antList) {
        int toMove = speedup + (clock - 1);
        for (; clock <= toMove; clock++) {
            // int n = clock;
            super.move(field, antList);
        }
        // int n = clock;

    }

}
