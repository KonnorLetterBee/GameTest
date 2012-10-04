package com.kngames.gametest.redata.Info.InfoFrags;

import com.kngames.gametest.cards.CardData;
import com.kngames.gametest.cards.structures.BaseInfoFragment;
import com.kngames.gametest.redata.CardTypes.ItemCard;
import com.kngames.gametest.redata.data.Expansion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ItemInfoFragment extends BaseInfoFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	gets the ID of the weapon to be displayed, then fetches the weapon info
		Intent intent = getActivity().getIntent();
		String catTag = intent.getStringExtra("catTag");
		int intTag = intent.getIntExtra("intTag", 0);
		
		CardData data = CardData.getCardData();
		ItemCard card = (ItemCard)data.getCard(catTag, intTag);
		
		//	set the Strings necessary for the BaseInfoActivity to display the information correctly
		titleText = String.format("%s", card.getName());
		
		infoText = generateItemInfo(card);
		
		footerText = String.format("CARD ID:  %s", card.getIDString());
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public static String generateItemInfo(ItemCard card) {
		return String.format(
				"Card Type:  Item\nExpansion Set:  %s\nQuantity in Deck:  %d\nPrice:  %d\n\n%s",
				Expansion.expansNames()[card.getExpansion()], card.getDeckQuantity(),card.getPrice(),
				card.getText());
	}
}
