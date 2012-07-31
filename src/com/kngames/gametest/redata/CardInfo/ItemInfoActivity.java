package com.kngames.gametest.redata.CardInfo;

import com.kngames.gametest.redata.BaseInfoActivity;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.CardTypes.ItemCard;

import android.content.Intent;
import android.os.Bundle;

public class ItemInfoActivity extends BaseInfoActivity {

	public void onCreate(Bundle savedInstanceState) {
		//	gets the ID of the weapon to be displayed, then fetches the weapon info
		Intent intent = getIntent();
		int cardID = intent.getIntExtra("cardID", 0);
		ItemCard card = CardData.Items[cardID];
		
		//	set the Strings necessary for the BaseInfoActivity to display the information correctly
		titleText = String.format("%s", card.getName());
		
		infoText = generateItemInfo(card);
		
		footerText = String.format("CARD ID:  %s", card.getIDString());
		super.onCreate(savedInstanceState);
	}
	
	public static String generateItemInfo(ItemCard card) {
		return String.format(
				"Card Type:  Item\nExpansion Set:  %s\nQuantity in Deck:  %d\nPrice:  %d\n" +
				"Found In:  %s\n\n%s",
				CardData.expansString(card.getExpansion()), card.getDeckQuantity(),card.getPrice(),
				CardData.originString(card.getOrigin()), card.getText());
	}
}
