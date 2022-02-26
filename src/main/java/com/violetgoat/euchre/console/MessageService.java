package com.violetgoat.euchre.console;

import com.violetgoat.euchre.model.Bid;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Suit;

import java.util.List;

public interface MessageService {

    void printCard(PlayingCard card);
    void printCardList(List<PlayingCard> cards);
    void printSuitList(List<Suit> suits);
    void printBid(Bid bid);
    void printPlayedCards(List<PlayingCard> playedCards);
    String cardFormatted(PlayingCard card);
    String suitFormatted(Suit suit);
    void printMessage(String message);

}
