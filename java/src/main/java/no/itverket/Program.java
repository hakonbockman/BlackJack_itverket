package no.itverket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Program {
    static Deck deck = new Deck();
    final static List<Card> playersHand = new ArrayList<>();
    final static List<Card> dealersHand = new ArrayList<>();

    final static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static int playersTotal = 0;
    static int dealersTotal = 0;

    public static void main(String[] args) {
        playBlackJack();
    }

    private static void playBlackJack() {
        while (true) {
            System.out.println("Play again?");
            final String read = scanner.nextLine();
            if (read.contains("yes") || read.equals("Yes")) {

                resetTheGame();

                System.out.println("Dealer starts the game");
                dealerPlaysOneCard();

                System.out.println("Players starts to play their turn");
                playerPlaysTheirTurn();

                System.out.println("Dealer starts to play their turn");
                dealerPlaysTheirTurnAutomatic();

                checkPoints(dealersTotal, playersTotal);
            } else if (read.equals("no") || read.equals("No")) {
                System.out.println("Thank you for playing.");
                break;
            }
        }
    }

    private static void resetTheGame(){
        playersTotal = 0;
        dealersTotal = 0;
        playersHand.clear();
        dealersHand.clear();
        deck = new Deck();
    }
    private static void checkPoints(int dealersTotal, int playersTotal) {
        if ((dealersTotal == playersTotal) || (playersTotal > 21 && dealersTotal > 21)) {
            System.out.println("It is a draw!!");
        }
        if (dealersTotal > playersTotal || playersTotal > 21) {
            if (dealersTotal <= 21) {
                System.out.println("Dealer wins!!");
            }
        }
        if (playersTotal > dealersTotal || dealersTotal > 21) {
            if (playersTotal <= 21) {
                System.out.println("Player wins!!");
            }
        }
    }

    private static void dealerPlaysOneCard() {
        System.out.println("dealer" + " plays one card");
        final Card cardFromDeck = drawRandomCard(deck);
        Program.dealersHand.add(cardFromDeck);
        dealersTotal = countPoints(Program.dealersHand);
        dealersTotal += showCard(cardFromDeck, "dealer");

        checkIfYouLost(dealersTotal, "dealer");
    }

    private static void playerPlaysTheirTurn() {
        while (true) {
            System.out.println("Stand, Hit");
            final String read = scanner.nextLine();

            if (read.equals("Hit")) {
                final Card cardFromDeck = drawRandomCard(deck);
                Program.playersHand.add(cardFromDeck);
                playersTotal = countPoints(Program.playersHand);
                playersTotal += showCard(cardFromDeck, "player");

                if (checkIfYouLost(playersTotal, "player")) {
                    System.out.println("Game is ended for " + "player");
                    break;
                }

            } else if (read.equals("Stand")) {
                System.out.println("Game is ended for " + "player");
                break;
            }
        }
    }

    private static void dealerPlaysTheirTurnAutomatic() {
        while (true) {
            checkIfYouLost(dealersTotal, "dealer");
            final Card cardFromDeck = drawRandomCard(deck);
            Program.dealersHand.add(cardFromDeck);
            dealersTotal = countPoints(Program.dealersHand);
            dealersTotal += showCard(cardFromDeck, "dealer");

            if (checkIfYouLost(dealersTotal, "dealer")) {
                System.out.println("Game is ended for " + "dealer");
                break;
            }
        }
    }

    private static int countPoints(List<Card> hand) {
        return hand.stream()
                .map(cardInHand ->
                        Math.min(cardInHand.rank, 14))
                .reduce(0, Integer::sum);
    }

    private static Card drawRandomCard(Deck deck) {
        int sizeOfDeck = deck.cards.size();

        if (sizeOfDeck < 1) {
            deck = new Deck();
        }
        return deck.cards.remove(random.nextInt(sizeOfDeck));
    }

    private static int checkIfAce(Card cardFromDeck) {
        if (cardFromDeck.rank == 14) {
            System.out.println("Do you wish to use A as 1 or 11 ?");

            if (scanner.nextLine().equals("1")) {
                cardFromDeck.rank = 1;
            } else {
                cardFromDeck.rank = 11;
            }
            return cardFromDeck.rank;
        }
        return 0;
    }

    private static int showCard(Card cardFromDeck, String name) {
        if (cardFromDeck.rank >= 11) {
            System.out.printf(name + " Hit with %s %s. ", cardFromDeck.suit, cardFromDeck.pictureCard);
        } else {
            System.out.printf(name + " Hit with %s %s. ", cardFromDeck.suit, cardFromDeck.rank);
        }
        return checkIfAce(cardFromDeck);
    }

    private static boolean checkIfYouLost(int total, String name) {
        if (name.equals("dealer") && total >= 17 && total <= 21) {
            System.out.printf(name + " is satisfied, " + name + "s total is %s%n", total);
            return true;
        }
        if (total == 21) {
            System.out.printf(name + "s total is %s%n", total);
            return true;
        } else if (total > 21) {
            System.out.printf(name + " lost, " + name + "s total is %s%n", total);
            return true;
        } else {
            System.out.printf(name + "s total is %s %n", total);
            return false;
        }
    }



}
