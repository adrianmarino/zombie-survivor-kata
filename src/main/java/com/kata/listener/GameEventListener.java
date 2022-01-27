package com.kata.listener;

import com.kata.survivor.ExperienceLevel;
import com.kata.Game;
import com.kata.survivor.Survivor;

public interface GameEventListener {

    void onStart(Game game);

    void onSurvivorAdded(Game game, Survivor survivor);

    void onLevelChange(Game game, ExperienceLevel previousLevel);
}
