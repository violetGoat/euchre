package com.violetgoat.euchre.console;

import com.violetgoat.euchre.model.Bid;
import com.violetgoat.euchre.model.Hand;
import com.violetgoat.euchre.model.Play;
import com.violetgoat.euchre.model.Trick;
import com.violetgoat.euchre.services.*;

public class ConsoleMain {

    public static void main(String[] args) {
        PlayerService playerService = new PlayerServiceImpl();
        Dealer dealer = new DealerImpl(playerService);
        CardToASCIIConverter cardToASCIIConverter = new CardToASCIIConverterImpl();
        MessageService messageService = new MessageServiceImpl(cardToASCIIConverter);
        Hand hand = new Hand();
        HandAccessService handAccessService = new HandAccessServiceImpl(hand);
        TrickAccessService trickAccessService = new TrickAccessServiceImpl();
        CardRankEvaluator cardRankEvaluator = new CardRankEvaluatorImpl(handAccessService, trickAccessService, playerService);
        TrickService trickService = new TrickServiceImpl(cardRankEvaluator, trickAccessService);


        HandProcessService handProcessService = new HandProcessServiceImpl(playerService, dealer, trickService, hand);
        ConsoleView consoleView = new ConsoleViewImpl(playerService, handAccessService, trickAccessService, messageService, cardRankEvaluator);
        PlayerCardSelector playerCardSelector = new ConsolePlayerCardSelectorImpl(consoleView);
        BiddingService biddingService = new BiddingServiceImpl(playerService, playerCardSelector);
        ComputerCardSelector computerCardSelector = new ComputerCardSelectorImpl(cardRankEvaluator, handAccessService, playerService, trickAccessService);
        CardSelectionService cardSelectionService = new CardSelectionServiceImpl(playerCardSelector, computerCardSelector);
        GameService gameService = new ConsoleGameServiceImpl(handProcessService, biddingService, cardSelectionService, playerService, handAccessService, consoleView);

        int dealerIndex = gameService.chooseDealer();

        System.out.println(cardToASCIIConverter.getTextArtStringFromCard(dealer.revealTopCard()));


        while (!gameService.gameIsComplete()) {

            gameService.initializeHand(dealerIndex);
            gameService.dealHand();
            gameService.initializeBid(dealerIndex, gameService.revealTopCard());

            Bid bid = null;

            while (bid == null || bid.isPass()) {
                if (gameService.getCurrentPlayerIndex() == 0) {
                    bid = gameService.getNextPlayerBid();
                } else {
                    bid = gameService.getNextComputerBid();
                }
                gameService.advancePlayer();
            }

            gameService.implementBid(bid);

            while (!gameService.handIsComplete()) {

                gameService.initializeTrick();

                while (!gameService.trickIsComplete()) {

                    Play play = null;

                    if (gameService.getCurrentPlayerIndex() == 0) {
                        play = gameService.getNextPlayerPlay();
                        gameService.advancePlayer();
                    } else {
                        play = gameService.getNextComputerPlay();
                        gameService.advancePlayer();
                    }

                    gameService.processPlayedCard(play);

                }

                gameService.wrapUpTrick();

            }

            gameService.wrapUpHand();
            dealerIndex = (dealerIndex + 1) % 4;
        }

    }
}
