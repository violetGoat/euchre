package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Bid;
import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;

import java.util.List;

public interface BiddingService {

    void initializeBid(int dealerIndex, PlayingCard topCard);
    Bid getNextComputerBid();
    Bid getNextPlayerBid();
    void wrapUpBid();

}
