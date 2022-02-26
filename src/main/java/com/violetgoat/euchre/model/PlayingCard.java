package com.violetgoat.euchre.model;

public class PlayingCard implements Comparable<PlayingCard> {

    private Rank rank;
    private Suit suit;
    private String name;


    public PlayingCard(Rank rank, Suit suit) {

        this.rank = rank;
        this.suit = suit;
        String rankString = rank.toString().substring(0, 1) + rank.toString().substring(1).toLowerCase();
        String suitString = suit.toString().substring(0, 1) + suit.toString().substring(1).toLowerCase();
        this.name = rankString + " of " + suitString;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public String getName() {
        return name;
    }

    public boolean rankMatches(PlayingCard card) {
        return (this.rank == card.getRank());
    }

    public boolean suitMatches(PlayingCard card) {
        return (this.suit == card.getSuit());
    }

    public String nameShort() {
        String shortSuit = this.suit.toShortString();
        String shortRank = this.rank.toShortString();
        return shortSuit + shortRank;
    }

    boolean rankIsHigherThan(PlayingCard card) {
        if (card == null) return true;
        return this.getRank().compareTo(card.getRank()) > 0;
    }

    public boolean equals(Object object) {
        if(!(object instanceof PlayingCard)){
            return false;
        }

        PlayingCard p = (PlayingCard) object;

        return (this.suit == p.getSuit()
                && this.rank == p.getRank());
    }

    public int hashCode(){
        int result = 17;
        result = result + 31 * this.suit.hashCode();
        result = result + 31 * this.rank.hashCode();
        return result;
    }

    public int compareTo(PlayingCard b){
        int result = 0;

        // compare by suit if different
        if(this.suit != b.suit){
            result = this.suit.compareTo(b.suit);

        // otherwise, compare by rank
        } else if (this.rank != b.rank) {
            result = this.rank.compareTo(b.rank);
        }

        return result;
    }

}
