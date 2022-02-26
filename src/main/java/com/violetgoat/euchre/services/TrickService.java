package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.CompletedTrick;
import com.violetgoat.euchre.model.CompletedTrickImpl;
import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;

import java.util.List;

public interface TrickService {

    void initializeTrick(int leadPlayerIndex); // implement
    void setPlayerGoingAlone(int playerIndex);
    List<PlayingCard> getPlayedCards();
    int getWinningPlayerIndex();
    int getWinningTeam();
    boolean trickIsComplete(); // implement
    void wrapUpTrick(); // implement
    CompletedTrick packageCompletedTrick();
    void processPlayedCard(Play play);

}
