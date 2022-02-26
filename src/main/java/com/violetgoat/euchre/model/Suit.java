package com.violetgoat.euchre.model;

public enum Suit {
    CLUBS(1), DIAMONDS(2), HEARTS(3), SPADES(4);

    public int suitRank;

    Suit(int suitRank){
        this.suitRank = suitRank;
    }

    public String toString() {
        String suitString = super.toString();
        return suitString.substring(0, 1) + suitString.substring(1).toLowerCase();
    }

    public String toShortString(){

        switch(this){
            case CLUBS:
                return Character.toString((char) 0x2663);
             case DIAMONDS:
                return Character.toString((char) 0x2666);
             case HEARTS:
                return Character.toString((char) 0x2665);
            case SPADES:
                return  Character.toString((char) 0x2660);
        }
        return "";

    }

    public Suit sisterSuit() {

        Suit result = Suit.SPADES;

        switch (this) {
            case HEARTS:
                result =  Suit.DIAMONDS;
                break;
            case SPADES:
                result = Suit.CLUBS;
                break;
            case DIAMONDS:
                result = Suit.HEARTS;
                break;
            case CLUBS:
                result = Suit.SPADES;
        }

        return result;
    }


}
