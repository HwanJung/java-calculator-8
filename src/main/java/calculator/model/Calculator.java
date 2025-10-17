package calculator.model;

import java.util.List;

public class Calculator {
    private Parser parser;

    public Calculator(Parser parser) {
        this.parser = parser;
    }

    public int calculate(String input) throws IllegalArgumentException{
        List<Integer> numbers = parser.parseInput(input);

        int sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }

        return sum;
    }
}
