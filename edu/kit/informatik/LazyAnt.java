package edu.kit.informatik;

import java.util.*;

/**
 * This class is to model lazy-ant.
 * 
 * @author EckoTan
 * @version 1.0
 */
public class LazyAnt extends Ant {

    /**
     * Constructs a lazy-ant.
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
    public LazyAnt(String name, int iPos, int jPos, Field field, int[] rule) {
        super(name, iPos, jPos, field, rule);
    }

    @Override
    public void move(int speedup, Field field, List<Ant> antList) {
        int toMove = speedup + (clock - 1);
        for (; clock <= toMove; clock++) {
            // int n = clock;
            if (clock % (speedup * speedup) == 1) {
                super.move(field, antList);
            }
        }
        // int n = clock;
    }

}
