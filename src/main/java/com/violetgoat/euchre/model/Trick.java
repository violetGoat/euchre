package com.violetgoat.euchre.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Trick {

    // == fields ==
    private PlayingCard[] playedCards;
    private int leadPlayerIndex;
    private int winningPlayerIndex;
    private int playerGoingAlone = -1;
    private PlayingCard winningCard;
    Suit leadSuit;

    // == constructor ==

    public Trick(int leadPlayerIndex){
        this.playedCards = new PlayingCard[]{null, null, null, null};
        this.leadPlayerIndex = leadPlayerIndex;
        this.winningPlayerIndex = leadPlayerIndex;
    }

    // == public methods ==

    public List<PlayingCard> getPlayedCards() {
        return Arrays.asList(playedCards);
    }

    public void addPlayedCard(Play play){

        int playerIndex = play.getPlayerIndex();
        PlayingCard card = play.getCard();

        playedCards[playerIndex] = card;
    }

    public void setPlayedCards(PlayingCard[] playedCards) {
        this.playedCards = playedCards;
    }

    public int getLeadPlayerIndex() {
        return leadPlayerIndex;
    }

    public void setLeadPlayerIndex(int leadPlayerIndex) {
        this.leadPlayerIndex = leadPlayerIndex;
    }

    public int getWinningPlayerIndex() {
        return winningPlayerIndex;
    }

    public void setWinningPlayerIndex(int winningPlayerIndex) {
        this.winningPlayerIndex = winningPlayerIndex;
    }

    public void setWinningCard(PlayingCard card){
        this.winningCard = card;
    }

    public PlayingCard getWinningCard(){
        if(getNumCardsPlayed() < 1){
            throw new IllegalStateException(
                    "getWinningCard(): Illegal state--" +
                            "method may not be called until at least one card has been played"
            );
        }
        return winningCard;
    }

    public Suit getLeadSuit() {
        if(getNumCardsPlayed() < 1){
            throw new IllegalStateException(
                    "getLeadSuit(): Illegal state--" +
                            "method may not be called until at least one card has been played"
            );
        }
        return leadSuit;
    }

    public void setLeadSuit(Suit leadSuit) {
        this.leadSuit = leadSuit;
    }

    public int getNumCardsPlayed(){
        int count = 0;

        for(PlayingCard card : playedCards) {
            if (card != null) {
                count++;
            }
        }
        return count;
    }

    public PlayingCard getLeadCard(){
        if(getNumCardsPlayed() < 1){
            throw new IllegalStateException(
                    "getLeadCard(): Illegal state--" +
                    "method may not be called until at least one card has been played"
            );
        }
        return playedCards[leadPlayerIndex];
    }

    public boolean isComplete(){

        long expectedNumCardsPlayed = playerGoingAlone > 0 ?
                3 : 4;

        return getNumCardsPlayed() == expectedNumCardsPlayed;

    }

    public int getPlayerGoingAlone() {
        return playerGoingAlone;
    }

    public void setPlayerGoingAlone(int playerIndex){
        this.playerGoingAlone = playerIndex;
    }


}
