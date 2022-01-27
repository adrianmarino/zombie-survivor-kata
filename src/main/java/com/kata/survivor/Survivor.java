package com.kata.survivor;

import com.google.common.collect.ImmutableSet;
import com.kata.listener.SurvivorEventListener;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Sets.newHashSet;
import static com.kata.survivor.ExperienceLevel.BLUE;
import static java.lang.Math.min;


public class Survivor {

    public static final int MAX_WOUNDS = 2;

    @Getter
    private final String name;

    @Getter
    private final int actionsByTurn;

    private final Set<SurvivorEventListener> eventListeners;

    @Getter
    private int experience;

    @Getter
    private ExperienceLevel experienceLevel;

    @Getter
    private int wounds;

    private Set<String> equipmentInBag;

    private Set<String> equipmentInHands;

    Survivor(String name, @NonNull Set<SurvivorEventListener> eventListeners, @NonNull Set<String> equipmentInBag, @NonNull Set<String> equipmentInHands) {
        wounds = 0;
        actionsByTurn = 3;
        experience = 0;
        experienceLevel = BLUE;

        this.name = name;
        this.eventListeners = eventListeners;

        initializeBag(equipmentInBag);
        initializeHands(equipmentInHands);
    }

    public void addListener(@NonNull SurvivorEventListener listener) {
        eventListeners.add(listener);
    }

    private void initializeHands(Set<String> equipmentInHands) {
        checkArgument(equipmentInHands.size() <= 2, "Hands can't" + " have more than 2 equipment items");
        this.equipmentInHands = newHashSet(equipmentInHands);
    }

    private void initializeBag(Set<String> equipmentInBag) {
        checkArgument(equipmentInBag.size() <= 10, "Bag can't have more than 10 equipment items");

        this.equipmentInBag = newHashSet(equipmentInBag);
    }

    public boolean isAlive() {
        return wounds < 2;
    }

    public boolean isDead() {
        return !isAlive();
    }

    public void receiveAttack(int value) {
        wounds = min(wounds + value, MAX_WOUNDS);
        firstOf(equipmentInHands).ifPresentOrElse(equipmentInHands::remove, () -> firstOf(equipmentInBag).ifPresent(equipmentInBag::remove));
        eventListeners.forEach(it -> it.onIsWounded(this, value));
        if (isDead()) eventListeners.forEach(it -> it.onDies(this));
    }

    private Optional<String> firstOf(Set<String> set) {
        return set.stream().findFirst();
    }

    public void killZombie(int count) {
        experience += count;

        setExperienceLevel(ExperienceLevel.from(experience));
    }

    private void setExperienceLevel(ExperienceLevel newExperienceLevel) {
        var previousLevel = experienceLevel;
        experienceLevel = newExperienceLevel;

        if (experienceLevel.compareTo(previousLevel) > 0)
            eventListeners.forEach(it -> it.onLevelsUp(this, previousLevel));
    }

    public Set<String> getEquipmentInBag() {
        return ImmutableSet.copyOf(equipmentInBag);
    }

    public Set<String> getEquipmentInHands() {
        return ImmutableSet.copyOf(equipmentInHands);
    }

    public void acquires(String equipment) {
        equipmentInBag.add(equipment);
        eventListeners.forEach(it -> it.onAcquiredEquipment(this, equipment));
    }
}
