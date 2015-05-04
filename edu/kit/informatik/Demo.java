package edu.kit.informatik;

import java.util.Collections;

/**
 * This class is to test the simulation.
 * 
 * @author EckoTan
 * @version 1.0
 */
public class Demo {

    private static final String INVALID_CONTENT_ERROR = "Error, the content of the file is not valid.";
    private static final String INVALID_ORDER_ERROR = "Error, the given order is not valid.";

    private Simulation simulation;

    /**
     * Main method
     * 
     * @param args
     *            arguments of the program
     */
    public static void main(String[] args) {
        int[] rule = {270, 90, 315, 45, 90};
        int speedup = 2;
        Demo demo = new Demo();
        demo.isArgsValid(args);
        if (args.length == 2) {
            if (args[1].matches("speedup=[0-9]+")) {
                speedup = demo.readThirdArg(args[1]);
            } else {
            rule = demo.readSecondArg(args[1]);
            }
        }
        if (args.length == 3) {
            rule = demo.readSecondArg(args[1]);
            speedup = demo.readThirdArg(args[2]);
        }

        demo.readFirstArg(args[0], rule, speedup);
        demo.readOrder(rule);

    }

    // TODO change public to private

    /**
     * Determine whether the arguments of the program is valid or not
     * 
     * @param args
     *            arguments of the program
     */
    private void isArgsValid(String[] args) {
        if (args.length != 1 && args.length != 2 && args.length != 3) {
            Terminal.printLine("Error, the argument of the programm is not valid.");
            System.exit(1);
        }
    }

    /**
     * 
     * @param fileAddress
     *            address of the test file
     * @param rule
     *            rule for the rotate-angle and the corresponding color
     * @param speedup
     *            accelerate-factor
     */
    private void readFirstArg(String fileAddress, int[] rule, int speedup) {
        String[] file = FileInputHelper.read(fileAddress);
        int length = file.length;
        int width = file[0].length();
        for (int i = 1; i < file.length; i++) {
            if (file[i].length() != width) {
                Terminal.printLine("Error, the content of the file is not valid.");
                System.exit(1);
            }
        }
        this.simulation = new Simulation(length, width, speedup);

        for (int j = 0; j < file.length; j++) {
            for (int k = 0; k < file[j].length(); k++) {
                char c = file[j].charAt(k);
                String info = String.valueOf(c);
                if (info.matches("[a-zA-Z]")) {
                    if (!this.simulation.isAntInList(info)) {
                        if (info.matches("[a-hA-H]")) {
                            Ant standard = new StandardAnt(info, j, k,
                                    simulation.getField(), rule);
                            simulation.getAntList().add(standard);
                            simulation.getField().getGrid()[j][k] = new Cell(
                                    info);
                        } else if (info.matches("[i-qI-Q]")) {
                            Ant sport = new SportAnt(info, j, k,
                                    simulation.getField(), rule);
                            simulation.getAntList().add(sport);
                            simulation.getField().getGrid()[j][k] = new Cell(
                                    info);
                        } else if (info.matches("[r-zR-Z]")) {
                            Ant lazy = new LazyAnt(info, j, k,
                                    simulation.getField(), rule);
                            simulation.getAntList().add(lazy);
                            simulation.getField().getGrid()[j][k] = new Cell(
                                    info);
                        }
                        Collections.sort(this.simulation.getAntList());
                    } else {
                        Terminal.printLine(INVALID_CONTENT_ERROR);
                        System.exit(1);
                    }
                } else if (info.matches("[0-4]") || info.equals("*")) {
                    simulation.getField().getGrid()[j][k] = new Cell(info);
                } else {
                    Terminal.printLine(INVALID_CONTENT_ERROR);
                    System.exit(1);
                }
            }
        }
    }

    /**
     * 
     * @param secondArg
     *            second argument of the program
     * @return a array that contains the relationship between the color and the
     *         rotate-angle
     */
    private int[] readSecondArg(String secondArg) {
        String[] argArr = secondArg.split("=");
        if (argArr.length != 2 || (!argArr[0].equals("rule"))) {
            Terminal.printLine("Error, the second argument is not valid.");
            System.exit(1);
        }
        String[] ruleArr = argArr[1].split("-");
        int[] rule = new int[5];
        try {
            if (ruleArr.length != 5) {
                throw new NumberFormatException();
            }
            for (int i = 0; i < rule.length; i++) {
                rule[i] = Integer.parseInt(ruleArr[i]);
                if (rule[i] != 45 && rule[i] != 90 && rule[i] != 270
                        && rule[i] != 315) {
                    throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException e) {
            Terminal.printLine("Error, the second argument is not valid.");
            System.exit(1);
        }
        return rule;

    }

    /**
     * 
     * @param thirdArg
     *            third argument of the program
     * @return accelerate-factor
     */
    private int readThirdArg(String thirdArg) {
        int speedup = 2;
        String[] arg = thirdArg.split("=");
        if (arg.length != 2 || (!arg[0].equals("speedup"))) {
            Terminal.printLine("Error, the third argument is not valid.");
            System.exit(1);
        }

        try {
            speedup = Integer.parseInt(arg[1]);
            if (speedup < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Terminal.printLine("Error, the third argument is not valid.");
            System.exit(1);
        }

        return speedup;
    }

    /**
     * Help method for readOrder
     * 
     * @param order
     *            order
     * @param expected
     *            expected length of {@code order}
     */
    private void isOrderValid(String[] order, int expected) {
        if (order.length != expected) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Read order
     * 
     * @param rule
     *            rule for the rotate-angle and the corresponding color
     */
    private void readOrder(int[] rule) {
        String input = null;
        while ((input = Terminal.readLine()) != null) {
            String[] order = input.trim().split(" ");
            try {
                switch (order[0]) {
                case "move":
                    this.isOrderValid(order, 2);
                    if (Integer.parseInt(order[1]) >= 0) {
                        this.simulation.execute(Integer.parseInt(order[1]),
                                this.simulation.getAntList());
                    } else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case "print":
                    isOrderValid(order, 1);
                    this.simulation.printField();
                    break;
                case "position":
                    isOrderValid(order, 2);
                    Terminal.printLine(this.simulation.antPosition(order[1]));
                    break;
                case "field":
                    isOrderValid(order, 2);
                    String[] coordinate = order[1].split(",", 2);
                    Terminal.printLine(this.simulation.cell(
                            Integer.parseInt(coordinate[0]),
                            Integer.parseInt(coordinate[1])));
                    break;
                case "direction":
                    isOrderValid(order, 2);
                    Terminal.printLine(this.simulation.antDirection(order[1]));
                    break;
                case "ant":
                    isOrderValid(order, 1);
                    this.simulation.printAntList();
                    break;
                case "create":
                    isOrderValid(order, 2);
                    String[] create = order[1].split(",");
                    isOrderValid(create, 3);
                    this.simulation.createAnt(create[0],
                            Integer.parseInt(create[1]),
                            Integer.parseInt(create[2]), rule);
                    break;
                case "escape":
                    isOrderValid(order, 2);
                    this.simulation.removeAnt(order[1]);
                    break;
                case "quit":
                    isOrderValid(order, 1);
                    System.exit(0);
                    break;
                default:
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                Terminal.printLine(INVALID_ORDER_ERROR);
                continue;
            }
        }
    }
}
