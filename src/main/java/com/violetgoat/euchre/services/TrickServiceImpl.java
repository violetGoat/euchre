package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.*;

import java.util.List;

public class TrickServiceImpl implements TrickService {

    // == fields ==

    private TrickAccessService trickAccessService;
    private CardRankEvaluator cardRankEvaluator;

    // == constructor ==
    public TrickServiceImpl(CardRankEvaluator cardRankEvaluator, TrickAccessService trickAccessService){
        this.cardRankEvaluator = cardRankEvaluator;
        this.trickAccessService = trickAccessService;
    }

    // == public (interface) methods ==
    @Override
    public void initializeTrick(int leadPlayerIndex) {
        trickAccessService.initializeNewTrick(leadPlayerIndex);
    }

    public void setPlayerGoingAlone(int playerIndex){
        trickAccessService.setPlayerGoingAlone(playerIndex);
    }

    @Override
    public List<PlayingCard> getPlayedCards() {
        return trickAccessService.getPlayedCards();
    }

    @Override
    public int getWinningPlayerIndex() {
        return trickAccessService.getWinningPlayerIndex();
    }

    @Override
    public int getWinningTeam() {
        return trickAccessService.getWinningTeamIndex();
    }

    @Override
    public boolean trickIsComplete() {
       return trickAccessService.trickIsComplete();
    }

    @Override
    public void wrapUpTrick() {

    }

    @Override
    public CompletedTrick packageCompletedTrick() {
        CompletedTrickImpl result = new CompletedTrickImpl();
        result.setPlayedCards(trickAccessService.getPlayedCards());
        result.setWinningPlayerIndex(trickAccessService.getWinningPlayerIndex());
        result.setWinningTeamIndex(trickAccessService.getWinningTeamIndex());
        result.setLeadPlayerIndex(trickAccessService.getLeadPlayerIndex());
        return result;
    }

    @Override
    public void processPlayedCard(Play play) {

        if(trickAccessService.getNumCardsPlayed() == 0){
            trickAccessService.setLeadSuit(cardRankEvaluator.getEuchreSuit(play.getCard()));
        }

        if(cardIsNewWinningCard(play)){
            trickAccessService.setWinningCard(play.getCard());
            trickAccessService.setWinningPlayerIndex(play.getPlayerIndex());
        }

        trickAccessService.addPlay(play);

    }

    // == private methods ==

    private boolean isPlayerGoingAlone(){
        return trickAccessService.getPlayerGoingAlone() > 0;
    }

    private boolean cardIsNewWinningCard(Play play){

        return cardRankEvaluator.cardBeatsCurrentWinner(play.getCard());

    }



}
