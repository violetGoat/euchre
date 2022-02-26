package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Suit;

import java.util.List;

public interface CardRankEvaluator {

    void initializeTrickAfterLead();
    void initializeHandAfterBid();
    Suit getEuchreSuit(PlayingCard card);
    void resetHand();
    boolean isValidPlay(Play play);
    boolean cardBeatsCurrentWinner(PlayingCard card);
    boolean cardBeatsCard(PlayingCard cardOne, PlayingCard cardTwo);
    boolean cardIsTrump(PlayingCard card);
    PlayingCard getRightBower();
    PlayingCard getLeftBower();
    List<PlayingCard> naiveRanking(List<PlayingCard> cardsToRank);
    List<PlayingCard> sortHand(List<PlayingCard> cards);


}
