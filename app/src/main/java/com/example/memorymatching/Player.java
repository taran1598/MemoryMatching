package com.example.memorymatching;

import java.util.ArrayList;

public class Player {

    String name = null;
    int id = 0;
    Integer points = 0;
    ArrayList<Card> cards_guessed_correctly;

    public Player(String name) {
        id = (int) (Math.random() * 1000);
        this.name = name;
        cards_guessed_correctly = new ArrayList<>();
    }

    /**
     * Adds points to player score
     * @param points: score of the player
     */
    public void addPoint(int points) {
        this.points += points;
    }

    public Integer getPoints() {
        return this.points;
    }

    /**
     * Adds all the cards the user matched correctly to their own collection
     * @param cards: cards the user matched correctly
     */
    public void addCorrectGuessedCard(ArrayList<Card> cards) {
        cards_guessed_correctly.addAll(cards);
    }

    public ArrayList<Card> getCards_guessed_correctly() {
        return this.cards_guessed_correctly;
    }



}
