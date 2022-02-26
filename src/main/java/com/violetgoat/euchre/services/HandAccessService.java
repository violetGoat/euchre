package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.CompletedTrick;
import com.violetgoat.euchre.model.Hand;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Suit;

public interface HandAccessService {

    boolean isBiddingComplete();
    Suit getTrumpSuit();
    CompletedTrick getCompletedTrickNum(int num);
    int getNumTricksCompleted();
    int getCurrentPlayerIndex();
    PlayingCard getRevealedCard();
    int getDealerIndex();




}
