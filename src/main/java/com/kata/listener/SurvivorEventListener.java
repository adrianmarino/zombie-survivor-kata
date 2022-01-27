package com.kata.listener;

import com.kata.survivor.ExperienceLevel;
import com.kata.survivor.Survivor;

public interface SurvivorEventListener {

    void onAcquiredEquipment(Survivor survivor, String equipment);

    void onIsWounded(Survivor survivor, int wounds);

    void onDies(Survivor survivor);

    void onLevelsUp(Survivor survivor, ExperienceLevel previousLevel);
}
