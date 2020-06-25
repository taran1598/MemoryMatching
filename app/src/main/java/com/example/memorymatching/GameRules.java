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
     * Abstraction that allows num points awarded to be changed.
     * @param cards: list of cards that are selected
     * @return: points > 0 if corrent o.w 0
     * @throws CardsException
     */
    public int num_points(ArrayList<Card> cards) throws CardsException {
        if (cardsEqual(cards)) {
            return 1;
        } else {
            return 0;
        }
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

    public void setNum_cards_to_match(int num_cards_to_match) {
        this.num_cards_to_match = num_cards_to_match;
    }

    public void setNum_cards_to_match_to_win(int num_cards_to_match_to_win) {
        this.num_cards_to_match_to_win = num_cards_to_match_to_win;
    }

    public void setNum_players(int num_players) {
        this.num_players = num_players;
    }

    public int getNum_cards_to_match() {
        return num_cards_to_match;
    }

    public int getNum_cards_to_match_to_win() {
        return num_cards_to_match_to_win;
    }

    public int getNum_players() {
        return num_players;
    }
}
