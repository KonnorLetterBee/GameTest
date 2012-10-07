package com.kngames.gametest.regame.dialog;

import java.util.ArrayList;

import com.kngames.gametest.redata.data.GameData;

import android.app.Dialog;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

public class CombineSplitChooserDialog extends Dialog {

	protected RelativeLayout container;
	protected ListView itemList;
	protected LinearLayout buttonPanel;
	protected Button btnSplit;
	protected Button btnCancel;
	
	protected Context context;
	protected ArrayList<Pair<Integer,String>> scenarioPairs;
	protected ArrayList<String> scenarioStrings;
	
	// the interface for what you want to do on the calling activity
    public interface DialogEventListener {
        public void buttonPressed(int value);
    }
    DialogEventListener onEventListener;
	
    
	public CombineSplitChooserDialog(Context context, DialogEventListener dialogEventListener) {
		super(context);
		this.onEventListener = dialogEventListener;
		init(context);
	}

	public CombineSplitChooserDialog(Context context, int theme, DialogEventListener onCustomDialogEventListener) {
		super(context, theme);
		this.onEventListener = onCustomDialogEventListener;
		init(context);
	}

	public CombineSplitChooserDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener, DialogEventListener onCustomDialogEventListener) {
		super(context, cancelable, cancelListener);
		this.onEventListener = onCustomDialogEventListener;
		init(context);
	}
	
	//	initialize the structure
	void init(Context context) {
		this.context = context;
    	setTitle("Combine with:");
    	setCancelable(true);
		
    	setupLayout();
    	
    	LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	setContentView(container, mainParams);
	}
	
	//	set up the layout, short of setting it to the content view
	void setupLayout() {
		//	set up main elements
    	container = new RelativeLayout(context);
    	RelativeLayout.LayoutParams subParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	container.setLayoutParams(subParams);
    	
    	//  set up the buttons
    	buttonPanel = new LinearLayout(context);
    	buttonPanel.setId(9000);
    	subParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	subParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    	buttonPanel.setLayoutParams(subParams);
    	
    	btnSplit = new Button(context);
    	subParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	btnSplit.setLayoutParams(subParams);
    	btnSplit.setText("Split Stack");
    	btnSplit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	onEventListener.buttonPressed(-1);
            	dismiss();
            }
        });
    	buttonPanel.addView(btnSplit);
    	
    	btnCancel = new Button(context);
    	btnCancel.setLayoutParams(subParams);
    	btnCancel.setText("Cancel");
    	btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	dismiss();
            }
        });
    	buttonPanel.addView(btnCancel);
    	container.addView(buttonPanel);
    	
    	//	set up ListView
    	itemList = new ListView(context);
    	subParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	subParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    	subParams.addRule(RelativeLayout.ABOVE, buttonPanel.getId());

    	//	set up list of items
    	scenarioPairs = new ArrayList<Pair<Integer,String>>();
    	scenarioStrings = new ArrayList<String>();
    	for (int i = 0; i < GameData.CustomScenarios.size(); i++) {
    		scenarioPairs.add(new Pair<Integer, String>(
    				GameData.CustomScenarios.get(i).first, 
    				GameData.CustomScenarios.get(i).second.name()));
    		scenarioStrings.add(GameData.CustomScenarios.get(i).second.name());
    	}
    	int resID = android.R.layout.simple_list_item_1;
    	ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(context, resID, scenarioStrings);
		itemList.setAdapter(listAdapter);
		itemList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> aV, View v, int index, long l) {
				onEventListener.buttonPressed(index);
			}
		});
		itemList.setLayoutParams(subParams);
    	
    	container.addView(itemList);
	}
}