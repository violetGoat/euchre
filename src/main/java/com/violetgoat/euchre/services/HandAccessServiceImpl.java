package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.CompletedTrick;
import com.violetgoat.euchre.model.Hand;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Suit;

public class HandAccessServiceImpl implements HandAccessService {

    // == fields ==
    private Hand hand;

    // == constructor ==

    public HandAccessServiceImpl(Hand hand) {
        this.hand = hand;
    }

    // == public (interface) methods ==

    @Override
    public boolean isBiddingComplete() {
        return hand.isBiddingComplete();
    }

    @Override
    public Suit getTrumpSuit() {
        Suit trumpSuit =hand.getTrumpSuit();
        if(trumpSuit == null){
            throw new IllegalStateException("getTrumpSuit(): illegal state--" +
                    "method may not be called until trump suit is set.");
        }
        return trumpSuit;
    }

    @Override
    public CompletedTrick getCompletedTrickNum(int num) {
        return hand.getCompletedTrickNum(num);
    }

    @Override
    public int getNumTricksCompleted() {
        return hand.getNumTricksCompleted();
    }

    public int getCurrentPlayerIndex(){
        return hand.getCurrentPlayerIndex();
    }

    public PlayingCard getRevealedCard(){
        return hand.getRevealedCard();
    }

    public int getDealerIndex(){
        return hand.getDealerIndex();
    }

}
