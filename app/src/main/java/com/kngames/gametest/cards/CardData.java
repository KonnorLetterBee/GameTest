package com.kngames.gametest.cards;

import java.util.HashMap;

import android.util.Log;
import android.util.SparseArray;

public class CardData {
	public static final String TAG = CardData.class.getSimpleName();
	private static CardData cardData = null;
	
	//	the primary storage method for cards is a HashMap of SparseArrays
	//	outer HashMap corresponds to the card's string portion of the key
	//	inner SparseArrays correspond to the card's int portion of the key
	private HashMap<String, SparseArray<Card>> cards;
	
	//	constructs a new CardData
	//	since CardData is a singleton, instantiate the CardData using initCardData
	private CardData() {
		cards = new HashMap<String, SparseArray<Card>>();
		Log.d(TAG, "CardData instantiated");
	}
	
	//	instantiates a new CardData if one doesn't exist
	//	otherwise, returns the instance that already exists
	public static CardData initCardData() {
		if (cardData == null)  cardData = new CardData();
		else  Log.e(TAG, "CardData already instantiated!");
		return cardData;
	}
	
	//	returns the instance of the CardData that already exists (even if there is none)
	public static CardData getCardData() {
		return cardData;
	}
	
	//	returns true if there is a CardData object, false otherwise
	public static boolean exists() {
		return cardData != null;
	}
	
	//	sets the stored CardData to null
	public static void destroy() {
		cardData = null;
	}
	
	//	adds a card to this CardData's collection
	//	if the card's string tag isn't present in the collection, it will be created
	//	returns true if the insertion was successful (meaning no card was already in the same position)
	//	returns false otherwise (on a false return, CardData will not be changed)
	public boolean addCard(Card c) {
		//	get the proper category based on the card's catTag, create it if it doesn't exist
		SparseArray<Card> category = cards.get(c.getCatTag());
		if (category == null) {
			category = new SparseArray<Card>();
			cards.put(c.getCatTag(), category);
		}
		
		if (category.get(c.getIntTag()) != null) {
			Log.e(TAG, "Tag "+c.getCatTag()+c.getIntTag()+" already exists!");
			return false;
		} else {
			category.put(c.getIntTag(), c);
			return true;
		}
	}
	
	/**
	 * Retrieves the card from the specified category and the specified position from CardData.
	 * @param cat the category to retrieve from
	 * @param pos the position in the specified category to get
	 * @return the card with the tag requested, or null if none could be found
	 */
	public Card getCard(String cat, int pos) {
		SparseArray<Card> category = cards.get(cat);
		if (category == null) {
			Log.e(TAG, "Category "+cat+" ("+cat+pos+") doesn't exist!");
			return null;
		}
		
		Card c = category.get(pos);
		if (c == null) {
			Log.e(TAG, "Position "+pos+" ("+cat+pos+") doesn't exist!");
		}
		return c;
	}
	
	/**
	 * Retrieves the card with the specified tag from CardData.  The tag is split along a semicolon which divides
	 * the category from the position within that category.
	 * @param concatTag that tag in form [cat];[pos] to search for
	 * @return the card with the tag requested, or null if none could be found
	 */
	public Card getCard(String concatTag) {
		String[] parts = concatTag.split(";");
		return getCard(parts[0], Integer.parseInt(parts[1]));
	}
	
	/**
	 * Searches CardData for a series of tags and creates an array of Card objects that correspond to those tags.
	 * @param concatTags the list of concatenated card tags to search the lists for
	 * @return the array filled with the card objects that correspond to the tags supplied
	 */
	public Card[] getCardList(String[] concatTags) {
		Card[] out = new Card[concatTags.length];
		
		for(int i = 0; i < out.length; i++) {
			out[i] = getCard(concatTags[i]);
		}
		
		return out;
	}
	
	//	removes a card from the collection
	//	if the category the card was in is empty after removal, also remove the category
	public void removeCard(String cat, int pos) {
		SparseArray<Card> category = cards.get(cat);
		if (category == null) {
			Log.e(TAG, "Category "+cat+" ("+cat+pos+") doesn't exist!");
			return;
		}
		
		category.remove(pos);
		if (category.size() == 0) {
			removeCategory(cat);
		}
	}
	
	//	gets the contents of a specified category and returns the cards in that category
	//	in an array
	public Card[] getCategory(String cat) {
		SparseArray<Card> category = cards.get(cat);
		if (category == null) {
			Log.e(TAG, "Category "+cat+" doesn't exist!");
			return null;
		}
		
		Card[] out = new Card[category.size()];
		for (int i = 0; i < category.size(); i++) {
			out[i] = category.valueAt(i);
		}
		return out;
	}
	
	//	removes an entire category from this collection if it exists
	public void removeCategory(String cat) {
		cards.remove(cat);
	}
}
