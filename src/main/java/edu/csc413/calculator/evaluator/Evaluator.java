package edu.csc413.calculator.evaluator;



import edu.csc413.calculator.exceptions.InvalidTokenException;
import edu.csc413.calculator.operators.*;
import edu.csc413.calculator.operators.Operator;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {

  private Stack<Operand> operandStack; //Hold objects
  private Stack<Operator> operatorStack;
  private StringTokenizer expressionTokenizer;// Takes a string and break it in smaller pieces
  private final String delimiters = " +/*-^()";

  //Constructor creates the object.
  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
  }

  public int evaluateExpression(String expression) throws InvalidTokenException {
    String expressionToken;

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.expressionTokenizer = new StringTokenizer(expression, this.delimiters, true);

    // initialize operator stack - necessary with operator priority schema
    // the priority of any operator in the operator stack other than
    // the usual mathematical operators - "+-*/" - should be less than the priority
    // of the usual operators


    while (this.expressionTokenizer.hasMoreTokens()) {

      if (!(expressionToken = this.expressionTokenizer.nextToken()).equals(" ")) {
        if (Operand.check(expressionToken)) {
          operandStack.push(new Operand(expressionToken));
        } else {
            if (!Operator.check(expressionToken) && !Operand.check(expressionToken)) {
              System.out.println("Invalid token*");
              throw new InvalidTokenException("Invalid token");
            } else if (")".equals(expressionToken)) {
              while (!(operatorStack.peek() instanceof LeftParenthesisOperator)) {
                process_operator();
              }
              operatorStack.pop();
            }  else if ("(".equals(expressionToken)) {
              operatorStack.push(new LeftParenthesisOperator());
            } else if (Operator.check(expressionToken)) {
                Operator newOperator = Operator.getOperator(expressionToken);
                if(operatorStack.size() == 0){
                    operatorStack.push(newOperator);
                } else if (operatorStack.size() != 0 &&
                        operatorStack.peek().priority() < newOperator.priority()) {
                  operatorStack.push(newOperator);
                } else {
                  while(operatorStack.size() != 0 && operatorStack.peek().priority() >= newOperator.priority()) {
                      process_operator();
                  }
                  operatorStack.push(newOperator);
                }
            }
        }
      }
    }

    while(!operatorStack.isEmpty()){
      process_operator();
    }

    return operandStack.peek().getValue();
  }

  public void process_operator(){
    Operator operatorFromStack = operatorStack.pop();
    Operand operandTwo = operandStack.pop();
    Operand operandOne = operandStack.pop();
    Operand result = operatorFromStack.execute(operandOne, operandTwo);
    operandStack.push(result);
  }


}






