package com.kngames.gametest.redata.Info.InfoFrags;

import com.kngames.gametest.cards.structures.BaseInfoFragment;
import com.kngames.gametest.redata.CardTypes.RECard.CardType;
import com.kngames.gametest.redata.CardTypes.Mansion.*;
import com.kngames.gametest.redata.carddata.CardData;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MansionInfoFragment extends BaseInfoFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	gets the ID of the weapon to be displayed, then fetches the weapon info
		Intent intent = getActivity().getIntent();
		int cardID = intent.getIntExtra("cardID", 0);
		MansionCard card = (MansionCard)CardData.findCard(cardID, CardType.Mansion, -1);
		
		//	set the Strings necessary for the BaseInfoActivity to display the information correctly
		titleText = String.format("%s", card.getName());
		
		infoText = generateMansionInfo(card);
		
		footerText = String.format("CARD ID:  %s", card.getIDString());
		if (card.getExpansion() == 3) footerText += String.format("\nPRINTED CARD ID:  %s-%03d", card.getIDPrefix(), card.getID() - 4);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public static String generateMansionInfo(MansionCard card) {
		if (card.getCardType() == CardType.Infected) {
			InfectedCard infected = (InfectedCard)card;
			String out = String.format(
					"Card Type:  Infected\nExpansion Set:  %s\nQuantity in Mansion:  %d\n" +
					"Health:  %d\nDamage:  %d\nDecorations:  %d",
					CardData.expansString(infected.getExpansion()), infected.getDeckQuantity(), 
					infected.getHealth(), infected.getDamage(), infected.getDecorations());
			if (!infected.getText().equals("")) out += "\n\n"+infected.getText();
			return out;
		} else if (card.getCardType() == CardType.Token) {
			TokenCard token = (TokenCard)card;
			return String.format(
					"Card Type:  Token\nExpansion Set:  %s\nQuantity in Mansion:  %s\n\n%s",
					CardData.expansString(token.getExpansion()), token.getDeckQuantity(), token.getText());
		} else if (card.getCardType() == CardType.Event) {
			EventCard event = (EventCard)card;
			return String.format(
					"Card Type:  Event\nExpansion Set:  %s\nQuantity in Mansion:  %s\n\n%s",
					CardData.expansString(event.getExpansion()), event.getDeckQuantity(), event.getText());
		}
		else return "";
	}
}
