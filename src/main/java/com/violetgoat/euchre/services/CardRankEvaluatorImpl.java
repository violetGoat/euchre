package com.violetgoat.euchre.services;


import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Rank;
import com.violetgoat.euchre.model.Suit;

import java.util.*;

public class CardRankEvaluatorImpl implements CardRankEvaluator {

    // == fields ==

    private final HandAccessService handAccessService;
    private final TrickAccessService trickAccessService;
    private final PlayerService playerService;
    private List<PlayingCard> winningCardRankList;
    private Map<Suit, List<PlayingCard>> cardsRankedBySuit;
    private boolean handInitialized;
    private boolean trickInitialized;
    private final CardRankComparator cardRankComparator;
    private final HandOrderComparator handOrderComparator;

    // == constructor ==
    public CardRankEvaluatorImpl(HandAccessService handAccessService, TrickAccessService trickAccessService, PlayerService playerService){
        this.handAccessService = handAccessService;
        this.trickAccessService = trickAccessService;
        this.handInitialized = false;
        this.trickInitialized = false;
        this.winningCardRankList = new ArrayList<>();
        this.cardsRankedBySuit = new HashMap<>();
        this.playerService = playerService;
        this.cardRankComparator = new CardRankComparator();
        this.handOrderComparator = new HandOrderComparator();
    }

    // == public (interface) methods ==

    @Override
    public void initializeHandAfterBid(){
        initializeCardsRankedBySuit();
        this.handInitialized = true;
    }

    @Override
    public void resetHand(){
        this.handInitialized = false;
        this.trickInitialized = false;
        this.winningCardRankList = new ArrayList<>();
    }

    @Override
    public void initializeTrickAfterLead(){
        initializeWinningCardRankList();
        this.trickInitialized = true;
    }

    @Override
    public boolean cardBeatsCurrentWinner(PlayingCard card) {

        if (trickAccessService.getNumCardsPlayed() == 0) {
            initializeHandAfterBid();
            return true;
        } else if (!trickInitialized) {
            initializeWinningCardRankList();
        }

        PlayingCard currentWinningCard = trickAccessService.getCurrentWinningCard();

        return cardBeatsCard(card, currentWinningCard);

    }

    public boolean cardBeatsCard(PlayingCard cardOne, PlayingCard cardTwo){

        int cardOneRank = winningCardRankList.indexOf(cardOne);

        if( cardOneRank  == -1 ){
            return false;
        }

        return cardOneRank < winningCardRankList.indexOf(cardTwo);

    }

    @Override
    public Suit getEuchreSuit(PlayingCard card){

        Suit suit = card.getSuit();

        if(card.getRank() == Rank.JACK
                && suit.sisterSuit() == handAccessService.getTrumpSuit()
        ) {
            suit = suit.sisterSuit();
        }

        return suit;

    }

    @Override
    // assumes player has the card in question
    public boolean isValidPlay(Play play) {

        int playerIndex = play.getPlayerIndex();
        PlayingCard card = play.getCard();

        // if this would be the first play, it is valid
        if (trickAccessService.getNumCardsPlayed() == 0){
            return true;
        }

        Suit leadSuit = trickAccessService.getLeadSuit();

        // card is valid if its suit matches the lead suit OR
        // if the player does not have any cards of the lead suit

        return getEuchreSuit(card) == leadSuit ||
                (!playerHasSuit(playerIndex, leadSuit));

    }

    @Override
    public PlayingCard getRightBower(){
        if(!handAccessService.isBiddingComplete()){
            throw new IllegalStateException(
                    "getRightBower(): Illegal state --" +
                    "method may not be called until bidding is complete"
            );
        }
        return new PlayingCard(Rank.JACK, handAccessService.getTrumpSuit());
    }

    @Override
    public PlayingCard getLeftBower(){

        if(!handAccessService.isBiddingComplete()){
            throw new IllegalStateException(
                    "getRightBower(): Illegal state --" +
                            "method may not be called until bidding is complete"
            );
        }

        return new PlayingCard(Rank.JACK, handAccessService.getTrumpSuit().sisterSuit());
    }

    @Override
    public boolean cardIsTrump(PlayingCard card) {
        return getEuchreSuit(card).equals(handAccessService.getTrumpSuit());
    }

    @Override
    public List<PlayingCard> naiveRanking(List<PlayingCard> cardsToRank) {
        List<PlayingCard> rankedCards = new ArrayList<>(cardsToRank);
        Collections.sort(rankedCards, cardRankComparator);

        return rankedCards;
    }

    @Override
    public List<PlayingCard> sortHand(List<PlayingCard> cards){
        List<PlayingCard> sortedHand = cards;
        Collections.sort(sortedHand, handOrderComparator);
        return sortedHand;
    }

    // == private methods ==

    private void initializeWinningCardRankList(){

        if(trickAccessService.getNumCardsPlayed() < 1){

            throw new IllegalStateException("" +
                    "initializeWinningCardRankList(): Illegal state --" +
                    "method may not be called until lead card is played " +
                    "in current trick."
            );
        }

        if(!handInitialized){
            throw new IllegalStateException("" +
                    "initializeWinningCardRankList(): Illegal state --" +
                    "method may not be called card rank list is initialized --" +
                    "(initializeCardsRankedBySuit())"
            );
        }

        this.winningCardRankList = new ArrayList<>();

        Suit leadSuit = trickAccessService.getLeadSuit();
        Suit trumpSuit = handAccessService.getTrumpSuit();

        this.winningCardRankList.addAll(getCardRankOrderForSuit(trumpSuit));
        this.winningCardRankList.addAll(getCardRankOrderForSuit(leadSuit));

    }

