package com.kngames.gametest.redata.Info;

import com.kngames.gametest.R;
import com.kngames.gametest.cards.structures.BaseSingleFragmentActivity;
import com.kngames.gametest.redata.REInfoFragmentActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MainInfoListActivity extends TabActivity {
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.card_list_main_menu);
	
	    setTitle("Card Encyclopedia");
	    
	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	
	    //	Create an Intent to launch an Activity for the tab, then initialize a TabSpec for each tab and add it to the TabHost
	    //	Repeat for all tabs
	    Intent intent = new Intent().setClass(this, REInfoFragmentActivity.class);
	    intent.putExtra("groupType", BaseSingleFragmentActivity.EXPAND_SELECTOR_FRAG);
	    intent.putExtra("fragType", BaseSingleFragmentActivity.CHAR_FRAG);
	    spec = tabHost.newTabSpec("characters").setIndicator("Characters", res.getDrawable(R.drawable.ic_tab_weapons)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, REInfoFragmentActivity.class);
	    intent.putExtra("groupType", BaseSingleFragmentActivity.EXPAND_SELECTOR_FRAG);
	    intent.putExtra("fragType", BaseSingleFragmentActivity.WEAPON_FRAG);
	    spec = tabHost.newTabSpec("weapons").setIndicator("Weapons", res.getDrawable(R.drawable.ic_tab_weapons)).setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, REInfoFragmentActivity.class);
	    intent.putExtra("groupType", BaseSingleFragmentActivity.EXPAND_SELECTOR_FRAG);
	    intent.putExtra("fragType", BaseSingleFragmentActivity.ACTION_FRAG);
	    spec = tabHost.newTabSpec("actions").setIndicator("Actions", res.getDrawable(R.drawable.ic_tab_weapons)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, REInfoFragmentActivity.class);
	    intent.putExtra("groupType", BaseSingleFragmentActivity.SELECTOR_FRAG);
	    intent.putExtra("fragType", BaseSingleFragmentActivity.ITEM_FRAG);
	    spec = tabHost.newTabSpec("items").setIndicator("Items", res.getDrawable(R.drawable.ic_tab_weapons)).setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, REInfoFragmentActivity.class);
	    intent.putExtra("groupType", BaseSingleFragmentActivity.EXPAND_SELECTOR_FRAG);
	    intent.putExtra("fragType", BaseSingleFragmentActivity.MANS_FRAG);
	    spec = tabHost.newTabSpec("mansion").setIndicator("Mansion", res.getDrawable(R.drawable.ic_tab_weapons)).setContent(intent);
	    tabHost.addTab(spec);
	
	    tabHost.setCurrentTab(0);
	}
}