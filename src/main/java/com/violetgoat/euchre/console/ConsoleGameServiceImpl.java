package com.violetgoat.euchre.console;

import com.violetgoat.euchre.model.*;
import com.violetgoat.euchre.services.*;

public class ConsoleGameServiceImpl extends GameServiceImpl {

    // == fields ==
    ConsoleView consoleView;
    // == constructor ==

    public ConsoleGameServiceImpl(HandProcessService handProcessService, BiddingService biddingService, CardSelectionService cardSelectionService, PlayerService playerService, HandAccessService handAccessService, ConsoleView consoleView) {
        super(handProcessService, biddingService, cardSelectionService, playerService, handAccessService);
        this.consoleView = consoleView;
    }

    // == methods ==


    @Override
    public void dealHand() {
        super.dealHand();
        consoleView.printTopCard();
        consoleView.printHand();
    }

    @Override
    public PlayingCard revealTopCard() {
        return super.revealTopCard();
    }

    @Override
    public Bid getNextComputerBid() {
        Bid bid = super.getNextComputerBid();
        consoleView.printBid(bid);
        return bid;
    }

    public Bid getNextPlayerBid(){
        Bid bid = super.getNextPlayerBid();
        return bid;
    }

    @Override
    public void processPlayedCard(Play play) {
        super.processPlayedCard(play);
        consoleView.printPlayedCards();
    }

    @Override
    public void wrapUpTrick() {
        super.wrapUpTrick();
        CompletedTrick completedTrick = getLastCompletedTrick();
        System.out.println(
                "Trick won by Player " +
                (completedTrick.getWinningPlayerIndex()+1) +
                " with " + completedTrick.getPlayedCards().get(completedTrick.getWinningPlayerIndex()).nameShort()
        );
    }

    @Override
    public void wrapUpHand() {
        super.wrapUpHand();
        consoleView.printScores();
    }

    public void initializeHand(int dealerIndex){
        super.initializeHand(dealerIndex);
        consoleView.printDealerMessage(dealerIndex);
    }

}
