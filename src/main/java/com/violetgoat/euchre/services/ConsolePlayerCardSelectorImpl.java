package com.violetgoat.euchre.services;

import com.violetgoat.euchre.console.ConsoleView;
import com.violetgoat.euchre.model.Bid;
import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;

public class ConsolePlayerCardSelectorImpl implements PlayerCardSelector {

    // == fields ==
    private ConsoleView consoleView;
    private int humanPlayerIndex = 0;


    // == constructor ==
    public ConsolePlayerCardSelectorImpl(ConsoleView consoleView){
        this.consoleView = consoleView;
    }

    @Override
    public Play getDealerDiscard() {
        PlayingCard playingCard = consoleView.playerDiscardCard();
        return Play.discard(humanPlayerIndex, playingCard);
    }

    @Override
    public Play getNextPlay() {
        PlayingCard playingCard = consoleView.playerPlayCard();
        return Play.of(humanPlayerIndex, playingCard);
    }

    @Override
    public Bid getNextFirstRoundBid() {
        return consoleView.getPlayerFirstRoundBid();
    }

    @Override
    public Bid getNextSecondRoundBid() {
        return consoleView.getPlayerSecondRoundBid();
    }
}
