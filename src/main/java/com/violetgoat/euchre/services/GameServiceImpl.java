package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Bid;
import com.violetgoat.euchre.model.CompletedTrick;
import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameServiceImpl implements GameService {

    // == fields ==
    private static final int SLEEP_TIME_MS = 1500;

    private HandProcessService handProcessService;
    private HandAccessService handAccessService;
    private BiddingService biddingService;
    private CardSelectionService cardSelectionService;
    private PlayerService playerService;

    // == constructors ==
    public GameServiceImpl(HandProcessService handProcessService, BiddingService biddingService, CardSelectionService cardSelectionService, PlayerService playerService, HandAccessService handAccessService){
        this.handProcessService = handProcessService;
        this.biddingService = biddingService;
        this.cardSelectionService = cardSelectionService;
        this.playerService = playerService;
        this.handAccessService = handAccessService;
    }

    // public methods

    @Override
    public void initializeGame() {

    }

    public int chooseDealer(){
        return new Random().nextInt(4);
    }

    @Override
    public void initializeHand(int dealerIndex) {
        handProcessService.initializeHand(dealerIndex);
    }

    @Override
    public void dealHand() {
        handProcessService.dealHand();
    }

    public PlayingCard revealTopCard(){
        return handProcessService.revealTopCard();
    }

    @Override
    public void initializeBid(int dealerIndex, PlayingCard topCard) {
        biddingService.initializeBid(dealerIndex, topCard);
    }

    @Override
    public Bid getNextComputerBid() {
        sleep();
        return biddingService.getNextComputerBid();
    }

    @Override
    public Bid getNextPlayerBid() {
        return biddingService.getNextPlayerBid();
    }

    @Override
    public void implementBid(Bid bid) {

        if(bid.isOrderingUp()) {
            addTopCardToDealerHand();
            getDiscard();
        }

        handProcessService.implementBid(bid);
        biddingService.wrapUpBid();
        cardSelectionService.initializeCardSelection();

    }

    public void addTopCardToDealerHand(){
        handProcessService.addTopCardToDealerHand();
    }

    @Override
    public Play getComputerDiscard() {
        return cardSelectionService.getComputerDiscard();
    }

    @Override
    public Play getPlayerDiscard() {
        return cardSelectionService.getPlayerDiscard();
    }

    @Override
    public void implementDealerDiscard(Play discard) {
        handProcessService.implementDealerDiscard(discard);
    }

    @Override
    public void initializeTrick() {
        handProcessService.initializeTrick();
    }

    @Override
    public void initializeCardSelection() {
        cardSelectionService.initializeCardSelection();
    }

    @Override
    public Play getNextComputerPlay() {
        sleep();
        return cardSelectionService.getNextComputerPlay();
    }

    @Override
    public Play getNextPlayerPlay() {
        return cardSelectionService.getNextPlayerPlay();
    }

    public void processPlayedCard(Play play){
        handProcessService.processPlayedCard(play);
    }

    @Override
    public boolean trickIsComplete() {
        return handProcessService.trickIsComplete();
    }

    @Override
    public void wrapUpTrick() {
        sleep();
        handProcessService.wrapUpTrick();
    }

    @Override
    public boolean handIsComplete() {
        return handProcessService.handIsComplete();
    }

    @Override
    public void wrapUpHand() {
        sleep();
        handProcessService.wrapUpHand();
    }

    @Override
    public boolean gameIsComplete() {
        return getScores().get(0) >= 10
                || getScores().get(1) >= 10;
    }

    @Override
    public void wrapUpGame() {

    }

    @Override
    public List<PlayingCard> getPlayerHand() {
        return handProcessService.getPlayerHand();
    }

    @Override
    public List<PlayingCard> getPlayedCards() {
        return handProcessService.getPlayedCards();
    }

    @Override
    public List<Integer> getScores() {
        return playerService.getScores();
    }

    @Override
    public int getCurrentPlayerIndex() {
        return handProcessService.getCurrentPlayerIndex();
    }

    @Override
    public void advancePlayer() {
        handProcessService.advancePlayer();
    }

    @Override
    public CompletedTrick getLastCompletedTrick() {
        int lastTrickNum = handAccessService.getNumTricksCompleted() - 1;
        return handAccessService.getCompletedTrickNum(lastTrickNum);
    }
    // private methods

    private void getDiscard(){

        if(handAccessService.getDealerIndex() == 0){
            getPlayerDiscard();
        }

        getComputerDiscard();

    }

    private void sleep(){
        try {
            TimeUnit.MILLISECONDS.sleep(SLEEP_TIME_MS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
