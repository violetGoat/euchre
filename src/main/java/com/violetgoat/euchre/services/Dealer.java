package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.PlayingCard;

public interface Dealer {

    void dealHand();
    PlayingCard revealTopCard();
    void initializeDeck();

}
