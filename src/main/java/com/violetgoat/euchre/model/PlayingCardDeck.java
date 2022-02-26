package com.violetgoat.euchre.model;
import com.violetgoat.euchre.model.PlayingCard;

import java.util.List;

// TODO: implement discard pile?

public interface PlayingCardDeck {

    PlayingCard revealCardByIndex(int index);
    PlayingCard removeCardByIndex(int index);
    PlayingCard revealTopCard();
    PlayingCard removeTopCard();
    PlayingCard revealRandomCard();
    PlayingCard removeRandomCard();
    PlayingCard[] removeNCards(int n);
    boolean isEmpty();
    int numCardsRemaining();
    int numCardsInFullDeck();
    void push(PlayingCard card);
    void enqueue(PlayingCard card);
    void addCardToFullDeck(PlayingCard card);
    void clearAll();
    void sortDeck();
    void reverseDeck();
    void shuffleDeck();

    List<PlayingCard> listAllCards();
}
