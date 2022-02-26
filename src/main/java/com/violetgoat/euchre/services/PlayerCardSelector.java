package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Bid;
import com.violetgoat.euchre.model.Play;

public interface PlayerCardSelector {

    Play getDealerDiscard();
    Play getNextPlay();
    Bid getNextFirstRoundBid();
    Bid getNextSecondRoundBid();

}
