package com.kngames.gametest.redata;

import com.kngames.gametest.R;
import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.Info.InfoFrags.*;
import com.kngames.gametest.redata.Info.SelectorFrags.*;

import android.support.v4.app.FragmentTransaction;

public class REInfoFragmentActivity extends BaseSingleFragmentActivity {

	public static final int ACTION_FRAG = 0;
	public static final int CHAR_FRAG = 1;
	public static final int ITEM_FRAG = 2;
	public static final int MANS_FRAG = 3;
	public static final int WEAPON_FRAG = 4;
	public static final int SCEN_FRAG = 5;
	
	public static final int SELECTOR_FRAG = 0;
	public static final int EXPAND_SELECTOR_FRAG = 1;
	public static final int INFO_FRAG = 2;
	
	private int groupType;
	private int fragType;
	
	public REInfoFragmentActivity() {
		groupType = getIntent().getIntExtra("groupType", 0);
        fragType = getIntent().getIntExtra("fragType", 0);
	}

	@Override
	public void chooseFragment(FragmentTransaction ft) {
		switch (groupType) {
        case SELECTOR_FRAG:
        	switch (fragType) {
            case ITEM_FRAG:		ft.add(R.id.fragment_content, new ItemSelectorFragment());  break;
        	}	break;
        case EXPAND_SELECTOR_FRAG:
        	switch (fragType) {
            case ACTION_FRAG:	ft.add(R.id.fragment_content, new ActionExpandSelectorFragment());  break;
            case CHAR_FRAG:		ft.add(R.id.fragment_content, new CharacterExpandSelectorFragment());  break;
            case MANS_FRAG:		ft.add(R.id.fragment_content, new MansionExpandSelectorFragment());  break;
            case WEAPON_FRAG:	ft.add(R.id.fragment_content, new WeaponExpandSelectorFragment());  break;
            case SCEN_FRAG:		ft.add(R.id.fragment_content, new ScenExpandSelectorFragment());  break;
        	}	break;
        case INFO_FRAG:
        	switch (fragType) {
            case ACTION_FRAG:	ft.add(R.id.fragment_content, new ActionInfoFragment());  break;
            case CHAR_FRAG:		ft.add(R.id.fragment_content, new CharacterInfoFragment());  break;
            case ITEM_FRAG:		ft.add(R.id.fragment_content, new ItemInfoFragment());  break;
            case MANS_FRAG:		ft.add(R.id.fragment_content, new MansionInfoFragment());  break;
            case WEAPON_FRAG:	ft.add(R.id.fragment_content, new WeaponInfoFragment());  break;
            case SCEN_FRAG:		ft.add(R.id.fragment_content, new ScenInfoFragment());  break;
        	}	break;
        }
	}
}