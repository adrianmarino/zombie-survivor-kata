package com.kata;

import com.kata.listener.impl.GameLogger;
import com.kata.listener.impl.SurvivorLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.kata.survivor.ExperienceLevel.RED;
import static com.kata.survivor.SurvivorBuilder.aSurvivor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GameUnitTest {

    @Test
    @DisplayName("when add a survivor to game it has only added survivor")
    void test1() {
        // Prepare
        var game = new Game();
        var survivor = aSurvivor().name("Adrian").build();

        // Perform
        game.add(survivor);

        // Asserts
        assertThat(game.getSurvivors().size(), is(1));
        assertThat(game.getSurvivors().get(survivor.getName()), is(survivor));
    }

    @Test
    @DisplayName("when all survivors are dead in a game it ends")
    void test2() {
        // Prepare
        var game = new Game();
        var survivor = aSurvivor().name("Adrian").build();
        game.add(survivor);
        survivor.receiveWounds(10);

        // Perform & Assert
        assertThat(game.isOver(), is(true));
    }

    @Test
    @DisplayName("when a game has 2 survivors with distinct experience level it return the highest")
    void test3() {
        // Prepare
        var game = new Game();

        var survivorA = aSurvivor().name("Adrian").build();
        game.add(survivorA);

        var survivorB = aSurvivor().name("Juan").build();
        game.add(survivorB);

        survivorA.killZombie(2);
        survivorA.killZombie(60);

        // Perform & Assert
        assertThat(game.getMaxExperienceLevel(), is(RED));
    }


    @Test
    void test4() {
        // Prepare
        var game = new Game(Set.of(new GameLogger()));

        game.add(aSurvivor().name("Adrian").listener(new SurvivorLogger()).build());

        game.start();

        game.getSurvivor("Adrian").killZombie(60);

        game.getSurvivor("Adrian").acquires("katana");

        game.getSurvivor("Adrian").receiveWounds(1);

        // Perform & Assert
        assertThat(game.getMaxExperienceLevel(), is(RED));
    }
}
