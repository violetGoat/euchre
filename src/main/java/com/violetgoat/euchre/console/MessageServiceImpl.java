package com.violetgoat.euchre.console;

import com.violetgoat.euchre.model.Bid;
import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Suit;
import com.violetgoat.euchre.services.CardToASCIIConverter;

import java.util.ArrayList;
import java.util.List;

public class MessageServiceImpl implements MessageService {

    // == fields ==
    private int humanPlayerIndex = 0;
    private CardToASCIIConverter cardToASCIIConverter;

    // == constructor ==

    public MessageServiceImpl(CardToASCIIConverter cardToASCIIConverter) {
        this.cardToASCIIConverter = cardToASCIIConverter;
    }

    // == public (interface) methods


    @Override
    public void printCard(PlayingCard card) {
        int spaceBefore = cardToASCIIConverter.getCardWidth() * 2;
        System.out.println(cardToASCIIConverter.getTextArtStringFromCard(card, spaceBefore, 0));
    }

    @Override
    public void printCardList(List<PlayingCard> cards) {
        int spacesBetween = 1;
        System.out.println(cardToASCIIConverter.getTextArtMultipleCardsHorizontal(cards, spacesBetween));


        for(int i = 0; i < cards.size(); i++){
            String numberLabel = "  [ " + (i+1) +" ]";
            int numSpaces = cardToASCIIConverter.getCardWidth() - numberLabel.length() + spacesBetween;
            for(int j = 0; j < numSpaces; j++){
                numberLabel += " ";
            }
            System.out.print(numberLabel);
        }
        System.out.println();
    }

    @Override
    public void printSuitList(List<Suit> suits) {
        for(Suit suit : suits){
            System.out.print(suitFormatted(suit) + "\t");
        }

        System.out.println();

        for(int i = 0; i < suits.size(); i++){
            System.out.print("[ " + (i+1) +" ]\t");
        }
        System.out.println();
    }

    @Override
    public void printPlayedCards(List<PlayingCard> playedCards) {

        if(playedCards.size() != 4) throw new IllegalStateException("playedCards must be of size 4 -- null values allowed");

        int spacesBeforeNorthSouthCard = cardToASCIIConverter.getCardWidth() * 2;
        int spacesBetweenEastWestCard = cardToASCIIConverter.getCardWidth() * 3;

        PlayingCard south = playedCards.get(0);
        PlayingCard west = playedCards.get(1);
        PlayingCard north = playedCards.get(2);
        PlayingCard east = playedCards.get(3);

        List<PlayingCard> eastWest = new ArrayList<>();
        eastWest.add(west);
        eastWest.add(east);

        //          NORTH
        System.out.println(cardToASCIIConverter.getTextArtStringFromCard(north, spacesBeforeNorthSouthCard, 0));

        // EAST             WEST
        System.out.println(cardToASCIIConverter.getTextArtMultipleCardsHorizontal(eastWest, spacesBetweenEastWestCard));

        //          SOUTH
        System.out.println(cardToASCIIConverter.getTextArtStringFromCard(south, spacesBeforeNorthSouthCard, 0));

    }

    public void printBid(Bid bid){

        String message = bid.getPlayerIndex() == humanPlayerIndex ?
                "You" :
                "Player " + (bid.getPlayerIndex()+1);

        if(bid.isPass()){
            message += " passed.";
        } else if (bid.isOrderingUp()) {
            message += " ordered up the revealed card, making "
                    + suitFormatted(bid.getSuit()) + " trump.";
        } else {
            message += " selected" +
                    suitFormatted(bid.getSuit()) + " as trump.";
        }

        System.out.println(message);
    }

    @Override
    public String cardFormatted(PlayingCard card){
        return cardToASCIIConverter.getTextArtStringFromCard(card);
    }

    @Override
    public String suitFormatted(Suit suit){
        return "[ " + suit.toShortString() + " ]";
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}
