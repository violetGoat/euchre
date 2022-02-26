package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Suit;

import java.util.List;

public interface PlayerService {

    List<Integer> getScores();
    List<PlayingCard> getHumanPlayerHand();
    List<PlayingCard> getPlayerHandByIndex(int playerIndex);
    int getPlayerHandSize(int playerIndex);
    void addCardToPlayerHand(PlayingCard card, int playerIndex);
    boolean playerHasSuit(int playerIndex, Suit suit);
    PlayingCard playerPlayCard(int playerIndex, PlayingCard card);
    PlayingCard playerPlayCardByIndex(int playerIndex, int cardIndex);
    void wrapUpHand();
    void increaseTeamScoreBy(int team, int amount);

}
