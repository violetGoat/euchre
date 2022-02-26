package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Rank;
import com.violetgoat.euchre.model.Suit;

import java.util.List;

public class ComputerCardSelectorImpl implements ComputerCardSelector{

    // == fields ==

    private final CardRankEvaluator cardRankEvaluator;
    private final HandAccessService handAccessService;
    private final PlayerService playerService;
    private final TrickAccessService trickAccessService;
    private List<PlayingCard> currentPlayerRankedCards = null;

    // == constructor ==

    public ComputerCardSelectorImpl(CardRankEvaluator cardRankEvaluator, HandAccessService handAccessService, PlayerService playerService, TrickAccessService trickAccessService){
        this.cardRankEvaluator = cardRankEvaluator;
        this.handAccessService = handAccessService;
        this.trickAccessService = trickAccessService;
        this.playerService = playerService;
    }

    // public (interface) methods ==

    @Override
    public Play getDealerDiscard() {
        return null;
    }

    @Override
    // currently returns the highest ranked playable card
    // without consideration of whether it will win the hand
    // TODO: implement more robust AI
    public Play getNextPlay() {

        resetRankedCards();

        PlayingCard bestCard = null;

        List<PlayingCard> rankedCards = rankedCards();

        if(thisIsTheFirstPlay() && handAccessService.getNumTricksCompleted() == 0){
            bestCard = getLeadPlay();
            playerService.playerPlayCard(playerIndex(), bestCard);
            return Play.of(playerIndex(), bestCard);
        }

        for(int i = rankedCards.size()-1; i >= 0; i--){
            if(cardIsPlayable(rankedCards.get(i))){
                bestCard = rankedCards.get(i);
                break;
            }
        }

        if(
                !(thisIsTheFirstPlay()) &&
                (!(cardRankEvaluator.cardBeatsCurrentWinner(bestCard)) ||
                partnerHasTheTrick())
        ){
            bestCard = getThrowawayCard();
        }


        if(bestCard == null) {
            throw new RuntimeException("getNextPlay(): Error--" +
                    "method was unable to identify any playable cards.");
        }

        playerService.playerPlayCard(playerIndex(), bestCard);
        return Play.of(playerIndex(), bestCard);
    }

    @Override
    public void initializeCardSelection() {

        //TODO: have this complete internally (within cardRankEvaluator) as needed
        this.cardRankEvaluator.initializeHandAfterBid();
    }

    // == private methods ==
    private Suit trumpSuit(){
        return handAccessService.getTrumpSuit();
    }

    private List<PlayingCard> availableCards(){
        return playerService.getPlayerHandByIndex(playerIndex());
    }

    private int playerIndex(){
        return handAccessService.getCurrentPlayerIndex();
    }

    private boolean cardIsPlayable(PlayingCard card){
        return cardRankEvaluator.isValidPlay(Play.of(playerIndex(), card));
    }

    // Currently returns a random card of the lowest available rank
    // (non-trump and non-lead suit if possible)
    // TODO: implement more robust logic for determination of best
    // cards to throw away.
    private PlayingCard getThrowawayCard(){

        List<PlayingCard> rankedCards = rankedCards();

        for(int i = 0; i < rankedCards.size(); i++){
            if(cardIsPlayable(rankedCards.get(i))){
                return rankedCards.get(i);
            }
        }

        System.out.println("Ranked cards:");
        printCards(rankedCards);
        throw new RuntimeException("getThrowawayCard(): Error--" +
                "method was unable to identify any playable cards.");

    }

    public PlayingCard getHighOffSuit(){

        List<PlayingCard> rankedCards = rankedCards();

        for(int i = rankedCards.size()-1; i >= 0; i--){
            PlayingCard card = rankedCards.get(i);
            if(cardRankEvaluator.getEuchreSuit(card) != handAccessService.getTrumpSuit()){
                return card;
            }
        }

        return rankedCards.get(rankedCards.size()-1);

    }

    public PlayingCard getLeadPlay(){

        PlayingCard card = getHighOffSuit();

        if(
                cardIsTrump(card) ||
                card.getRank() == Rank.ACE ||
                card.getRank() == Rank.KING
        ){
            return card;
        }

        card = getHighestCard().equals(cardRankEvaluator.getRightBower()) ?
            getHighestCard() : getThrowawayCard();

        return card;
    }

    private List<PlayingCard> currentPlayerHand(){
        int player = handAccessService.getCurrentPlayerIndex();
        return playerService.getPlayerHandByIndex(player);
    }

    private List<PlayingCard> rankedCards(){

        if(this.currentPlayerRankedCards == null) {
            currentPlayerRankedCards = cardRankEvaluator.naiveRanking(currentPlayerHand());
        }

        return currentPlayerRankedCards;

    }

    private PlayingCard getHighestCard(){
        return rankedCards().get(rankedCards().size()-1);
    }

    private boolean partnerHasTheTrick(){
        return (playerIndex() % 2) == (trickAccessService.getWinningPlayerIndex() % 2);
    }

    private boolean thisIsTheFirstPlay(){
        return trickAccessService.getNumCardsPlayed() == 0;
    }

    private void resetRankedCards(){
        this.currentPlayerRankedCards = null;
    }

    private boolean cardIsTrump(PlayingCard card){
        return cardRankEvaluator.cardIsTrump(card);
    }

    private void printCards(List<PlayingCard> cards){
        for(PlayingCard card : cards){
            System.out.print("\t" + card.nameShort());
        }
        System.out.println();
    }



}
