package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 커스텀_구분자_사용과_각_구분자_정상동작1() {
        assertSimpleTest(() -> {
            run("//;\\n1,10:20;10");
            assertThat(output()).contains("결과 : 41");
        });
    }

    @Test
    void 커스텀_구분자_사용과_각_구분자_정상동작2() {
        assertSimpleTest(() -> {
            run("//\\\\n1,10:20\\10");
            assertThat(output()).contains("결과 : 41");
        });
    }

    @Test
    void 커스텀_구분자_사용과_empty_body() {
        assertSimpleTest(() -> {
            run("//;\\n");
            assertThat(output()).contains("결과 : 0");
        });
    }

    @Test
    void 커스텀_구분자_형식_오류1() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("//;;\\n1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자_형식_오류2() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("//;1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자가_숫자면_에러() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("//4\\n1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 단순_문자열_입력_정상동작() {
        assertSimpleTest(() -> {
            run("1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 단순_문자열_입력_정상동작2() {
        assertSimpleTest(() -> {
            run("11:22,33");
            assertThat(output()).contains("결과 : 66");
        });
    }


    @Test
    void 단순_문자열_입력_빈문자열은_0() {
        assertSimpleTest(() -> {
            run("\n");
            assertThat(output()).contains("결과 : 0");
        });
    }

    @Test
    void 단순_문자열_입력_숫자가_아닌_문자가_있으면_에러() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("1+2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 단순_문자열_입력_숫자가_아닌_문자가_있으면_에러2() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("1 2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 단순_문자열_입력_구분자_사이에_숫자_없으면_에러() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("1::2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 단순_문자열_입력_구분자가_맨앞이면_에러() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException(",1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 단순_문자열_입력_구분자가_맨뒤라면_에러() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("1,2,3,"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
