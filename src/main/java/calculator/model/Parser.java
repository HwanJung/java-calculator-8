package calculator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parser {
    private static final Pattern HEADER = Pattern.compile("\\A//(.)\\\\n");

    public List<Integer> parseInput(String input) throws IllegalArgumentException {
        String body = input;
        List<Character> delimiters = new ArrayList<>(List.of(',', ':'));

        // custom 구분자가 있을 가능성이 있다면 구분자 추출
        if (input.startsWith("//")) {
            ParsedInput parsedInput = extractDelimiter(input);
            body = parsedInput.body();
            delimiters.add(parsedInput.customDelimiter());
        }

        // 구분자를 표시하기 위한 부분을 제외한 문자가 비어있다면 빈 리스트 반환
        if (body.isEmpty()) {
            return new ArrayList<>();
        }

        // 구분자를 토대로 문자열에서 숫자 리스트 추출하여 반환
        return extractNumbers(delimiters, body);
    }

    private ParsedInput extractDelimiter(String input) {

        Matcher matcher = HEADER.matcher(input);
        if (!matcher.lookingAt()) {
            // "//" + 문자1개 + "\n" 패턴이 아니라면 예외
            throw new IllegalArgumentException("Invalid input: illegal format of delimiter in " + input);
        }

        char customDelimiter = matcher.group(1).charAt(0);
        if (Character.isDigit(customDelimiter)) {
            // 추출한 구분자가 숫자라면 예외
            throw new IllegalArgumentException("Invalid input: extracted delimiter is number in " + input);
        }
        String body = input.substring(matcher.end());

        return new ParsedInput(customDelimiter, body);
    }

    private List<Integer> extractNumbers(List<Character> delimiters, String body) {
        // 구분자 정규식 생성
        String delimitersRegex = delimiters.stream()
            .map(String::valueOf)
            .map(Pattern::quote)
            .collect(Collectors.joining("|"));

        String[] tokens = body.split(delimitersRegex, -1);
        List<Integer> numberList = new ArrayList<>();

        for (String token : tokens) {
            if (token.isEmpty()) {
                // 구분자 사이에 숫자가 없다면 예외
                // 구분자가 맨 앞, 맨 뒤에 온다면 예외
                throw new IllegalArgumentException("Invalid input: misplaced delimiter in " + body);
            }
            if (!token.matches("[0-9]+")) {
                // 문자열에 구분자와 숫자가 아닌 문자가 있으면 예외
                throw new IllegalArgumentException("Invalid input: extracted String is not Integer in " + body);
            }
            numberList.add(Integer.parseInt(token));
        }

        return numberList;
    }

    private record ParsedInput(
        char customDelimiter,
        String body
    ) {
    }

}
