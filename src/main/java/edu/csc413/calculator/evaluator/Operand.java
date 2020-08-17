package edu.csc413.calculator.evaluator;

/**
 * Operand class used to represent an operand
 * in a valid mathematical expression.
 */
public class Operand {

    String token;
    int num;
    /**
     * construct operand from string token.
     */
    public Operand(String token) {
     this.num = Integer.parseInt(token);
    }

    /**
     * construct operand from integer
     */
    public Operand(int value) {
        this.num = value;
    }

    /**
     * return value of operand
     */
    public int getValue() {
        return this.num;
    }

    /**
     * Check to see if given token is a valid
     * operand.
     */
    public static boolean check(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
