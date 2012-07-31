package com.kngames.gametest.cards;

import java.util.ArrayList;
import java.util.Random;

public abstract class Deck {
	
	/*	This structure assumes that index 0 corresponds to the top of the face-up deck.
	 *  If the deck is face-down, the top of the deck is at index size() - 1
	 *  The "front" of the deck is always index 0, regardless of face-up/face-down
	 */
	
	private ArrayList<Card> cards;		//	structure to track all of the Card objects in this Deck
	private boolean faceUp;				//	variable that designates whether or not the deck is face up or face down
	private Random gen;					//	Random object used in deck shuffling and random selection of cards
	
	//	constructs a new empty Deck object
	public Deck () {
		this(new String[0]);
	}
	
	//	constructs a new Deck object from a single string of tags
	public Deck (String tags) {
		this(tags.split("\\/"));
	}
	
	//	constructs a new Deck object from an array of tags
	public Deck (String[] tags) {
		cards = new ArrayList<Card>();
		faceUp = true;
		gen = new Random();
		
		if (tags.length > 0 && !tags[0].equals("")) {
			for (int i = 0; i < tags.length; i++) {
				Card temp = parseTag(tags[i]);
				cards.add(temp);
			}
		}
	}
	
	//	gets the number of Cards currently stored in this Deck
	public int getSize() { return cards.size(); }
	
	//	gets the current face-up/face-down state of this deck
	public boolean isFaceUp() { return faceUp; }
	
	//	flips the deck face-up if it's face-down, or face-down if it's face-up
	public void flipDeck() { faceUp = !faceUp; }
	
	//	adds a Card to the front of this Deck (index 0)
	public void addToFront(Card toAdd) { cards.add(0, toAdd); }
	
	//	adds a Card to the back of this Deck (index size() - 1)
	public void addToBack(Card toAdd) { cards.add(cards.size(), toAdd); }
	
	//	adds a Card to the top of this Deck, depends on face-up/face-down
	public void addToTop(Card toAdd) {
		if (faceUp) { cards.add(0, toAdd); }
		else { cards.add(cards.size(), toAdd); }
	}
	
	//	adds a Card to the bottom of this Deck, depends on face-up/face-down
	public void addToBottom(Card toAdd) {
		if (faceUp) { cards.add(cards.size(), toAdd); }
		else { cards.add(0, toAdd); }
	}
	
	//	removes and returns the first card in this Deck object (from index 0)
	public Card popFirstCard() {
		if (cards.size() == 0) return null;
		Card temp = cards.get(0);
		cards.remove(0);
		return temp;
	}
	
	//	removes and returns the last card in this Deck object (from index size() - 1)
	public Card popLastCard() {
		if (cards.size() == 0) return null;
		Card temp = cards.get(cards.size() - 1);
		cards.remove(cards.size() - 1);
		return temp;
	}
	
	//	removes and returns the top card in this Deck object, depends on face-up/face-down
	public Card popTopCard() {
		if (cards.size() == 0) return null;
		Card temp;
		if (faceUp) {
			temp = cards.get(0);
			cards.remove(0);
		} else {
			temp = cards.get(cards.size() - 1);
			cards.remove(cards.size() - 1);
		}
		return temp;
	}
	
	//	removes and returns the bottom card in this Deck object, depends on face-up/face-down
	public Card popBottomCard() {
		if (cards.size() == 0) return null;
		Card temp;
		if (faceUp) {
			temp = cards.get(cards.size() - 1);
			cards.remove(cards.size() - 1);
		} else {
			temp = cards.get(0);
			cards.remove(0);
		}
		return temp;
	}
	
	//	removes and returns the card at the specified index in this Deck object (from index 0)
	public Card popCard(int index) {
		if (cards.size() == 0 || index >= cards.size() || index < 0) return null;
		Card temp = cards.get(index);
		cards.remove(index);
		return temp;
	}
	
	//	returns the first card in this Deck object (from index 0)
	public Card peekFirstCard() {
		if (cards.size() == 0) return null;
		return cards.get(0);
	}
	
	//	returns the last card in this Deck object (from index size() - 1)
	public Card peekLastCard() {
		if (cards.size() == 0) return null;
		return cards.get(cards.size() - 1);
	}
	
	//	returns the top card in this Deck object, depends on face-up/face-down
	public Card peekTopCard() {
		if (cards.size() == 0) return null;
		if (faceUp) return cards.get(0);
		else return cards.get(cards.size() - 1);
	}
	
	//	returns the bottom card in this Deck object, depends on face-up/face-down
	public Card peekBottomCard() {
		if (cards.size() == 0) return null;
		if (faceUp) return cards.get(cards.size() - 1);
		else return cards.get(0);
	}
	
	//	returns the card at the specified index in this Deck object
	public Card peekCard(int index) {
		if (cards.size() == 0 || index >= cards.size() || index < 0) return null;
		return cards.get(index);
	}
	
	//	shuffles this deck by arranging the cards in a random order
	public void shuffle(int repetitions) {
		for (int i = 0; i < repetitions; i++) {
			ArrayList<Card> shuffled = new ArrayList<Card>();
			while (cards.size() > 0) {
				int pos = gen.nextInt(shuffled.size() + 1);
				shuffled.add(pos, popFirstCard());
			}
			cards = shuffled;	//	sets cards to point to the shuffled deck
		}
	}

	//	prints the contents of this Deck to stdout
	public void printDeck() {
		for (Card c : cards) {
			System.out.printf("%s ", c);
		}
		System.out.printf("\n");
	}
	
	///
	///		Tag Methods
	///
	
	//	generates an array of Strings that correspond to the tags of the cards in the deck
	public String[] generateTagArray() {
		String[] out = new String[cards.size()];
		
		for (int i = 0; i < cards.size(); i++) {
			out[i] = generateTag(cards.get(i));
		}
		
		return out;
	}
	
	//	generates a single String containing the tags of the cards in this deck delimited with a '/' to separate cards
	public String generateSingleTagString() {
		String[] tags = generateTagArray();
		StringBuilder out = new StringBuilder();
		for (int i = 0; i < tags.length; i++) {
			if (i > 0) out.append("/");
			out.append(tags[i]);
		}
		return out.toString();
	}
	
	//	searches for a card based on tag information
	public abstract Card parseTag(String tag);
	
	//	finds/generates the tag for a given card
	public abstract String generateTag(Card c);
}