package com.kngames.gametest.regame.dialog;

import com.kngames.gametest.R;
import com.kngames.gametest.redata.CardTypes.RECard;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class CardChooserDialog extends Dialog {

	protected HorizontalScrollView scrollLayout;
	protected LinearLayout rowContainer;
	protected LinearLayout row1;
	protected LinearLayout row2;
	protected LinearLayout row3;
	protected int numButtons = 0;
	protected int numRows = 0;
	
	protected Context context;
	
	public final int CARD_PIC_WIDTH = 125;
	public final int CARD_PIC_HEIGHT = 175;
	public final int SPACER_WIDTH = 16;
	public final int SPACER_HEIGHT = 16;
	
	// the interface for what you want to do on the calling activity
    public interface CardChosenListener {
        public void buttonPressed(int index);
    }
    CardChosenListener onCardChosenListener;
    
	public CardChooserDialog(Context context, RECard[] cards, CardChosenListener customListener) {
		super(context);
		this.onCardChosenListener = customListener;
		init(context, cards);
	}

	public CardChooserDialog(Context context, RECard[] cards, int theme, CardChosenListener customListener) {
		super(context, theme);
		this.onCardChosenListener = customListener;
		init(context, cards);
	}

	public CardChooserDialog(Context context, RECard[] cards, boolean cancelable,
			OnCancelListener cancelListener, CardChosenListener customListener) {
		super(context, cancelable, cancelListener);
		this.onCardChosenListener = customListener;
		init(context, cards);
	}
	
	public CardChooserDialog(Context context, String[] cards, CardChosenListener customListener) {
		super(context);
		this.onCardChosenListener = customListener;
		init(context, cards);
	}

	public CardChooserDialog(Context context, String[] cards, int theme, CardChosenListener customListener) {
		super(context, theme);
		this.onCardChosenListener = customListener;
		init(context, cards);
	}

	public CardChooserDialog(Context context, String[] cards, boolean cancelable,
			OnCancelListener cancelListener, CardChosenListener customListener) {
		super(context, cancelable, cancelListener);
		this.onCardChosenListener = customListener;
		init(context, cards);
	}
	
	//	initialize the structure using a list of Strings
	void init(Context context, String[] cards) {
		this.context = context;
    	setTitle("Choose a Card");
    	setCancelable(true);
		
    	setupLayout(2);
    	
    	//	add each card in the array to the layout
    	for (int i = 0; i < cards.length; i++) {
    		addCard(cards[i]);
    	}
    	
    	LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	setContentView(scrollLayout, mainParams);
	}
	
	//	initialize the structure using a list of card objects
	void init(Context context, RECard[] cards) {
		this.context = context;
    	setTitle("Choose a Card");
    	setCancelable(true);
		
    	setupLayout(2);
    	
    	//	add each card in the array to the layout
    	for (int i = 0; i < cards.length; i++) {
    		addCard(cards[i].getName());
    	}
    	
    	LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	setContentView(scrollLayout, mainParams);
	}
	
	//	set up the layout, short of setting it to the content view
	void setupLayout(int rows) {
		//	error-checking for number of active rows
		if (rows < 1) numRows = 1;
		else if (rows > 3) numRows = 3;
		else numRows = rows;
		
		//	set up mainLayout and rowContainer which will contain all elements
    	scrollLayout = new HorizontalScrollView(context);
    	
    	rowContainer = new LinearLayout(context);
    	rowContainer.setOrientation(LinearLayout.VERTICAL);
    	LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	
    	//	set up each row individually
    	row1 = new LinearLayout(context);
    	LinearLayout.LayoutParams row1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CARD_PIC_HEIGHT);
    	row1Params.setMargins(0, SPACER_HEIGHT, 0, SPACER_HEIGHT);
    	rowContainer.addView(row1, row1Params);
    	row1.addView(new View(context), new ViewGroup.LayoutParams(SPACER_WIDTH, SPACER_HEIGHT));
    	
    	if (numRows >= 2) {
    		row2 = new LinearLayout(context);
    		LinearLayout.LayoutParams row2Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CARD_PIC_HEIGHT);
        	row2Params.setMargins(0, 0, 0, SPACER_HEIGHT);
        	rowContainer.addView(row2, row2Params);
        	row2.addView(new View(context), new ViewGroup.LayoutParams(SPACER_WIDTH, SPACER_HEIGHT));
    	}
    	if (numRows >= 3) {
    		row3 = new LinearLayout(context);
    		LinearLayout.LayoutParams row3Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CARD_PIC_HEIGHT);
        	row3Params.setMargins(0, 0, 0, SPACER_HEIGHT);
        	rowContainer.addView(row3, row3Params);
        	row3.addView(new View(context), new ViewGroup.LayoutParams(SPACER_WIDTH, SPACER_HEIGHT));
    	}
    	
    	scrollLayout.addView(rowContainer, subParams);
	}
	
	public void addCard(String entry) {
		//	create the new button
		Button cardButton = new Button(context);
		cardButton.setId(numButtons);
    	cardButton.setLayoutParams(new ViewGroup.LayoutParams(CARD_PIC_WIDTH, ViewGroup.LayoutParams.MATCH_PARENT));
    	cardButton.setText(entry);
    	cardButton.setTextColor(Color.WHITE);
    	cardButton.setBackgroundResource(R.drawable.card_back_small);
    	cardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	onCardChosenListener.buttonPressed(v.getId());
            	dismiss();
            }
        });
    	
    	View spacer = new View(context);
    	spacer.setLayoutParams(new ViewGroup.LayoutParams(SPACER_WIDTH, SPACER_HEIGHT));
    	
		if (numButtons % numRows == 0) {	// add button to top row
			row1.addView(cardButton);
			row1.addView(spacer);
		} else if (numButtons % numRows == 1) {	// add button to middle row
			row2.addView(cardButton);
			row2.addView(spacer);
		} else {	// add button to bottom row
			row3.addView(cardButton);
			row3.addView(spacer);
		}
		
		numButtons++;
	}
}
