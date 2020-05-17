package com.example.memorymatching;


import java.util.ArrayList;
import com.example.memorymatching.exception.CardsException;


public class GameRules {

    private int num_cards_to_match = 2;
    private int num_cards_to_match_to_win = 10;
    private int num_players  = 2;

    public GameRules(int num_cards_to_match, int num_players, int num_cards_to_match_to_win) {
        this.num_cards_to_match = num_cards_to_match;
        this.num_players = num_players;
        this.num_cards_to_match_to_win = num_cards_to_match_to_win;
    }

    /**
     * Checks to see if all cards in the list cards are equal
     * @param cards: List of Card objects
     * @return: True if all cards are equal false otherwise
     * @throws CardsException: throw if size of cards is less than num_cards_to_match
     */
    public boolean cardsEqual(ArrayList<Card> cards) throws CardsException {
        boolean equal = true;
        if (cards.size() < num_cards_to_match) {
            throw new CardsException("Number of cards chosen is less than the number of cards needed to be chosen");
        }
        for(int i = 0; i < cards.size()-1; i++) {
            if (!cards.get(i).equals(cards.get(i+1))) {
                equal = false;
                break;
            }
        }
        return equal;
    }
}