    private void initializeCardsRankedBySuit(){
        for (Suit suit: Suit.values()) {
            List<PlayingCard> cardList = new ArrayList<>();
            cardList.addAll(generateCardRankOrderForSuit(suit));
            cardsRankedBySuit.put(suit, cardList);
        }
    }

    private List<PlayingCard> getCardRankOrderForSuit(Suit suit){
        if(cardsRankedBySuit.get(suit) == null){
            throw new IllegalStateException("getCardRankOrderForSuit( " +
                    suit + "):" +
                    "card rank order must be initialized 'initializeCardsRankedBySuit()'" +
                    "before this method may be called");
        }

        return cardsRankedBySuit.get(suit);

    }

    private List<PlayingCard> generateCardRankOrderForSuit(Suit suit){
        if(!handAccessService.isBiddingComplete()){
            throw new IllegalStateException(
                    "generateCardRankOrderForSuit(): Illegal state --" +
                    "method may not be called until bidding is complete)"
            );
        }

        List<PlayingCard> cardRankOrder  = new ArrayList<>();

        Suit trumpSuit = handAccessService.getTrumpSuit();
        boolean suitIsTrump = (suit == trumpSuit);
        boolean suitIsTrumpSisterSuit = (suit.sisterSuit() == trumpSuit);


        // right bower (jack of trump suit)
        // and left bower (jack of same color suit)
        // are the top two ranked cards in the trump suit
        if(suitIsTrump){
            cardRankOrder.add(getRightBower());
            cardRankOrder.add(getLeftBower());
        }

        cardRankOrder.add(new PlayingCard(Rank.ACE, suit));
        cardRankOrder.add(new PlayingCard(Rank.KING, suit));
        cardRankOrder.add(new PlayingCard(Rank.QUEEN, suit));

        // jack of trump suit has already been added (above)
        // jack of same color switches suits for this hand
        if(!suitIsTrump && !suitIsTrumpSisterSuit){
            cardRankOrder.add(new PlayingCard(Rank.JACK, suit));
        }

        cardRankOrder.add(new PlayingCard(Rank.TEN, suit));
        cardRankOrder.add(new PlayingCard(Rank.NINE, suit));

        return cardRankOrder;
    }

    private int getRankInSuit(PlayingCard card){

        if(! handInitialized ){
            throw new IllegalStateException("getRankInSuit(PlayingCard card): Illegal state --" +
                    "method may not be called until hand is initialized " +
                    "(by calling initializeHandAfterBid()");
        }

        return cardsRankedBySuit.get(getEuchreSuit(card)).indexOf(card);
    }

    private boolean playerHasSuit(int player, Suit suit){

        for(PlayingCard card : playerService.getPlayerHandByIndex(player)) {
            if(getEuchreSuit(card) == suit){
                return true;
            }
        }

        return false;

    }

    private class HandOrderComparator implements Comparator<PlayingCard>{

        @Override
        public int compare(PlayingCard cardOne, PlayingCard cardTwo) {

            if(! handInitialized ){
                throw new IllegalStateException("SuitFirstComparator.compare(PlayingCard cardOne, PlayingCard cardTwo) -- Illegal state --" +
                        "method may not be called until hand is initialized " +
                        "(by calling initializeHandAfterBid()");
            }

            if( cardOne.equals(cardTwo) ){ return 0; }

            int suitComparison = handOrderSuitCompare(cardOne, cardTwo);

            if(suitComparison != 0){ return suitComparison; }

            return getRankInSuit(cardTwo) - getRankInSuit(cardOne);

        }

        private int handOrderSuitCompare(PlayingCard cardOne, PlayingCard cardTwo){
            return getEuchreSuit(cardOne).compareTo(getEuchreSuit(cardTwo));
        }

    }

    private class CardRankComparator implements Comparator<PlayingCard>{
        @Override
        public int compare(PlayingCard cardOne, PlayingCard cardTwo) {
            if(! handInitialized ){
                throw new IllegalStateException("compare(PlayingCard cardOne, PlayingCard cardTwo) -- Illegal state --" +
                        "method may not be called until hand is initialized " +
                        "(by calling initializeHandAfterBid()");
            }

            if( cardOne.equals(cardTwo) ){ return 0; }

            int suitComparison = cardRankSuitCompare(cardOne, cardTwo);

            if(suitComparison != 0){ return suitComparison; }

            return getRankInSuit(cardTwo) - getRankInSuit(cardOne);

        }

        private int cardRankSuitCompare(PlayingCard cardOne, PlayingCard cardTwo){
            Suit suitOne = getEuchreSuit(cardOne);
            Suit suitTwo = getEuchreSuit(cardTwo);

            if(suitOne == suitTwo){
                return 0;
            }

            Suit trumpSuit = handAccessService.getTrumpSuit();

            if(suitOne == trumpSuit) { return 1; }
            if(suitTwo == trumpSuit) { return -1; }

            if (trickInitialized){
                Suit leadSuit = trickAccessService.getLeadSuit();
                if(suitOne == leadSuit) { return 1; }
                if(suitTwo == leadSuit) { return -1; }
            }

            return 0;
        }

    }


}


