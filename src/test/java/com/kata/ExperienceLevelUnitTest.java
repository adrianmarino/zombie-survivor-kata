package com.kata;

import com.kata.survivor.ExperienceLevel;
import org.junit.jupiter.api.Test;

import static com.kata.survivor.ExperienceLevel.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExperienceLevelUnitTest {

    @Test
    void test1() {
        assertThat(ExperienceLevel.from(3), is(BLUE));
    }

    @Test
    void test2() {
        assertThat(ExperienceLevel.from(10), is(YELLOW));
    }

    @Test
    void test3() {
        assertThat(ExperienceLevel.from(10), is(YELLOW));
    }

    @Test
    void test4() {
        assertThat(ExperienceLevel.from(20), is(ORANGE));
    }

    @Test
    void test5() {
        assertThat(ExperienceLevel.from(60), is(RED));
    }
}
