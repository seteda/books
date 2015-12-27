/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
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

import java.io.Serializable;

public class Card3 implements Serializable {
    private int suit = UNASSIGNED;
    private int number = UNASSIGNED;

    public final static int UNASSIGNED = -1;

    public final static int DIAMONDS = 1;
    public final static int CLUBS = 2;
    public final static int HEARTS = 3;
    public final static int SPADES = 4;

    public final static int ACE = 1;
    public final static int KING = 13;

    public Card3(int number, int suit) {
        if (isValidNumber(number)) {
            this.number = number;
        } else {
            //Error
        }

        if (isValidSuit(suit)) {
            this.suit = suit;
        } else {
            //Error
        }
    }

    public int getSuit() {
        return suit;
    }

    public int getNumber() {
        return number;
    }

    public static boolean isValidNumber(int number) {
        if (number >= ACE && number <= KING) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isValidSuit(int suit) {
        if (suit >= DIAMONDS && suit <= SPADES) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof Card3) {
	    Card3 card = (Card3)obj;
            if (card.getNumber() == this.number && card.getSuit() == this.suit) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public int hashCode() {
        return number * suit;
    }
    public String toString() {
        return numberToString(this.number) + " of "
               + suitToString(this.suit);
    }

    public static String numberToString(int number) {
        String result = "";
        switch (number) {
            case ACE: result =  "Ace"; break;
            case 2: result = "Two"; break;
            case 3: result = "Three"; break;
            case 4: result = "Four"; break;
            case 5: result = "Five"; break;
            case 6: result = "Six"; break;
            case 7: result = "Seven"; break;
            case 8: result = "Eight"; break;
            case 9: result = "Nine"; break;
            case 10: result = "Ten"; break;
            case 11: result = "Jack"; break;
            case 12: result = "Queen"; break;
            case KING: result = "King"; break;
            case UNASSIGNED: result = "Invalid Number"; break;
        }
        return result;
    }

    public static String suitToString(int suit) {
        String result = "";
        switch (suit) {
            case DIAMONDS: result = "Diamonds"; break;
            case CLUBS: result = "Clubs"; break;
            case HEARTS: result = "Hearts"; break;
            case SPADES: result = "Spades"; break;
            case UNASSIGNED: result = "Invalid Suit"; break;
        }
        return result;
    }
}
