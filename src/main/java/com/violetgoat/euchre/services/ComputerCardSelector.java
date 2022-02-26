package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Play;

public interface ComputerCardSelector {

    Play getDealerDiscard();
    Play getNextPlay();
    void initializeCardSelection();

}
