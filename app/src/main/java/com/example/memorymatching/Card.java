package com.example.memorymatching;

import java.util.Objects;

public class Card {

    private String cardName = null;

    /**
     * Creates card
     * @param cardName: url to identify the card. (Will always be unique so no need to just take the actual name)
     */
    public Card(String cardName) {
        this.cardName = cardName;
    }

    public String getCardName() {
        return cardName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(getCardName(), card.getCardName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardName());
    }
}
