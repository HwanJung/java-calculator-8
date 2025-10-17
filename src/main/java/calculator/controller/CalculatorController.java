package calculator.controller;

import calculator.model.Calculator;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {
    private final InputView inputView;
    private final Calculator calculator;
    private final OutputView outputView;

    public CalculatorController(InputView inputView, Calculator calculator, OutputView outputView) {
        this.inputView = inputView;
        this.calculator = calculator;
        this.outputView = outputView;
    }

    public void run() throws IllegalArgumentException {
        String input = inputView.readInput();
        int result = calculator.calculate(input);
        outputView.printResult(result);
    }
}
