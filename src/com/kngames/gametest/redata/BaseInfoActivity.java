package com.kngames.gametest.redata;

import com.kngames.gametest.R;
import com.kngames.gametest.regame.MainMenu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BaseInfoActivity extends Activity {
	protected String titleText = null;
	protected String infoText = null;
	protected String footerText = null;
	
    //	a base InfoActivity class
    //	classes that extend this only need to set the strings titleText, infoText, and footerText
    //	in the onCreate() method, then call super.onCreate()
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MainMenu.PHONE_VIEW == true) setContentView(R.layout.card_info_view_phone);
        else setContentView(R.layout.card_info_view_tablet);
        
        TextView title = (TextView)findViewById(R.id.titleView);
        if (titleText != null) title.setText(titleText);
        else title.setText("");
        
        TextView info = (TextView)findViewById(R.id.infoView);
        if (titleText != null) info.setText(infoText);
        else info.setText("");
        
        TextView footer = (TextView)findViewById(R.id.smallInfoView);
        if (titleText != null) footer.setText(footerText);
        else footer.setText("");
	}
}
