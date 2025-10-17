package calculator;

import calculator.controller.CalculatorController;
import calculator.model.Calculator;
import calculator.model.Parser;
import calculator.view.InputView;
import calculator.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        Parser parser = new Parser();
        Calculator calculator = new Calculator(parser);
        OutputView outputView = new OutputView();
        CalculatorController calculatorController = new CalculatorController(inputView, calculator, outputView);

        calculatorController.run();
    }
}
