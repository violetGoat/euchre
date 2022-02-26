package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Bid;
import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Suit;

import java.util.List;

public interface HandProcessService {

    void initializeHand(int dealerIndex); // implement
    void dealHand(); // implement
    PlayingCard revealTopCard();
    void implementBid(Bid bid); // implement
    void addTopCardToDealerHand();
    void implementDealerDiscard(Play discard);
    void initializeTrick(); // delegate to TrickService
    void processPlayedCard(Play play);
    void advancePlayer();
    boolean trickIsComplete(); // delegate to TrickService
    void wrapUpTrick(); // implement, pass to TrickService
    boolean handIsComplete(); // implement
    void wrapUpHand();

    List<PlayingCard> getPlayerHand(); // implement
    List<PlayingCard> getPlayedCards(); // implement
    int getCurrentPlayerIndex();

}
