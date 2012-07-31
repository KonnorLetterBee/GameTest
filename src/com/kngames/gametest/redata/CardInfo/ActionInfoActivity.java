package com.kngames.gametest.redata.CardInfo;

import com.kngames.gametest.redata.BaseInfoActivity;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.CardTypes.ActionCard;

import android.content.Intent;
import android.os.Bundle;

public class ActionInfoActivity extends BaseInfoActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		
		//	gets the ID of the weapon to be displayed, then fetches the weapon info
		Intent intent = getIntent();
		int cardID = intent.getIntExtra("cardID", 0);
		ActionCard card = CardData.Actions[cardID];
		
		//	set the Strings necessary for the BaseInfoActivity to display the information correctly
		titleText = String.format("%s", card.getName());
		
		infoText = generateActionInfo(card);
		
		footerText = String.format("CARD ID:  %s", card.getIDString());
		super.onCreate(savedInstanceState);
	}
	
	public static String generateActionInfo (ActionCard card) {
		//	check ammo and damage values for substitution by X (an ammo or damage value of -1 denotes X)
		StringBuilder extras = new StringBuilder();
		int temp = 0;
		extras.append("Price:  "+card.getPrice()+"\n");
		temp = card.getExtraActions();
		if (temp != 0) extras.append("Extra actions:  "+temp+"\n");
		temp = card.getExtraAmmo();
		if (temp != 0) extras.append("Extra ammo:  "+temp+"\n");
		temp = card.getExtraBuys();
		if (temp != 0) extras.append("Extra buys:  "+temp+"\n");
		temp = card.getExtraCards();
		if (temp != 0) extras.append("Extra cards:  "+temp+"\n");
		temp = card.getExtraExplores();
		if (temp != 0) extras.append("Extra explores:  "+temp+"\n");
		temp = card.getExtraGold();
		if (temp != 0) extras.append("Extra gold:  "+temp+"\n");
		
		String cardText = "Card Type:  Action\nExpansion Set:  "+CardData.expansString(card.getExpansion())+
				"\nQuantity in Deck:  "+card.getDeckQuantity()+"\n" + 
				extras.toString();
		if (!card.getText().equals("")) cardText += "\n"+card.getText();
		
		return cardText;
	}
}
