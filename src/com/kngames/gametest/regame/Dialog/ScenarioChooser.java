package com.kngames.gametest.regame.Dialog;

import java.util.ArrayList;

import com.kngames.gametest.redata.ScenData;

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

public class ScenarioChooser extends Dialog {

	protected RelativeLayout container;
	protected ListView scenList;
	protected LinearLayout buttonPanel;
	protected Button btnNewScen;
	protected Button btnCancel;
	
	protected Context context;
	protected ArrayList<Pair<Integer,String>> scenarioPairs;
	protected ArrayList<String> scenarioStrings;
	
	// the interface for what you want to do on the calling activity
    public interface DialogEventListener {
        public void buttonPressed(int value);
    }
    DialogEventListener onButtonPressedListener;
	
    
	public ScenarioChooser(Context context, DialogEventListener dialogEventListener) {
		super(context);
		this.onButtonPressedListener = dialogEventListener;
		init(context);
	}

	public ScenarioChooser(Context context, int theme, DialogEventListener onCustomDialogEventListener) {
		super(context, theme);
		this.onButtonPressedListener = onCustomDialogEventListener;
		init(context);
	}

	public ScenarioChooser(Context context, boolean cancelable,
			OnCancelListener cancelListener, DialogEventListener onCustomDialogEventListener) {
		super(context, cancelable, cancelListener);
		this.onButtonPressedListener = onCustomDialogEventListener;
		init(context);
	}
	
	//	initialize the structure
	void init(Context context) {
		this.context = context;
    	setTitle("Choose a Scenario");
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
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	subParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    	buttonPanel.setLayoutParams(subParams);
    	
    	btnNewScen = new Button(context);
    	subParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	btnNewScen.setLayoutParams(subParams);
    	btnNewScen.setText("New Scenario");
    	btnNewScen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	onButtonPressedListener.buttonPressed(-1);
            	dismiss();
            }
        });
    	buttonPanel.addView(btnNewScen);
    	
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
    	scenList = new ListView(context);
    	subParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	subParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    	subParams.addRule(RelativeLayout.ABOVE, buttonPanel.getId());
    	scenList.setLayoutParams(subParams);

    	//	set up custom scenario list
    	scenarioPairs = new ArrayList<Pair<Integer,String>>();
    	scenarioStrings = new ArrayList<String>();
    	for (int i = 0; i < ScenData.CustomScenarios.size(); i++) {
    		scenarioPairs.add(new Pair<Integer, String>(
    				ScenData.CustomScenarios.get(i).first, 
    				ScenData.CustomScenarios.get(i).second.getName()));
    		scenarioStrings.add(ScenData.CustomScenarios.get(i).second.getName());
    	}
    	int resID = android.R.layout.simple_list_item_1;
    	ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(context, resID, scenarioStrings);
		scenList.setAdapter(listAdapter);
		scenList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> aV, View v, int index, long l) {
				onButtonPressedListener.buttonPressed(index);
			}
		});
    	
    	container.addView(scenList);
	}
}