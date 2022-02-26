package com.violetgoat.euchre.model;

public class EuchreDeckImpl extends PlayingCardDeckImpl {

    public EuchreDeckImpl() {

        Rank[] euchreRanks = {Rank.ACE, Rank.NINE, Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING};

        for (Suit suit : Suit.values()) {
            for(Rank rank : euchreRanks){
                PlayingCard card = new PlayingCard(rank, suit);
                this.enqueue(card);
                this.addCardToFullDeck(card);
            }
        }
    }
}