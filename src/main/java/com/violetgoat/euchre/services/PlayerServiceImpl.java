package com.violetgoat.euchre.services;

import com.violetgoat.euchre.model.Player;
import com.violetgoat.euchre.model.PlayerImpl;
import com.violetgoat.euchre.model.PlayingCard;
import com.violetgoat.euchre.model.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerServiceImpl implements PlayerService{

    // == fields ==

    private List<Player> playerList;
    private List<Integer> scores = Arrays.asList(new Integer[]{0,0});
    private int humanPlayerIndex = 0;

    // == constructor ==

    public PlayerServiceImpl(){
        playerList = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            String name = "Player " + (i+1);
            boolean isComputer = (i != humanPlayerIndex);
            playerList.add(new PlayerImpl(name, isComputer));
        }
    }

    // == public (interface) methods ==

    @Override
    public List<Integer> getScores() {
        return new ArrayList<>(scores);
    }

    public void increaseTeamScoreBy(int team, int amount){
        int newScore = scores.get(team) + amount;
        scores.set(team, newScore);
    }

    @Override
    public List<PlayingCard> getHumanPlayerHand() {
        return playerList.get(humanPlayerIndex)
                .getHand();
    }

    @Override
    public List<PlayingCard> getPlayerHandByIndex(int index){
        return playerList.get(index)
                .getHand();
    }

    @Override
    public int getPlayerHandSize(int playerIndex) {
        return playerList.get(playerIndex).handSize();
    }

    @Override
    public void addCardToPlayerHand(PlayingCard card, int playerIndex) {
        playerList.get(playerIndex)
                .addCard(card);
    }

    @Override
    public boolean playerHasSuit(int playerIndex, Suit suit) {
        return playerList.get(playerIndex)
                .hasSuit(suit);
    }

    @Override
    public PlayingCard playerPlayCardByIndex(int playerIndex, int cardIndex) {
        return playerList.get(playerIndex)
                .playCard(cardIndex);
    }

    public PlayingCard playerPlayCard(int playerIndex, PlayingCard card){

        List<PlayingCard> availableCards = playerList.get(playerIndex).getHand();

        for(int i = 0; i < availableCards.size(); i++){
            if(availableCards.get(i).equals(card)){
                return playerList.get(playerIndex).playCard(i);
            }
        }


        System.out.print("Player " + (playerIndex + 1) + " attempted to play ");
        System.out.println(card.nameShort() + " with hand:");
        printPlayerHand(playerIndex);

        throw new IllegalArgumentException("playerPlayCard(): Illegal argument--" +
                "PlayingCard argument must be a card in player's hand.");

    }

    @Override
    public void wrapUpHand() {
        for(Player player : playerList){
            player.clearHand();
        }
    }

    // == private methods ==
    private void printPlayerHand(int playerIndex){
        for(PlayingCard card : getPlayerHandByIndex(playerIndex)){
            System.out.print(card.nameShort() + "\t");
        }
        System.out.println();
    }

}
