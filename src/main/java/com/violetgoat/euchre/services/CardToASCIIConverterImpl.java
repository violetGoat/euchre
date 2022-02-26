package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.PlayingCard;

import java.util.List;

public class CardToASCIIConverterImpl implements CardToASCIIConverter {

    // == fields ==
    private static final String CARD_TEMPLATE =
                    ",-------.\n" +
                    "|XXX    |\n" +
                    "| | S | |\n" +
                    "|    YYY|\n" +
                    "'-------'";

    private static final String EMPTY_TEMPLATE =
                    ",-------.\n" +
                    "|   -   |\n" +
                    "|   -   |\n" +
                    "|   -   |\n" +
                    "'-------'";

    // == methods ==

    public String getTextArtStringFromCard(PlayingCard card, int spacesBefore, int spacesAfter){

        String result = card == null ?
                EMPTY_TEMPLATE :
                CARD_TEMPLATE;

        result = addSpacesBeforeAfterCardString(result, spacesBefore, spacesAfter);

        if(card == null) {
            return result;
        }

        String suitString = card.getSuit().toShortString();

        String cardTopString = card.nameShort().length() == 3 ?
                card.nameShort() :
                card.nameShort() + " ";

        String cardBottomString = card.nameShort().length() == 3 ?
                card.nameShort() :
                " " + card.nameShort();

        return  result
                .replace("XXX", cardTopString)
                .replace("YYY", cardBottomString)
                .replace("S", suitString);
    }

    public String getTextArtStringFromCard(PlayingCard card){
        return getTextArtStringFromCard(card, 0, 0);
    }

    public int getCardWidth(){
        return CARD_TEMPLATE.indexOf('\n');
    }

    public int getCardHeight(){
        int rowCount = 1;
        for (char c : CARD_TEMPLATE.toCharArray()){
            if(c == '\n') rowCount++;
        }
        return rowCount;
    }

    @Override
    public String getTextArtMultipleCardsHorizontal(List<PlayingCard> cards) {
        return getTextArtMultipleCardsHorizontal(cards, 0);
//        String[] resultRows = new String[getCardHeight()];
//        String resultString = "";
//
//        for (int i = 0; i < cards.size(); i++) {
//            String cardString = getTextArtStringFromCard(cards.get(i));
//            String[] cardRows = cardString.split("\n");
//            for(int j = 0; j < resultRows.length; j++){
//                if(resultRows[j] == null){
//                    resultRows[j] = "";
//                }
//                resultRows[j] += cardRows[j];
//            }
//        }
//
//        for(String row : resultRows){
//            resultString += row + "\n";
//        }
//
//        return resultString.trim();
    }

    public String getTextArtMultipleCardsHorizontal(List<PlayingCard> cards, int spacesBetween) {

        String[] resultRows = new String[getCardHeight()];
        String resultString = "";

        for (int i = 0; i < cards.size(); i++) {
            String cardString = getTextArtStringFromCard(cards.get(i));
            String[] cardRows = cardString.split("\n");
            for(int j = 0; j < resultRows.length; j++){
                if(resultRows[j] == null){
                    resultRows[j] = "";
                }
                resultRows[j] += cardRows[j];
                if (i != cards.size() - 1){
                    resultRows[j] = addSpacesAfter(resultRows[j], spacesBetween);
                }
            }
        }

        for(String row : resultRows){
            resultString += row + "\n";
        }

        return resultString.trim();
    }

    private String addSpacesBeforeAfterCardString(String cardString, int spacesBefore, int spacesAfter){

        String result = "";

        String[] cardArray = cardString.split("\n");

        for(String s : cardArray){
            s = addSpacesBefore(s, spacesBefore);
            s = addSpacesAfter(s, spacesAfter);
            result = result + s + "\n";
        }

        // remove final "\n"
        return result.substring(0, result.length()-1);
    }


    private String addSpacesAfter(String s, int n){
        String result = s;
        if(n < 0) throw new IllegalStateException("n must be positive");
        for(int i = 0; i < n; i++){
            result += " ";
        }
        return result;
    }

    private String addSpacesBefore(String s, int n){
        String result = s;
        if(n < 0) throw new IllegalStateException("n must be 0 or positive");
        for(int i = 0; i < n; i++){
            result = " " + result;
        }
        return result;
    }

}
