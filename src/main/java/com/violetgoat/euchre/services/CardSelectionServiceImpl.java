package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Play;

public class CardSelectionServiceImpl implements CardSelectionService {

    // == fields ==
    private PlayerCardSelector playerCardSelector;
    private ComputerCardSelector computerCardSelector;

    // == constructor ==
    public CardSelectionServiceImpl(PlayerCardSelector playerCardSelector, ComputerCardSelector computerCardSelector){
        this.playerCardSelector = playerCardSelector;
        this.computerCardSelector = computerCardSelector;
    }

    // == public (interface) methods ==

    @Override
    public void initializeCardSelection() {
        computerCardSelector.initializeCardSelection();
    }

    @Override
    public Play getComputerDiscard() {
        return computerCardSelector.getDealerDiscard();
    }

    @Override
    public Play getPlayerDiscard() {
        return playerCardSelector.getDealerDiscard();
    }

    @Override
    public Play getNextComputerPlay() {
        return computerCardSelector.getNextPlay();
    }

    @Override
    public Play getNextPlayerPlay() {
        return playerCardSelector.getNextPlay();
    }

    @Override
    public void wrapUpCardSelection() {

    }
}
