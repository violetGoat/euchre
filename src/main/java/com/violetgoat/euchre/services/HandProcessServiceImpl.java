package com.violetgoat.euchre.services;
import com.violetgoat.euchre.model.*;

import java.util.List;

public class HandProcessServiceImpl implements HandProcessService {

    // == fields ==

    private Hand hand;
    private PlayerService playerService;
    private Dealer dealer;
    private TrickService trickService;

    // == constructor ==
    public HandProcessServiceImpl(PlayerService playerService, Dealer dealer, TrickService trickService, Hand hand){
        this.playerService = playerService;
        this.dealer = dealer;
        this.trickService = trickService;
        this.hand = hand;
    }

    // == public (interface) methods

    @Override
    public void initializeHand(int dealerIndex) {
        hand.reset();
        hand.setDealerIndex(dealerIndex);
        hand.setCurrentPlayerIndex((dealerIndex + 1) % 4);
        dealer.initializeDeck();
    }

    @Override
    public void dealHand() {
        dealer.dealHand();
        PlayingCard revealedCard = dealer.revealTopCard();
        hand.setRevealedCard(revealedCard);
    }

    @Override
    public PlayingCard revealTopCard() {
        return hand.getRevealedCard();
    }

    @Override
    public void implementBid(Bid bid) {
        hand.setWinningBid(bid);
    }

    @Override
    public void addTopCardToDealerHand(){
        PlayingCard topCard = revealTopCard();
        int dealerIndex = hand.getDealerIndex();
        playerService.addCardToPlayerHand(topCard, dealerIndex);
    }

    @Override
    public void implementDealerDiscard(Play discard){
        hand.setDealerDiscardedCard(discard.getCard());
    }

    @Override
    public void initializeTrick() {
        int leadPlayerIndex = getLeadPlayer();
        trickService.initializeTrick(leadPlayerIndex);
        hand.setCurrentPlayerIndex(leadPlayerIndex);
    }

    @Override
    public void processPlayedCard(Play play){
        trickService.processPlayedCard(play);
    }

    @Override
    public void advancePlayer(){
        int currentPlayerIndex = hand.getCurrentPlayerIndex();
        hand.setCurrentPlayerIndex((currentPlayerIndex + 1) % 4);
    }

    @Override
    public boolean trickIsComplete() {
        return trickService.trickIsComplete();
    }

    @Override
    public void wrapUpTrick() {
        int winningTeam = trickService.getWinningTeam();
        hand.incrementNumTricksWonBy(winningTeam);
        CompletedTrick completedTrick = trickService.packageCompletedTrick();
        hand.addCompletedTrick(completedTrick);
        trickService.wrapUpTrick();
    }

    @Override
    public boolean handIsComplete() {
        return hand.getNumTricksCompleted() == 5;
    }

    @Override
    public void wrapUpHand() {
        updateScores();
        playerService.wrapUpHand();
    }

    @Override
    public List<PlayingCard> getPlayerHand() {
        return playerService.getHumanPlayerHand();
    }

    @Override
    public List<PlayingCard> getPlayedCards() {
        return trickService.getPlayedCards();
    }

    public int getCurrentPlayerIndex(){
        return hand.getCurrentPlayerIndex();
    }

    // == private methods ==

    private int getLeadPlayer(){

        if(hand.getNumTricksCompleted() == 0){
            return (hand.getDealerIndex() +1) % 4;
        }

        return trickService.getWinningPlayerIndex();

    }

    private void updateScores(){
        int winningTeam = getHandWinner();
        int biddingTeam = hand.getBiddingTeamIndex();
        int scoreIncrease = 1;

        if(biddingTeam == winningTeam
                && hand.getNumTricksWonBy(winningTeam) == 5){
            scoreIncrease = hand.biddingTeamIsGoingAlone() ?
                    4 : 2;
        } else if (hand.getBiddingTeamIndex() != winningTeam){
            scoreIncrease = 2;
        }

        playerService.increaseTeamScoreBy(winningTeam, scoreIncrease);
    }

    private int getHandWinner(){

        if (!handIsComplete()){
            throw new IllegalStateException("getHandWinner(): Illegal state--" +
                    "method may not be called until hand is complete");
        }

        return hand.getNumTricksWonBy(0) >= 3 ?
                0 : 1;

    }


}
