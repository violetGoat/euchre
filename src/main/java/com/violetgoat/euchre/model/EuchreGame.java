package com.violetgoat.euchre.model;

public class EuchreGame {

    private Player[] players = new Player[4];
    private int[] scores = new int[2];
    private Hand currentHand;
    private int currentPlayerIndex;
    private int dealerIndex;

    public EuchreGame(){

    }

    public int getCurrentPlayerIndex(){
        return currentPlayerIndex;
    }

    public int getDealerIndex(){
        return dealerIndex;
    }

    public void advanceCurrentPlayer(){
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

}
