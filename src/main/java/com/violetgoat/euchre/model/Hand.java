package com.violetgoat.euchre.model;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    // == fields ==
    private int dealerIndex;
    private int currentPlayerIndex;
    private int numTricksCompleted;
    private Suit trumpSuit;
    private Bid winningBid;
    private PlayingCard dealerDiscardedCard;
    private PlayingCard revealedCard;
    private boolean biddingComplete = false;
    private int[] numTricksWon = {0, 0};
    private List<CompletedTrick> completedTricks = new ArrayList<>();

    // == constructor ==

    public Hand(){ }

    // == getters and setters ==

    public void reset(){
        dealerIndex = (dealerIndex + 1) % 4;
        currentPlayerIndex = (dealerIndex + 1) % 4;
        biddingComplete = false;
        trumpSuit = null;
        winningBid = null;
        completedTricks = new ArrayList<>();
        numTricksWon = new int[]{0, 0};
        revealedCard = null;
        dealerDiscardedCard = null;
    }

    public Suit getTrumpSuit() {
        return trumpSuit;
    }

    public void setTrumpSuit(Suit trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    public int getDealerIndex() {
        return dealerIndex;
    }

    public void setDealerIndex(int dealerIndex){
        this.dealerIndex = dealerIndex;
    }

    public Bid getWinningBid() {
        return winningBid;
    }

    public void setWinningBid(Bid winningBid) {
        this.winningBid = winningBid;
        this.trumpSuit = winningBid.getSuit();
        this.biddingComplete = true;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int playerIndex){
        this.currentPlayerIndex = playerIndex;
    }

    public PlayingCard getDealerDiscardedCard() {
        return dealerDiscardedCard;
    }

    public void setDealerDiscardedCard(PlayingCard dealerDiscardedCard) {
        this.dealerDiscardedCard = dealerDiscardedCard;
    }

    public int getNumTricksCompleted() {
        return completedTricks.size();
    }

    public void setNumTricksCompleted(int numTricksCompleted) {
        this.numTricksCompleted = numTricksCompleted;
    }

    public PlayingCard getRevealedCard() {
        return revealedCard;
    }

    public void setRevealedCard(PlayingCard revealedCard) {
        this.revealedCard = revealedCard;
    }

    public int getNumTricksWonBy(int teamIndex) {
        return numTricksWon[teamIndex];
    }

    public void incrementNumTricksWonBy(int teamIndex) {
        this.numTricksWon[teamIndex] = this.numTricksWon[teamIndex] + 1;
    }

    public void addCompletedTrick(CompletedTrick completedTrick){
        completedTricks.add(completedTrick);
    }

    public CompletedTrick getCompletedTrickNum(int trickNumber){

        if(trickNumber >= completedTricks.size() || trickNumber < 0) {
            throw new IllegalArgumentException("getCompletedTrickNum: Illegal argument -- argument must be between 0 and "
                + (completedTricks.size()-1));
        }

        return completedTricks.get(trickNumber);

    }

    public boolean isBiddingComplete(){
        return biddingComplete;
    }

    public void advancePlayer(){
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public int getBiddingTeamIndex(){
        return winningBid.getPlayerIndex() % 2;
    }

    public boolean biddingTeamIsGoingAlone(){
        return winningBid.isGoingAlone();
    }


}
