package com.violetgoat.euchre.model;

import java.util.List;

public class CompletedTrickImpl implements CompletedTrick {

    // == fields ==

    private List<PlayingCard> playedCards;
    private int winningPlayerIndex;
    private int winningTeamIndex;
    private int leadPlayerIndex;

    // == constructor ==

    public CompletedTrickImpl() {}

    // == public methods ==

    public List<PlayingCard> getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(List<PlayingCard> playedCards) {
        this.playedCards = playedCards;
    }

    public int getWinningPlayerIndex() {
        return winningPlayerIndex;
    }

    public void setWinningPlayerIndex(int winningPlayerIndex) {
        this.winningPlayerIndex = winningPlayerIndex;
    }

    public int getWinningTeamIndex() {
        return winningTeamIndex;
    }

    public void setWinningTeamIndex(int winningTeamIndex) {
        this.winningTeamIndex = winningTeamIndex;
    }

    @Override
    public int getLeadPlayerIndex() {
        return leadPlayerIndex;
    }

    public void setLeadPlayerIndex(int leadPlayerIndex) {
        this.leadPlayerIndex = leadPlayerIndex;
    }
}
