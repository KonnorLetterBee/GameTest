package com.kngames.gametest.cards.structures;

import com.kngames.gametest.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public abstract class BaseSingleFragmentActivity extends FragmentActivity {

	public static final int ACTION_FRAG = 0;
	public static final int CHAR_FRAG = 1;
	public static final int ITEM_FRAG = 2;
	public static final int MANS_FRAG = 3;
	public static final int WEAPON_FRAG = 4;
	public static final int SCEN_FRAG = 5;
	
	public static final int SELECTOR_FRAG = 0;
	public static final int EXPAND_SELECTOR_FRAG = 1;
	public static final int INFO_FRAG = 2;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standard_frag_activity);
        
        FragmentManager fm       = getSupportFragmentManager();
        Fragment        fragment = fm.findFragmentById(R.id.fragment_content);
        
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            chooseFragment(ft);
            ft.commit();
        }
    }
    
    public abstract void chooseFragment(FragmentTransaction ft);
}