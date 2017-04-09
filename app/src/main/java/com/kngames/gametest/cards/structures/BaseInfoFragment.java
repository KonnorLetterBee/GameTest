package com.kngames.gametest.cards.structures;

import com.kngames.gametest.regame.screens.MainMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class BaseInfoFragment extends Fragment {
	protected String titleText = null;
	protected String infoText = null;
	protected String footerText = null;
	
	public static final int PH_MARGIN = 16;
	public static final int TAB_MARGIN = 32;
	
	public static final int SMPH_TITLE_SIZE = 20;
	public static final int SMPH_BODY_SIZE = 14;
	public static final int SMPH_FOOTER_SIZE = 10;
	
	public static final int PH_TITLE_SIZE = 24;
	public static final int PH_BODY_SIZE = 16;
	public static final int PH_FOOTER_SIZE = 10;
	
	public static final int TB_TITLE_SIZE = 30;
	public static final int TB_BODY_SIZE = 18;
	public static final int TB_FOOTER_SIZE = 10;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ScrollView scroll = new ScrollView(getActivity());
		LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
        scroll.setLayoutParams(mainParams);
		
        RelativeLayout rel = new RelativeLayout(getActivity());
        
        //	initialize the fields, set their IDs and text fields
        TextView title = new TextView(getActivity());
        title.setId(1000);
        if (titleText != null) title.setText(titleText);
        else title.setText("");
        
        TextView info = new TextView(getActivity());
        info.setId(1001);
        if (titleText != null) info.setText(infoText);
        else info.setText("");
        
        TextView footer = new TextView(getActivity());
        footer.setId(1002);
        if (titleText != null) footer.setText(footerText);
        else footer.setText("");
        
        //  set text sizes
        if (MainMenu.SMALL_PHONE_VIEW == true) {	//	small phone
        	title.setTextSize(SMPH_TITLE_SIZE);
        	info.setTextSize(SMPH_BODY_SIZE);
        	footer.setTextSize(SMPH_FOOTER_SIZE);
        } else if (MainMenu.PHONE_VIEW == true) {	//	large phone
        	title.setTextSize(PH_TITLE_SIZE);
        	info.setTextSize(PH_BODY_SIZE);
        	footer.setTextSize(PH_FOOTER_SIZE);
        } else {	//	tablet
        	title.setTextSize(TB_TITLE_SIZE);
        	info.setTextSize(TB_BODY_SIZE);
        	footer.setTextSize(TB_FOOTER_SIZE);
        }
        
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
    			RelativeLayout.LayoutParams.MATCH_PARENT, 
    			RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        
        //	set text sizes and set correct margins for title field
        if (MainMenu.PHONE_VIEW == true) {	//	large phone
        	params.setMargins(PH_MARGIN, PH_MARGIN, PH_MARGIN, 0);
        } else if (MainMenu.TABLET_VIEW == true) {	//	tablet
        	params.setMargins(TAB_MARGIN, TAB_MARGIN, TAB_MARGIN, 0);
        }
        
        rel.addView(title, params);
        
        //	set params for body and footer, then add them to the relative layout
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
    			RelativeLayout.LayoutParams.WRAP_CONTENT, 
    			RelativeLayout.LayoutParams.WRAP_CONTENT);
        params2.setMargins(0, PH_MARGIN, 0, 0);
        params2.addRule(RelativeLayout.ALIGN_LEFT, title.getId());
        params2.addRule(RelativeLayout.ALIGN_RIGHT, title.getId());
        params2.addRule(RelativeLayout.BELOW, title.getId());
        rel.addView(info, params2);
        
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(params2);
        params3.addRule(RelativeLayout.BELOW, info.getId());
        rel.addView(footer, params3);
        
        //	add the RelativeLayout to the main layout
        LinearLayout.LayoutParams relParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
        scroll.addView(rel, relParams);
        
        return scroll;
	}
}
