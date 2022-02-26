package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Bid;
import com.violetgoat.euchre.model.Play;

public interface CardSelectionService {

    void initializeCardSelection();
    Play getComputerDiscard();
    Play getPlayerDiscard();
    Play getNextComputerPlay();
    Play getNextPlayerPlay();
    void wrapUpCardSelection();
}
