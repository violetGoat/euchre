package com.violetgoat.euchre.model;

public class Team {

    // == fields ==
    private int teamIndex;
    private int score;
    private int numTricksWon;
    private int[] teamMemberPlayerIndices;

    // == constructors ==
    public Team(int teamIndex){
        this.teamIndex = teamIndex;
        this.score = 0;
        this.numTricksWon = 0;
        this.teamMemberPlayerIndices = new int[]{teamIndex, teamIndex + 2};
    }

    // == public methods ==

    public int getTeamIndex() {
        return teamIndex;
    }

    public int getScore() {
        return score;
    }

    public int getNumTricksWon(){
        return numTricksWon;
    }

    public void incrementNumTricksWon(){
        numTricksWon++;
    }

    public void resetNumTricksWon(){
        numTricksWon = 0;
    }

    public int[] getTeamMemberPlayerIndices(){
        return teamMemberPlayerIndices;
    }

    public void increaseScoreBy(int addend){
        this.score += addend;
    }

}
