package com.violetgoat.euchre.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayingCardDeckImpl implements PlayingCardDeck {

    private List<PlayingCard> remainingCards;
    private List<PlayingCard> cardsInFullDeck;
    private final Random random = new Random();

    public PlayingCardDeckImpl() {
        this.remainingCards = new ArrayList<>();
        this.cardsInFullDeck = new ArrayList<>();
    }

    @Override
    public PlayingCard revealCardByIndex(int index) {
        return remainingCards.get(index);
    }

    @Override
    public PlayingCard removeCardByIndex(int index) {
        return remainingCards.remove(index);
    }

    @Override
    public PlayingCard revealTopCard() {
        return remainingCards.get(0);
    }

    @Override
    public PlayingCard removeTopCard() {
        return remainingCards.remove(0);
    }

    @Override
    public PlayingCard revealRandomCard() {
        int randomIndex = random.nextInt(remainingCards.size());
        return remainingCards.get(randomIndex);
    }

    @Override
    public PlayingCard removeRandomCard() {
        int randomIndex = random.nextInt(remainingCards.size());
        return remainingCards.remove(randomIndex);
    }

    public PlayingCard[] removeNCards(int n){
        PlayingCard[] result = new PlayingCard[n];

        for(int i = 0; i < n; i++) {
            result[i] = removeTopCard();
        }

        return result;
    }

    @Override
    public boolean isEmpty() {
        return remainingCards.isEmpty();
    }

    @Override
    public int numCardsRemaining() {
        return remainingCards.size();
    }

    @Override
    public int numCardsInFullDeck() {
        return cardsInFullDeck.size();
    }

    public void addCardToFullDeck(PlayingCard card){
        cardsInFullDeck.add(card);
    }

    @Override
    public void push(PlayingCard card) {
        remainingCards.add(0, card);
    }

    @Override
    public void enqueue(PlayingCard card) {
        remainingCards.add(card);
    }

    @Override
    public void sortDeck() {
        Collections.sort(remainingCards);
    }

    @Override
    public void reverseDeck() {
        Collections.reverse(remainingCards);
    }

    @Override
    public void shuffleDeck() {
        Collections.shuffle(remainingCards);
    }

    public void clearAll(){
        remainingCards.clear();
    }

    @Override
    public List<PlayingCard> listAllCards() {
        return new ArrayList<>(remainingCards);
    }
}