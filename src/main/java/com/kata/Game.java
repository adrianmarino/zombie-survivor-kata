package com.kata;

import com.kata.listener.GameEventListener;
import com.kata.listener.SurvivorEventListener;
import com.kata.survivor.ExperienceLevel;
import com.kata.survivor.Survivor;
import lombok.NonNull;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.ImmutableMap.copyOf;
import static com.google.common.collect.Maps.newHashMap;
import static com.kata.survivor.ExperienceLevel.BLUE;

public class Game implements SurvivorEventListener {

    private final Map<String, Survivor> survivors;

    private final Set<GameEventListener> eventListeners;

    private final ExperienceLevel previousLevel;

    public Game() {
        this(Set.of());
    }

    public Game(@NonNull Set<GameEventListener> eventListeners) {
        this.eventListeners = eventListeners;
        this.survivors = newHashMap();
        this.previousLevel = ExperienceLevel.initial();
    }

    public void add(@NonNull Survivor survivor) {
        survivors.put(survivor.getName(), survivor);
        survivor.addListener(this);
        eventListeners.forEach(it -> it.onSurvivorAdded(this, survivor));
    }

    public Map<String, Survivor> getSurvivors() {
        return copyOf(survivors);
    }

    public boolean isOver() {
        return survivors.values().stream().allMatch(Survivor::isDead);
    }

    public ExperienceLevel getMaxExperienceLevel() {
        return getSurvivors().values().stream().map(Survivor::getExperienceLevel).max(Enum::compareTo).orElse(BLUE);
    }

    public void start() {
        eventListeners.forEach(it -> it.onStart(this));
    }

    public Survivor getSurvivor(String name) {
        return survivors.get(name);
    }

    @Override
    public void onAcquiredEquipment(Survivor survivor, String equipment) {
    }

    @Override
    public void onIsWounded(Survivor survivor, int wounds) {
    }

    @Override
    public void onDies(Survivor survivor) {
    }

    @Override
    public void onLevelsUp(Survivor survivor, ExperienceLevel previousLevel) {
        eventListeners.forEach(it -> it.onLevelChange(this, this.previousLevel));
        previousLevel = survivor.getExperienceLevel();
    }
}
