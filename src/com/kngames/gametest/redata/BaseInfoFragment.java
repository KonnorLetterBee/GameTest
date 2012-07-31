package com.kngames.gametest.redata;

import com.kngames.gametest.R;
import com.kngames.gametest.regame.MainMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseInfoFragment extends Fragment {
	protected String titleText = null;
	protected String infoText = null;
	protected String footerText = null;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (MainMenu.PHONE_VIEW == true) view = inflater.inflate(R.layout.card_info_view_phone, container, false);
        else view = inflater.inflate(R.layout.card_info_view_tablet, container, false);
        
        TextView title = (TextView)view.findViewById(R.id.titleView);
        if (titleText != null) title.setText(titleText);
        else title.setText("");
        
        TextView info = (TextView)view.findViewById(R.id.infoView);
        if (titleText != null) info.setText(infoText);
        else info.setText("");
        
        TextView footer = (TextView)view.findViewById(R.id.smallInfoView);
        if (titleText != null) footer.setText(footerText);
        else footer.setText("");
        
        return view;
	}
}
