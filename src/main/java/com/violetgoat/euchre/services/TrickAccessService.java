package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Suit;
import com.violetgoat.euchre.model.Trick;

import java.util.List;

public interface TrickAccessService {

    Suit getLeadSuit();
    int getWinningTeamIndex();
    int getWinningPlayerIndex();
    int getLeadPlayerIndex();
    PlayingCard getLeadCard();
    int getPlayerGoingAlone();
    PlayingCard getCurrentWinningCard();
    int getNumCardsPlayed();
    List<PlayingCard> getPlayedCards();
    boolean trickIsComplete();


    void initializeNewTrick(int leadPlayerIndex);
    void setWinningCard(PlayingCard card);
    void setLeadSuit(Suit suit);
    void setWinningPlayerIndex(int winningPlayerIndex);
    void setPlayerGoingAlone(int playerIndex);
    void addPlay(Play play);


}
