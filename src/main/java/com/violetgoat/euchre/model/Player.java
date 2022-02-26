package com.violetgoat.euchre.model;

import java.util.List;

public interface Player {

    String getName();
    int getPosition();
    List<PlayingCard> getHand();
    boolean isComputerPlayer();

    PlayingCard playCard(int index);
    PlayingCard playRandomCard();
    PlayingCard showCard(int index);
    void addCard(PlayingCard card);
    void discardCard(int index);

    void sortHand();
    void clearHand();
    int handSize();
    boolean hasCard(PlayingCard card);
    int numberOfSuit(Suit suit);
    boolean hasSuit(Suit suit);

}
