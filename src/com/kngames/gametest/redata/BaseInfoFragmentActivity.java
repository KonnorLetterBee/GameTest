package com.kngames.gametest.redata;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.CardInfo.InfoFrags.*;
import com.kngames.gametest.redata.ScenInfo.ScenInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class BaseInfoFragmentActivity extends FragmentActivity {

	public static final int ACTION_FRAG = 0;
	public static final int CHAR_FRAG = 1;
	public static final int ITEM_FRAG = 2;
	public static final int MANS_FRAG = 3;
	public static final int WEAPON_FRAG = 4;
	public static final int SCEN_FRAG = 5;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragtest_activity);
        
        FragmentManager fm       = getSupportFragmentManager();
        Fragment        fragment = fm.findFragmentById(R.id.fragment_content);
        int fragType = getIntent().getIntExtra("fragType", 0);
        
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            
            switch (fragType) {
            case ACTION_FRAG:	ft.add(R.id.fragment_content, new ActionInfoFragment());  break;
            case CHAR_FRAG:		ft.add(R.id.fragment_content, new CharacterInfoFragment());  break;
            case ITEM_FRAG:		ft.add(R.id.fragment_content, new ItemInfoFragment());  break;
            case MANS_FRAG:		ft.add(R.id.fragment_content, new MansionInfoFragment());  break;
            case WEAPON_FRAG:	ft.add(R.id.fragment_content, new WeaponInfoFragment());  break;
            case SCEN_FRAG:		ft.add(R.id.fragment_content, new ScenInfoFragment());  break;
            }
            
            ft.commit();
        }
    }
}