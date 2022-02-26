package com.violetgoat.euchre.model;

public interface Bid {

    Suit getSuit();
    int getPlayerIndex();
    boolean isOrderingUp();
    boolean isPass();
    boolean isGoingAlone();

}
