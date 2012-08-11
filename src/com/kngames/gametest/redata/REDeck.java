package com.kngames.gametest.redata;

import com.kngames.gametest.cards.Card;
import com.kngames.gametest.cards.Deck;
import com.kngames.gametest.redata.CardTypes.RECard;

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
}
