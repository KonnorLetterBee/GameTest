package com.kngames.gametest.regame.screens;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout;

public class MainMenuView extends ScrollView {
	
	protected RelativeLayout relShell;
	protected RelativeLayout relLayout;
	protected LinearLayout buttonContainer;
	
	public final int TEXT_SIZE = 30;
	public final int BUTTON_WIDTH = 400;
	public final int BUTTON_HEIGHT = 150;
	public final int SPACER_WIDTH = 16;
	public final int SPACER_HEIGHT = 16;
	
	protected Context context;
	protected MainMenu parent;
	
	public MainMenuView(Context context, MainMenu parent) {
		super(context);
		init(context, parent);
	}
	
	//	initialize the structure
	void init(Context context, MainMenu parent) {
		this.context = context;
		this.parent = parent;
    	setupLayout();
    	
    	setFocusable(true);
	}
	
	//	set up the layout
	void setupLayout() {
		relShell = new RelativeLayout(context);
    	RelativeLayout.LayoutParams shellParams = new RelativeLayout.LayoutParams(
    			RelativeLayout.LayoutParams.MATCH_PARENT,
    			RelativeLayout.LayoutParams.MATCH_PARENT);
    	shellParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
    	
    	relLayout = new RelativeLayout(context);
    	RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(
    			RelativeLayout.LayoutParams.WRAP_CONTENT,
    			RelativeLayout.LayoutParams.WRAP_CONTENT);
    	relParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
    	
    	buttonContainer = new LinearLayout(context);
    	buttonContainer.setOrientation(LinearLayout.VERTICAL);
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	
    	relLayout.addView(buttonContainer, params);
    	relShell.addView(relLayout, relParams);
    	addView(relShell, shellParams);
	}
	
	public void addButton(String text, OnClickListener listen) {
		//	create the new button
		Button newButton = new Button(context);
    	newButton.setLayoutParams(new ViewGroup.LayoutParams(BUTTON_WIDTH, BUTTON_HEIGHT));
    	newButton.setText(text);
    	newButton.setOnClickListener(listen);
    	
    	View spacer = new View(context);
    	spacer.setLayoutParams(new ViewGroup.LayoutParams(SPACER_WIDTH, SPACER_HEIGHT));
    	
    	buttonContainer.addView(newButton);
    	buttonContainer.addView(spacer);
	}
	
	public void addButton(String text, final Class<?> cls) {
		addButton(text, new OnClickListener() {
			public void onClick(View v) {
				parent.startActivity(cls);
			}
		});
	}
}
