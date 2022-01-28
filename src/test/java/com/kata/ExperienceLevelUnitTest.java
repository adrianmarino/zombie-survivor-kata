package com.kata;

import com.kata.survivor.ExperienceLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.kata.survivor.ExperienceLevel.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExperienceLevelUnitTest {

    @Test
    @DisplayName("When experience is between 0 and 6 it returns BLUE level")
    void test1() {
        assertThat(ExperienceLevel.from(3), is(BLUE));
    }

    @Test
    @DisplayName("When experience is between 7 and 18 it returns BLUE level")
    void test2() {
        assertThat(ExperienceLevel.from(10), is(YELLOW));
    }

    @Test
    @DisplayName("When experience is between 19 and 42 it returns YELLOW level")
    void test3() {
        assertThat(ExperienceLevel.from(20), is(ORANGE));
    }

    @Test
    @DisplayName("When experience is greater than 43 it returns RED level")
    void test4() {
        assertThat(ExperienceLevel.from(60), is(RED));
    }
}
