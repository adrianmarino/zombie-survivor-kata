package com.kata.listener.impl;


import com.kata.Game;
import com.kata.listener.GameEventListener;
import com.kata.survivor.ExperienceLevel;
import com.kata.survivor.Survivor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public final class GameLogger implements GameEventListener {

    public static GameEventListener instance = null;

    public static  GameEventListener getInstance() {
        return instance == null ? instance = new GameLogger(): instance;
    }

    private GameLogger() {
    }
    @Override
    public void onStart(Game game) {
        log.info("Game {} started.", game.hashCode());
    }

    @Override
    public void onSurvivorAdded(Game game, Survivor survivor) {
        log.info("Survivor {} added to the game.", survivor.getName());
    }

    @Override
    public void onLevelChange(Game game, ExperienceLevel previousLevel) {
        log.info("Level changed from {} to {}.", previousLevel, game.getMaxExperienceLevel());
    }

}
