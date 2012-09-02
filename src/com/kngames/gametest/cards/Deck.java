package com.kngames.gametest.cards;

import java.util.ArrayList;
import java.util.Random;

public abstract class Deck {
	
	/*	This structure assumes that index 0 corresponds to the top of the face-up deck.
	 *  If the deck is face-down, the top of the deck is at index size() - 1
	 *  The "front" of the deck is always index 0, regardless of face-up/face-down
	 */
	
	protected ArrayList<Card> cards;		//	structure to track all of the Card objects in this Deck
	protected boolean faceUp;				//	variable that designates whether or not the deck is face up or face down
	protected Random gen;					//	Random object used in deck shuffling and random selection of cards
	protected ArrayList<String> index;		//	structure to act as a list of what kinds of cards are in this Deck (not in any particular order)
	
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
		index = new ArrayList<String>();
		
		if (tags.length > 0 && !tags[0].equals("")) {
			for (int i = 0; i < tags.length; i++) {
				Card temp = parseTag(tags[i]);
				cards.add(temp);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Deck(Deck other) {
		cards = (ArrayList<Card>) other.cards.clone();
		faceUp = other.faceUp;
		gen = new Random();
		index = (ArrayList<String>) other.index.clone();
	}
	
	//	checks to see if the contents of two Deck objects are equal
	public boolean equals(Object other) {
		Deck otherDeck;
		try { otherDeck = (Deck)other; }
		catch (Exception e) { return false; }
		
		if (this.size() != otherDeck.size()) return false;
		return this.cards.equals(otherDeck.cards);
	}
	
	//	gets the number of Cards currently stored in this Deck
	public int size() { return cards.size(); }
	
	//	gets the current face-up/face-down state of this deck
	public boolean isFaceUp() { return faceUp; }
	
	//	explicitly sets whether the deck is face up or face down
	public void setFaceUp(boolean faceUp) { this.faceUp = faceUp; }
	
	//	flips the deck face-up if it's face-down, or face-down if it's face-up
	public void flipDeck() { faceUp = !faceUp; }
	
	//	searches the index for a specified tag, and returns whether or not the key exists
	public boolean indexContains(String tag) {
		for (String s : index)
			if (s.equals(tag)) return true;
		return false;
	}
	
	//	searches this deck for a card with the specified tag, and returns the first index
	//	that a card with that tag occurs, or -1 if no such card exists
	public int indexOf(String tag) {
		if (indexContains(tag)) {
			for (int i = 0; i < cards.size(); i++) {
				if (cards.get(i).tag.equals(tag)) return i;
			}
		}
		return -1;
	}
	
	//	adds a Card to the front of this Deck (index 0)
	public void addFront(Card toAdd) {
		cards.add(0, toAdd);
		if (!indexContains(toAdd.tag)) index.add(toAdd.tag);
	}
	
	//	adds a Card to the back of this Deck (index size() - 1)
	public void addBack(Card toAdd) {
		cards.add(cards.size(), toAdd);
		if (!indexContains(toAdd.tag)) index.add(toAdd.tag);
	}
	
	//	adds a Card to the top of this Deck, depends on face-up/face-down
	public void addTop(Card toAdd) {
		if (faceUp) { cards.add(0, toAdd); }
		else { cards.add(cards.size(), toAdd); }
		if (!indexContains(toAdd.tag)) index.add(toAdd.tag);
	}
	
	//	adds a Card to the bottom of this Deck, depends on face-up/face-down
	public void addBottom(Card toAdd) {
		if (faceUp) { cards.add(cards.size(), toAdd); }
		else { cards.add(0, toAdd); }
		if (!indexContains(toAdd.tag)) index.add(toAdd.tag);
	}
	
	//	removes and returns the first card in this Deck object (from index 0)
	public Card popFirst() {
		if (cards.size() == 0) return null;
		Card temp = cards.get(0);
		cards.remove(0);
		return temp;
	}
	
	//	removes and returns the last card in this Deck object (from index size() - 1)
	public Card popLast() {
		if (cards.size() == 0) return null;
		Card temp = cards.get(cards.size() - 1);
		cards.remove(cards.size() - 1);
		return temp;
	}
	
	//	removes and returns the top card in this Deck object, depends on face-up/face-down
	public Card popTop() {
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
	public Card popBottom() {
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
	public Card pop(int index) throws IndexOutOfBoundsException {
		Card temp = cards.get(index);
		cards.remove(index);
		return temp;
	}
	
	//	returns the first card in this Deck object (from index 0)
	public Card peekFirst() {
		if (cards.size() == 0) return null;
		return cards.get(0);
	}
	
	//	returns the last card in this Deck object (from index size() - 1)
	public Card peekLast() {
		if (cards.size() == 0) return null;
		return cards.get(cards.size() - 1);
	}
	
	//	returns the top card in this Deck object, depends on face-up/face-down
	public Card peekTop() {
		if (cards.size() == 0) return null;
		if (faceUp) return cards.get(0);
		else return cards.get(cards.size() - 1);
	}
	
	//	returns the bottom card in this Deck object, depends on face-up/face-down
	public Card peekBottom() {
		if (cards.size() == 0) return null;
		if (faceUp) return cards.get(cards.size() - 1);
		else return cards.get(0);
	}
	
	//	returns the card at the specified index in this Deck object
	public Card peek(int index) throws IndexOutOfBoundsException {
		return cards.get(index);
	}
	
	//	shuffles this deck by arranging the cards in a random order
	public void shuffle(int repetitions) {
		for (int i = 0; i < repetitions; i++) {
			ArrayList<Card> shuffled = new ArrayList<Card>();
			while (cards.size() > 0) {
				int pos = gen.nextInt(shuffled.size() + 1);
				shuffled.add(pos, popFirst());
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
