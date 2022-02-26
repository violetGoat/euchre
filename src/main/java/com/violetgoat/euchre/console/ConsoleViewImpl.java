package com.violetgoat.euchre.console;

import com.violetgoat.euchre.model.*;
import com.violetgoat.euchre.services.CardRankEvaluator;
import com.violetgoat.euchre.services.HandAccessService;
import com.violetgoat.euchre.services.PlayerService;
import com.violetgoat.euchre.services.TrickAccessService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleViewImpl implements ConsoleView {

    // == constants ==
    Scanner scanner = new Scanner(System.in);

    // == fields ==
    private PlayerService playerService;
    private TrickAccessService trickAccessService;
    private HandAccessService handAccessService;
    private MessageService messageService;
    private CardRankEvaluator cardRankEvaluator;
    private int humanPlayerIndex = 0;

    // == constructor ==

    public ConsoleViewImpl(PlayerService playerService, HandAccessService handAccessService, TrickAccessService trickAccessService, MessageService messageService, CardRankEvaluator cardRankEvaluator){
        this.playerService = playerService;
        this.handAccessService = handAccessService;
        this.trickAccessService = trickAccessService;
        this.messageService = messageService;
        this.cardRankEvaluator = cardRankEvaluator;
    }

    // public (interface) methods

    @Override
    public void printHand() {
        printHand(humanPlayerIndex);
    }

    @Override
    public void printHand(int playerIndex) {
        List<PlayingCard> playerHand = playerService.getPlayerHandByIndex(playerIndex);
        messageService.printCardList(playerHand);
    }

    public void printDealerMessage(int dealerIndex){
        System.out.println();
        System.out.println("Player " + (dealerIndex + 1) + " is dealer.");
    }

    @Override
    public void printScores() {
        List<Integer> scores = playerService.getScores();
        String teamOneScore= "Team 1 (Your team) score:  " + scores.get(0);
        String teamTwoScore = "Team 2 score: " + scores.get(1);
        messageService.printMessage(teamOneScore);
        messageService.printMessage(teamTwoScore);
        messageService.printMessage("");
    }

    @Override
    public void printPlayedCards() {

        List<PlayingCard> playedCards = trickAccessService.getPlayedCards();
        messageService.printPlayedCards(playedCards);

    }

    @Override
    public void printTopCard() {
        System.out.println();
        System.out.println("Top card of deck revealed: ");
        messageService.printCard(handAccessService.getRevealedCard());
        System.out.println();
    }

    @Override
    public void printBid(Bid bid) {
        messageService.printBid(bid);
    }

    @Override
    public PlayingCard playerDiscardCard() {
        System.out.println("Please choose a card to discard.");
        printHand();
        int cardIndex = getCardIndexChoice();
        return playerService.playerPlayCardByIndex(humanPlayerIndex, cardIndex);
    }

    @Override
    public PlayingCard playerPlayCard(){
        printHand();
        boolean validPlay = false;
        int cardIndex = -1;

        while(!validPlay){
            cardIndex = getCardIndexChoice();
            PlayingCard card = playerService.getPlayerHandByIndex(humanPlayerIndex).get(cardIndex);
            validPlay = cardRankEvaluator.isValidPlay(Play.of(humanPlayerIndex, card));
        }
        return playerService.playerPlayCardByIndex(humanPlayerIndex, cardIndex);
    }

    @Override
    public Bid getPlayerSecondRoundBid() {

        List<Suit> availableSuits = getAvailableSuits();

        System.out.println("Please select a suit to be trump. ");
        messageService.printSuitList(availableSuits);

        // non-dealer players may pass, but dealer must select trump suit
        if(!playerIsDealer()){
            System.out.println("or enter '4' to pass");
        }

        int choice = getSuitIndexChoice();

        // choice of 4 (0-index 3) = pass
        if(choice == 3){
            return new BidImpl()
                    .setBiddingPlayerIndex(humanPlayerIndex)
                    .setPass(true);
        }

        Suit selectedSuit = availableSuits.get(choice);

        return new BidImpl()
                .setBiddingPlayerIndex(humanPlayerIndex)
                .setSuit(selectedSuit)
                .setPass(false)
                .setOrderingUp(false);
    }

    @Override
    public Bid getPlayerFirstRoundBid() {

        System.out.println("Would you like the suit of the revealed card to be trump?");
        int choice = getOrderUpIndexChoice();

        Bid bid = choice == 0 ?

            new BidImpl()
                    .setBiddingPlayerIndex(humanPlayerIndex)
                    .setSuit(revealedCardSuit())
                    .setPass(false) :

            new BidImpl()
                .setBiddingPlayerIndex(humanPlayerIndex)
                .setPass(true);

        return bid;
    }

    // == private methods ==

    private boolean playerIsDealer(){
        return humanPlayerIndex == handAccessService.getDealerIndex();
    }

    private List<Suit> getAvailableSuits(){
        List<Suit> availableSuits = new ArrayList<>();

        for(Suit suit: Suit.values()){
            if(suit != handAccessService.getRevealedCard().getSuit()){
                availableSuits.add(suit);
            }
        }

        return availableSuits;
    }

    private int getSuitIndexChoice(){

        int choice = 0;

        // if player is dealer, they may not pass (choice 4)
        int maxChoice = playerIsDealer() ?
                3 : 4;

        do {
            while(!scanner.hasNextInt()){
                System.out.println("Your choice must be an integer.");
                scanner.next();
            }
            choice = scanner.nextInt();
        }
        while (choice < 1 || choice > maxChoice);

        return choice-1;
    }

    private int getOrderUpIndexChoice(){

        int choice = 0;

        do {
            System.out.println("Enter 1 (revealed card will go to dealer, and its suit will become trump) or 2 (pass)");
            while(!scanner.hasNextInt()){
                System.out.println("Your choice must be an integer.");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 2);

        return choice-1;
    }

    public void closeScanner(){
        scanner.close();
    }

    private int getCardIndexChoice(){

        int handSize = playerService.getPlayerHandSize(humanPlayerIndex);

        int choice = 0;

        do {
            System.out.println("Enter a number between 1 and " + handSize + ":");
            while(!scanner.hasNextInt()){
                System.out.println("Your choice must be an integer");
                scanner.next();
            }
            choice = scanner.nextInt();
        }
        while (choice < 1 || choice > handSize);

        return choice-1;
    }

    private Suit revealedCardSuit(){
        return handAccessService.getRevealedCard().getSuit();
    }

}
