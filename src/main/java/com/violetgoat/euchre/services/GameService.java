package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Bid;
import com.violetgoat.euchre.model.CompletedTrick;
import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;

import java.util.List;

public interface GameService {

    void initializeGame();
    int chooseDealer();
    void initializeHand(int dealerIndex); // delegate to HandService, BiddingService, CardSelectionService
    void dealHand(); // delegate to HandService
    PlayingCard revealTopCard();
    void initializeBid(int dealerIndex, PlayingCard topCard); // delegate to BiddingService
    Bid getNextComputerBid(); // delegate to BiddingService
    Bid getNextPlayerBid(); // delegate to BiddingService
    void implementBid(Bid bid); // delegate to HandService
    void addTopCardToDealerHand();
    Play getComputerDiscard(); // delegate to CardSelectionService
    Play getPlayerDiscard();
    void implementDealerDiscard(Play discard); // delegateToHandService
    void initializeTrick(); // delegate to HandService
    void initializeCardSelection();
    Play getNextComputerPlay(); // delegate to CardSelectionService
    Play getNextPlayerPlay(); // delegate to CardSelectionService
    void processPlayedCard(Play play); // delegate to HandService
    boolean trickIsComplete(); // delegate to HandService
    void wrapUpTrick(); // delegate to HandService
    boolean handIsComplete(); // delegate to HandService
    void wrapUpHand(); // delegate to HandService, BiddingService, CardSelectionService,
    boolean gameIsComplete();
    void wrapUpGame();

    List<PlayingCard> getPlayerHand();
    List<PlayingCard> getPlayedCards();
    List<Integer> getScores();
    CompletedTrick getLastCompletedTrick();
    int getCurrentPlayerIndex();
    void advancePlayer();


}
