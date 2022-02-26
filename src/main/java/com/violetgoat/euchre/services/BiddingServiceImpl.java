package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.*;

import java.util.List;

public class BiddingServiceImpl implements  BiddingService {

    // == constants ==

    private static final double BID_THRESHOLD = 4.5;
    private static final double GOING_ALONE_THRESHOLD = 10.0;

    private static final double RIGHT_BOWER_VALUE = 3.0;
    private static final double LEFT_BOWER_VALUE = 2.0;
    private static final double ACE_TRUMP_VALUE = 1.75;
    private static final double KING_TRUMP_VALUE = 1.5;
    private static final double QUEEN_TRUMP_VALUE = 1.4;
    private static final double TEN_TRUMP_VALUE = 1.2;
    private static final double NINE_TRUMP_VALUE = 1.1;
    private static final double ACE_NONTRUMP_VALUE = 1.5;
    private static final double KING_NONTRUMP_VALUE = 1.0;
    private static final double QUEEN_NONTRUMP_VALUE = .95;
    private static final double JACK_NONTRUMP_VALUE = .9;
    private static final double TEN_NONTRUMP_VALUE = .85;
    private static final double NINE_NONTRUMP_VALUE = .8;

    // == fields ==

    private Suit topCardSuit;
    private final PlayerService playerService;
    private final PlayerCardSelector playerCardSelector;
    private int dealerIndex;
    private int bidRound;
    private int bidNum;
    private double bidScore;

    // == constructor ==

    public BiddingServiceImpl(PlayerService playerService, PlayerCardSelector playerCardSelector){
        this.playerService = playerService;
        this.playerCardSelector = playerCardSelector;
    }

    // public (interface) methods

    @Override
    public void initializeBid(int dealerIndex, PlayingCard topCard) {
        this.dealerIndex = dealerIndex;
        this.topCardSuit = topCard.getSuit();
        this.bidNum = 0;
        this.bidRound = 0;
        this.bidScore = -1.0;
    }

    @Override
    public Bid getNextComputerBid() {

        boolean pass;
        boolean goingAlone;
        Suit suit = topCardSuit;
        boolean orderingUp;

        if(bidRound == 0){
            bidScore = generateBidScore(topCardSuit);
            orderingUp = true;
        } else {
            suit = getBestSuit();
            orderingUp = false;
        }

        pass = evaluatePass();
        goingAlone = evaluateGoingAlone();

        Bid result = new BidImpl()
                .setBiddingPlayerIndex( (dealerIndex + bidNum + 1) % 4)
                .setOrderingUp(orderingUp)
                .setPass(pass)
                .setSuit(suit)
                .setGoingAlone(goingAlone);

        advanceBidder();
        return result;

    }

    @Override
    public Bid getNextPlayerBid() {
        Bid bid = null;

        if(bidRound == 0){
            bid = playerCardSelector.getNextFirstRoundBid();
        } else {
            bid = playerCardSelector.getNextSecondRoundBid();
        }

        advanceBidder();
        return bid;
    }

    @Override
    public void wrapUpBid() {
        // currently no logic is needed
    }

    // == private methods ==

    private double generateBidScore(Suit potentialTrump){

        double result = 1;

        List<PlayingCard> cards = playerService
                .getPlayerHandByIndex((dealerIndex + bidNum + 1) % 4);

        for(PlayingCard card : cards){
            result = result * cardScore(card, potentialTrump);
        }

        return result;
    }

    private Suit getBestSuit(){

        Suit result = null;
        bidScore = 0;

        for(Suit suit : Suit.values()){

            // player may not choose the suit of the revealed card as trump
            // during second round of bidding
            if(suit.equals(topCardSuit)){ continue; }

            double score = generateBidScore(suit);

            if(score > bidScore){
                bidScore = score;
                result = suit;
            }

        }

        return result;
    }

    private double cardScore(PlayingCard card, Suit potentialTrump){

        double result = 1;
        Suit suit = card.getSuit();
        Rank rank = card.getRank();

        if(rank == Rank.JACK){

            if (card.getSuit() ==  potentialTrump) { result = RIGHT_BOWER_VALUE; }
            else if (card.getSuit() == potentialTrump.sisterSuit()){ result = LEFT_BOWER_VALUE; }
            else {result = JACK_NONTRUMP_VALUE; }

        } else if (suit == potentialTrump) {

            switch(rank) {
                case ACE:
                    result = ACE_TRUMP_VALUE;
                    break;
                case KING:
                    result = KING_TRUMP_VALUE;
                    break;
                case QUEEN:
                    result = QUEEN_TRUMP_VALUE;
                    break;
                case TEN:
                    result = TEN_TRUMP_VALUE;
                    break;
                case NINE:
                    result = NINE_TRUMP_VALUE;
                    break;
            }

        } else {
            switch(rank) {
                case ACE:
                    result = ACE_NONTRUMP_VALUE;
                    break;
                case KING:
                    result = KING_NONTRUMP_VALUE;
                    break;
                case QUEEN:
                    result = QUEEN_NONTRUMP_VALUE;
                    break;
                case TEN:
                    result = TEN_NONTRUMP_VALUE;
                    break;
                case NINE:
                    result = NINE_NONTRUMP_VALUE;
                    break;
            }
        }


        return result;

    }

    private boolean evaluatePass(){

        // dealer is not allowed to pass on the second round
        if( bidRound == 1 && (dealerIndex == (dealerIndex + bidNum + 1) % 4)) {
            return false;
        } else {
            return bidScore < BID_THRESHOLD;
        }

    }

    private boolean evaluateGoingAlone(){
        return bidScore > GOING_ALONE_THRESHOLD;
    }

    private void advanceBidder(){
        bidNum++;
        if(bidNum % 4 ==  0) { bidRound = 1; }
    }

    private boolean isValidBid(Bid bid){
        if(bidRound == 1 && bid.getSuit() == topCardSuit){
            return false;
        }

        return true;
    }

}
