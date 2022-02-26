package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.PlayingCard;

import java.util.List;

public interface CardToASCIIConverter {

    public String getTextArtStringFromCard(PlayingCard card);
    public String getTextArtStringFromCard(PlayingCard card, int spacesBefore, int spacesAfter);
    public String getTextArtMultipleCardsHorizontal(List<PlayingCard> card);
    public String getTextArtMultipleCardsHorizontal(List<PlayingCard> card, int spacesBetween);
    public int getCardWidth();
    public int getCardHeight();

}
