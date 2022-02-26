package com.violetgoat.euchre.model;

public class BidImpl implements Bid {

    // == fields ==

    private Suit suit = null;
    private int biddingPlayerIndex = -1;
    private boolean isOrderingUp = true;
    private boolean isPass = true;
    private boolean isGoingAlone = false;

    // == constructor (builder methods below) ==

    public BidImpl(){}

    // == public (interface) methods

    @Override
    public Suit getSuit() {
        if(this.isPass()){
            throw new IllegalStateException("getSuit(): Illegal state--" +
                    "player passed (so no suit was selected).");
        }
        return suit;
    }

    @Override
    public int getPlayerIndex() {
        if(biddingPlayerIndex == -1){
            throw new IllegalStateException("getBiddingPlayerIndex(): Illegal state --" +
                    "bidding player was not properly set.");
        }
        return biddingPlayerIndex;
    }

    @Override
    public boolean isOrderingUp() {
        return isOrderingUp;
    }

    @Override
    public boolean isPass() {
        return isPass;
    }

    @Override
    public boolean isGoingAlone() {
        return isGoingAlone;
    }

    // == public (builder) methods

    public BidImpl setSuit(Suit suit) {
        this.suit = suit;
        return this;
    }

    public BidImpl setBiddingPlayerIndex(int biddingPlayerIndex) {
        this.biddingPlayerIndex = biddingPlayerIndex;
        return this;
    }

    public BidImpl setOrderingUp(boolean orderingUp) {
        isOrderingUp = orderingUp;
        return this;
    }

    public BidImpl setPass(boolean pass) {
        isPass = pass;
        return this;
    }

    public BidImpl setGoingAlone(boolean goingAlone) {
        isGoingAlone = goingAlone;
        return this;
    }
}
