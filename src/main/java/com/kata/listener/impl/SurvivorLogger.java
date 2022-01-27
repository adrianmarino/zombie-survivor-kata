package com.kata.listener.impl;

import com.kata.survivor.ExperienceLevel;
import com.kata.listener.SurvivorEventListener;
import com.kata.survivor.Survivor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SurvivorLogger implements SurvivorEventListener {
    @Override
    public void onAcquiredEquipment(Survivor survivor, String equipment) {
        log.info("Survivor {} acquired {} equipment.", survivor.getName(), equipment);
    }

    @Override
    public void onIsWounded(Survivor survivor, int wounds) {
        log.info("Survivor {} is wounded {} times.", survivor.getName(), wounds);
    }

    @Override
    public void onDies(Survivor survivor) {
        log.info("Survivor {} died.", survivor.getName());
    }

    @Override
    public void onLevelsUp(Survivor survivor, ExperienceLevel previousLevel) {
        log.info(
                "Survivor {} levels up experience level from {} to {}.",
                survivor.getName(),
                previousLevel,
                survivor.getExperienceLevel());
    }
}
