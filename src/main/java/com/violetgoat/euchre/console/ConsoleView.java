package com.violetgoat.euchre.console;

import com.violetgoat.euchre.model.Bid;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Suit;

public interface ConsoleView {

    // TODO: add print score and going alone check
    void printHand();
    void printHand(int playerIndex);
    void printPlayedCards();
    void printTopCard();
    void printBid(Bid bid);
    void printScores();
    void printDealerMessage(int dealerIndex);
    void closeScanner();
    Bid getPlayerSecondRoundBid();
    Bid getPlayerFirstRoundBid();
    PlayingCard playerPlayCard();
    PlayingCard playerDiscardCard();


}
