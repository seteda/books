/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package defaultmethods;
 
import java.util.*;
import java.util.stream.*;
import java.lang.*;

public class StandardDeck implements Deck {
    
    private List<Card> entireDeck;

    public StandardDeck(List<Card> existingList) {
        this.entireDeck = existingList;
    }
    
    public StandardDeck() {
        this.entireDeck = new ArrayList<>();
        for (Card.Suit s : Card.Suit.values()) {
            for (Card.Rank r : Card.Rank.values()) {
               this.entireDeck.add(new PlayingCard(r, s));
            }
        }
    }
    
    public Deck deckFactory() {
        return new StandardDeck(new ArrayList<Card>());
    }
    
    public int size() {
        return entireDeck.size();
    }
    
    public List<Card> getCards() {
        return entireDeck;
    }
    
    public void addCard(Card card) {
        entireDeck.add(card);
    }

    public void addCards(List<Card> cards) {
        entireDeck.addAll(cards);
    }

    
    public void addDeck(Deck deck) {
        List<Card> listToAdd = deck.getCards();
        entireDeck.addAll(listToAdd);
    }

    public void sort() {
        Collections.sort(entireDeck);
    }
    
    public void sort(Comparator<Card> c) {
        Collections.sort(entireDeck, c);
    }    

    public void shuffle() {
        Collections.shuffle(entireDeck);
    }
    
    public Map<Integer, Deck> deal(int players, int numberOfCards)
        throws IllegalArgumentException
    {
        int cardsDealt = players * numberOfCards;
        int sizeOfDeck = entireDeck.size();
        if (cardsDealt > sizeOfDeck) {
            throw new IllegalArgumentException(
                "Number of players (" + players +
                ") times number of cards to be dealt (" + numberOfCards +
                ") is greater than the number of cards in the deck (" +
                sizeOfDeck + ").");
        }
        
        Map<Integer, List<Card>> dealtDeck = entireDeck
           .stream()
           .collect(
               Collectors.groupingBy(
                   card -> {
                       int cardIndex = entireDeck.indexOf(card);
                       if (cardIndex >= cardsDealt) return (players + 1);
                       else return (cardIndex % players) + 1;
                   }));
        
        // Convert Map<Integer, List<Card>> to Map<Integer, Deck>
        Map<Integer, Deck> mapToReturn = new HashMap<>();
           
        for (int i = 1; i <= (players + 1); i++) {
            Deck currentDeck = deckFactory();
            currentDeck.addCards(dealtDeck.get(i));
            mapToReturn.put(i, currentDeck);
        }
        return mapToReturn;
    }
    
    public String deckToString() {
        return this.entireDeck
            .stream()
            .map(Card::toString)
            .collect(Collectors.joining("\n"));
    }
    
    public static void main(String... args) {
        StandardDeck myDeck = new StandardDeck();
        System.out.println("Creating deck:");
        myDeck.sort();
        System.out.println("Sorted deck");
        System.out.println(myDeck.deckToString());
        myDeck.shuffle();
        myDeck.sort(new SortByRankThenSuit());
        System.out.println("Sorted by rank, then by suit");
        System.out.println(myDeck.deckToString());        
        myDeck.shuffle();
        myDeck.sort(
            Comparator.comparing(Card::getRank)
                .thenComparing(Comparator.comparing(Card::getSuit)));
        System.out.println("Sorted by rank, then by suit " +
            "with static and default methods");
        System.out.println(myDeck.deckToString());        
        myDeck.sort(
            Comparator.comparing(Card::getRank)
                .reversed()
                .thenComparing(Comparator.comparing(Card::getSuit)));
        System.out.println("Sorted by rank reversed, then by suit " +
            "with static and default methods");
        System.out.println(myDeck.deckToString());
    }
}

