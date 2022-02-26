package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.EuchreDeckImpl;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.PlayingCardDeck;

public class DealerImpl implements Dealer {

    // == fields ==
    PlayerService playerService;
    PlayingCardDeck deck;

    // == constructor ==
    public DealerImpl(PlayerService playerService){
        this.playerService = playerService;
        this.deck = new EuchreDeckImpl();

    }

    // == public (interface) methods

    @Override
    public void dealHand() {

        deck.shuffleDeck();

        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                playerService.addCardToPlayerHand(deck.removeTopCard(), j);
            }
        }

    }

    @Override
    public PlayingCard revealTopCard() {
        return deck.revealTopCard();
    }

    public void initializeDeck(){
        deck = new EuchreDeckImpl();
    }

    // == private methods ==


}
