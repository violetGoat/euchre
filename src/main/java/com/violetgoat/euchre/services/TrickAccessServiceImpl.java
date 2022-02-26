package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Suit;
import com.violetgoat.euchre.model.Trick;

import java.util.List;

public class TrickAccessServiceImpl implements TrickAccessService{


    // == fields ==

    Trick trick;

    //  == constructor ==

    public TrickAccessServiceImpl(){}

    // == public (interface) methods

    @Override
    public void initializeNewTrick(int leadPlayerIndex) {
        this.trick = new Trick(leadPlayerIndex);
    }

    @Override
    public Suit getLeadSuit() {
        return trick.getLeadSuit();
    }

    @Override
    public PlayingCard getLeadCard() {
        return trick.getLeadCard();
    }

    @Override
    public PlayingCard getCurrentWinningCard() {
        return trick.getWinningCard();
    }

    @Override
    public int getNumCardsPlayed() {
        return trick.getNumCardsPlayed();
    }

    @Override
    public List<PlayingCard> getPlayedCards() {
        return trick.getPlayedCards();
    }

    @Override
    public int getWinningTeamIndex() {
        return trick.getWinningPlayerIndex() % 2;
    }

    @Override
    public int getWinningPlayerIndex() {
        return trick.getWinningPlayerIndex();
    }

    @Override
    public int getLeadPlayerIndex() {
        return trick.getLeadPlayerIndex();
    }

    @Override
    public boolean trickIsComplete() {
        return trick.isComplete();
    }

    @Override
    public void setWinningCard(PlayingCard card) {
        trick.setWinningCard(card);
    }

    @Override
    public void setWinningPlayerIndex(int winningPlayerIndex) {
        trick.setWinningPlayerIndex(winningPlayerIndex);
    }

    @Override
    public void setPlayerGoingAlone(int playerIndex) {
        trick.setPlayerGoingAlone(playerIndex);
    }

    @Override
    public void addPlay(Play play) {
        trick.addPlayedCard(play);
    }

    @Override
    public int getPlayerGoingAlone() {
        return trick.getPlayerGoingAlone();
    }

    @Override
    public void setLeadSuit(Suit suit) {
        trick.setLeadSuit(suit);
    }
}


