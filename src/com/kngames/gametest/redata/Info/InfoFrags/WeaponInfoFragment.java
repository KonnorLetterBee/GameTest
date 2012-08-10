package com.kngames.gametest.redata.Info.InfoFrags;

import com.kngames.gametest.cards.structures.BaseInfoFragment;
import com.kngames.gametest.redata.CardData;
import com.kngames.gametest.redata.CardTypes.WeaponCard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WeaponInfoFragment extends BaseInfoFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	gets the ID of the weapon to be displayed, then fetches the weapon info
		Intent intent = getActivity().getIntent();
		int cardID = intent.getIntExtra("cardID", 0);
		WeaponCard card = CardData.findWeapon(cardID);
		
		//	set the Strings necessary for the BaseInfoActivity to display the information correctly
		titleText = String.format("%s", card.getName());
		
		infoText = generateWeaponInfo(card);
		
		footerText = String.format("CARD ID:  %s", card.getIDString());
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	public static String generateWeaponInfo(WeaponCard card) {
		//	if necessary, add the text "[TRASH ONCE USED]" to the beginning of the text string
		String cardDesc = "";
		if (card.trashFlagOn()) cardDesc += "[TRASH ONCE USED]\n";
		cardDesc += card.getText();
			
		//	check ammo and damage values for substitution by X (an ammo or damage value of -1 denotes X)
		String ammo;
		if (card.getAmmoRec() == -1) ammo = "X";
		else ammo = "" + card.getAmmoRec();
		String damage;
		if (card.getDamage() == -1) damage = "X";
		else damage = "" + card.getDamage();
		
		String cardText = String.format(
				"Card Type:  Weapon\nExpansion Set:  %s\nQuantity in Deck:  %d\nPrice:  %d\n" +
				"Ammo Requirement:  %s\nDamage:  %s",
				CardData.expansString(card.getExpansion()), card.getDeckQuantity(),card.getPrice(),
				ammo, damage);
		if (!cardDesc.equals("")) cardText += "\n\n"+cardDesc;
		
		return cardText;
	}
}
