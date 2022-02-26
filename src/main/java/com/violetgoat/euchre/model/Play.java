package com.violetgoat.euchre.model;


// == data structure representing one player
// == disposing of one card (play or discard)

public class Play {

    private int playerIndex;
    private PlayingCard card;
    private boolean isDiscard;

    private Play(int playerIndex, PlayingCard card){
        this(playerIndex, card, false);
    }

    private Play(int playerIndex, PlayingCard card, boolean isDiscard){
        this.playerIndex = playerIndex;
        this.card = card;
        this.isDiscard = isDiscard;
    }

    public static Play of(int playerIndex, PlayingCard card){
        return new Play(playerIndex, card);
    }

    public static Play discard(int playerIndex, PlayingCard card){
        return new Play(playerIndex, card, true);
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public PlayingCard getCard() {
        return card;
    }

    public boolean isDiscard() {
        return isDiscard;
    }

}
