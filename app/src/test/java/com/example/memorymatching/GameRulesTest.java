package com.example.memorymatching;

import com.example.memorymatching.exception.CardsException;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameRulesTest {

    private static GameRules gameRules;
    private static int num_cards_to_match_to_win = 10;

    @BeforeClass
    public static void setup() {
        gameRules = new GameRules(2, 2, num_cards_to_match_to_win);
    }

    @Test
    public void cardsEqualTestCorrect() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(createFakeCards(2).get(0));
        cards.add(createFakeCards(2).get(0));
        try {
            assertEquals(true, gameRules.cardsEqual(cards));
        } catch (CardsException e) {
            e.printStackTrace();
            fail("CardsException thrown");
        }
    }

    @Test
    public void cardsEqualTestNotCorrect() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(createFakeCards(2).get(0));
        cards.add(createFakeCards(2).get(1));
        try {
            assertNotEquals(true, gameRules.cardsEqual(cards));
        } catch (CardsException e) {
            e.printStackTrace();
            fail("CardsException thrown");
        }
    }

    @Test
    public void cardsEqualTestNOtCorrectNumberCards() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(createFakeCards(2).get(0));
        try {
           gameRules.cardsEqual(cards);
           fail("CardsException should have been thrown");
        } catch (CardsException e) {
            System.out.println("CardsException thrown");
        }
    }

    private ArrayList<Card> createFakeCards(int num_cards) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < num_cards; i++) {
            cards.add(new Card("Card" + i));
        }
        return cards;
    }
}
