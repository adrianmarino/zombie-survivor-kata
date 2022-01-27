package com.kata;

import com.kata.listener.SurvivorEventListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.collect.Sets.newHashSet;
import static com.kata.survivor.ExperienceLevel.RED;
import static com.kata.survivor.SurvivorBuilder.aSurvivor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class SurvivorUnitTest {

    @Test
    @DisplayName("When create a survivor with a custom name it has the default initial state")
    void test1() {
        // Perform
        var survivor = aSurvivor().name("Adrian").build();

        // Asserts
        assertThat(survivor.getName(), is("Adrian"));
        assertThat(survivor.getActionsByTurn(), is(3));
        assertThat(survivor.getWounds(), is(0));
        assertThat(survivor.isAlive(), is(true));
    }

    @Test
    @DisplayName("When a survivor receive more than 2 wounds it dies")
    void test2() {
        // Prepare
        var survivor = aSurvivor().name("Adrian").build();

        // Perform
        survivor.receiveAttack(3);

        // Asserts
        assertThat(survivor.getWounds(), is(2));
        assertThat(survivor.isAlive(), is(false));
    }

    @Test
    @DisplayName("When a survivor receive less than 2 wounds it dies")
    void test3() {
        // Prepare
        var survivor = aSurvivor().name("Adrian").build();

        // Perform
        survivor.receiveAttack(1);

        // Asserts
        assertThat(survivor.getWounds(), is(1));
        assertThat(survivor.isAlive(), is(true));
    }

    @Test
    @DisplayName("When a survivor receive one wound it lose one of in hand equipments")
    void test4() {
        // Prepare
        var survivor = aSurvivor().name("Adrian").equipmentInBag("Baseball bat", "Frying pan", "Pistol",
                "Bottled " + "Water", "Molotov").equipmentInHands("Katana").build();

        // Perform
        survivor.receiveAttack(1);

        // Asserts
        assertThat(survivor.getEquipmentInHands().size(), is(0));
    }

    @Test
    @DisplayName("When a survivor receive one wound it lose one of in bag equipments")
    void test5() {
        // Prepare
        var equipmentInBag = newHashSet("Baseball bat", "Frying pan", "Pistol", "Bottled Water", "Molotov");
        var survivor = aSurvivor().name("Adrian").equipmentInBag(equipmentInBag).build();

        // Perform
        survivor.receiveAttack(1);

        // Asserts
        assertThat(survivor.getEquipmentInHands().size(), is(0));
        assertThat(survivor.getEquipmentInBag().size(), is(equipmentInBag.size() - 1));
    }

    @Test
    @DisplayName("when each time tha a survivor kill a zombie it increase your experience and experience level")
    void test6() {
        // Prepare
        var killedZombies = 60;
        var survivor = aSurvivor().name("Adrian").build();

        // Perform
        survivor.killZombie(killedZombies);

        // Asserts
        assertThat(survivor.getExperience(), is(killedZombies));
        assertThat(survivor.getExperienceLevel(), is(RED));
    }

    @Test
    @DisplayName("When a survivor increase your experience level it trigger an onLevelsUp event")
    void test7() {
        // Prepare
        var listener = mock(SurvivorEventListener.class);
        var survivor = aSurvivor().name("Adrian").listener(listener).build();
        var wounds = 60;
        var initialLevel = survivor.getExperienceLevel();

        // Perform
        survivor.killZombie(wounds);

        // Assert
        verify(listener, times(1)).onLevelsUp(survivor, initialLevel);
    }

    @Test
    @DisplayName("When a survivor acquire an equipment it trigger an onAcquiredEquipment event")
    void test8() {
        // Prepare
        var listener = mock(SurvivorEventListener.class);
        var survivor = aSurvivor().name("Adrian").listener(listener).build();
        var equipment = "katana";

        // Perform
        survivor.acquires(equipment);

        // Assert
        verify(listener, times(1)).onAcquiredEquipment(survivor, equipment);
    }

    @Test
    @DisplayName("When a survivor receives an attack and dies it trigger onDies event")
    void test9() {
        // Prepare
        var listener = mock(SurvivorEventListener.class);
        var survivor = aSurvivor().name("Adrian").listener(listener).build();
        var wounds = 10;

        // Perform
        survivor.receiveAttack(wounds);

        // Assert
        verify(listener, times(1)).onDies(survivor);
    }

    @Test
    @DisplayName("When a survivor receives an attack that produce wounds it trigger onIsWounded event")
    void test10() {
        // Prepare
        var listener = mock(SurvivorEventListener.class);
        var survivor = aSurvivor().name("Adrian").listener(listener).build();
        var wounds = 10;

        // Perform
        survivor.receiveAttack(wounds);

        // Assert
        verify(listener, times(1)).onIsWounded(survivor, wounds);
    }
}
