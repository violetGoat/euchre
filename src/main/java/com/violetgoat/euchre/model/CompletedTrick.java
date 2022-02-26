package com.violetgoat.euchre.model;

import java.util.List;

public interface CompletedTrick {

    List<PlayingCard> getPlayedCards();
    int getLeadPlayerIndex();
    int getWinningPlayerIndex();
    int getWinningTeamIndex();

}
