package com.kngames.gametest.redata;

import java.util.ArrayList;

import android.util.Pair;

import com.kngames.gametest.cards.Card;
import com.kngames.gametest.cards.Deck;
import com.kngames.gametest.redata.CardTypes.RECard;
import com.kngames.gametest.redata.CardTypes.RECard.CardType;

public class REDeck extends Deck {

	//	constructs a new empty Deck object
	public REDeck () {
		this(new String[0]);
	}
	
	//	constructs a new Deck object from a single string of tags
	public REDeck (String tags) {
		this(tags.split("\\/"));
	}
	
	//	constructs a new Deck object from an array of tags
	public REDeck (String[] tags) {
		super(tags);
	}
	
	@Override
	public Card parseTag(String tag) {
		return RECard.parseTag(tag);
	}

	@Override
	public String generateTag(Card c) {
		return c.getTag();
	}
	
	//	queries this deck for all cards that match the specified card type
	//	returns an array list of pairs, the first element being the index in the deck the card is found,
	//	the second being a reference to the card itself
	public ArrayList<Pair<Integer,RECard>> queryType(CardType type) {
		ArrayList<Pair<Integer,RECard>> result = new ArrayList<Pair<Integer,RECard>>();
		for (int i = 0; i < size(); i++) {
			RECard temp = (RECard)peek(i);
			if (temp.getCardType() == type)
				result.add(new Pair<Integer, RECard>(i, temp));
		}
		return result;
	}
}
