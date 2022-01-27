package com.kata.survivor;

import com.kata.listener.SurvivorEventListener;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public final class SurvivorBuilder {

    private final Set<SurvivorEventListener> eventListeners;

    private String name;

    private Set<String> equipmentInBag;

    private Set<String> equipmentInHands;

    public SurvivorBuilder() {
        this.name = "";
        this.equipmentInBag = newHashSet();
        this.equipmentInHands = newHashSet();
        this.eventListeners = newHashSet();
    }

    public static SurvivorBuilder aSurvivor() {
        return new SurvivorBuilder();
    }

    public SurvivorBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SurvivorBuilder equipmentInBag(Set<String> equipmentInBag) {
        this.equipmentInBag = equipmentInBag;
        return this;
    }

    public SurvivorBuilder equipmentInBag(String... equipmentInBag) {
        return equipmentInBag(newHashSet(equipmentInBag));
    }

    public SurvivorBuilder equipmentInHands(String... equipmentInHands) {
        this.equipmentInHands = newHashSet(equipmentInHands);
        return this;
    }

    public SurvivorBuilder listener(SurvivorEventListener eventListener) {
        this.eventListeners.add(eventListener);
        return this;
    }

    public Survivor build() {
        return new Survivor(name, eventListeners, equipmentInBag, equipmentInHands);
    }
}
