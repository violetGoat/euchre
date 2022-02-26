package com.violetgoat.euchre.model;

import java.util.List;

public class PlayerImpl implements Player {

    private static int position_counter = 0;

    // == fields ==
    private String name;
    private PlayingCardDeck hand;
    private int position;
    private boolean isComputerPlayer;

    // == constructor ==
    public PlayerImpl(String name, boolean isComputerPlayer){
        this.name = name;
        this.isComputerPlayer = isComputerPlayer;
        this.hand = new PlayingCardDeckImpl();
        assignPosition();
    }

    private void assignPosition(){
        this.position = position_counter;
        position_counter++;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public boolean isComputerPlayer() {
        return isComputerPlayer;
    }

    @Override
    public PlayingCard playCard(int index) {
        return hand.removeCardByIndex(index);
    }

    @Override
    public PlayingCard playRandomCard() {
        return hand.removeRandomCard();
    }

    @Override
    public PlayingCard showCard(int index) {
        return hand.revealCardByIndex(index);
    }

    @Override
    public void addCard(PlayingCard card) {
        hand.enqueue(card);
    }

    @Override
    public void discardCard(int index) {
        hand.removeCardByIndex(index);
    }

    @Override
    public void clearHand() {
        hand.clearAll();
    }

    @Override
    public int handSize() {
        return hand.numCardsRemaining();
    }

    @Override
    public boolean hasCard(PlayingCard card) {
        return getHand().contains(card);
    }

    @Override
    public int numberOfSuit(Suit suit) {
        return (int) getHand()
                .stream()
                .filter((c) -> c.getSuit().equals(suit))
                .count();
    }

    @Override
    public boolean hasSuit(Suit suit) {
        return getHand()
                .stream()
                .anyMatch((c) -> c.getSuit().equals(suit));
    }

    public void sortHand(){
        hand.sortDeck();
    }

    public List<PlayingCard> getHand(){
        return hand.listAllCards();
    }

}
