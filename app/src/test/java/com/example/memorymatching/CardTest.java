package com.example.memorymatching;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
public class CardTest {
    @Test
    public void testCardEquals() {
        Card card1 = new Card("card1");
        Card card2 = new Card("card2");
        Card cardEquals1 = new Card("card1");
        assertEquals(card1, cardEquals1);
        assertNotEquals(card1, card2);
    }

}
