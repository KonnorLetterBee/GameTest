package com.kngames.gametest.regame.Dialog;

import com.kngames.gametest.R;

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
	
	protected Context context;
	
	public final int CARD_PIC_WIDTH = 125;
	public final int CARD_PIC_HEIGHT = 175;
	public final int SPACER_WIDTH = 16;
	public final int SPACER_HEIGHT = 16;
	
	// the interface for what you want to do on the calling activity
    public interface DialogEventListener {
        public void buttonPressed(int value);
    }
    DialogEventListener onButtonPressedListener;
	
    
	public CardChooserDialog(Context context, DialogEventListener onCustomDialogEventListener) {
		super(context);
		this.onButtonPressedListener = onCustomDialogEventListener;
		init(context);
	}

	public CardChooserDialog(Context context, int theme, DialogEventListener onCustomDialogEventListener) {
		super(context, theme);
		this.onButtonPressedListener = onCustomDialogEventListener;
		init(context);
	}

	public CardChooserDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener, DialogEventListener onCustomDialogEventListener) {
		super(context, cancelable, cancelListener);
		this.onButtonPressedListener = onCustomDialogEventListener;
		init(context);
	}
	
	//	initialize the structure
	void init(Context context) {
		this.context = context;
    	setTitle("Choose a Card");
    	setCancelable(true);
		
    	setupLayout();
    	
    	LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	setContentView(scrollLayout, mainParams);
	}
	
	//	set up the layout, short of setting it to the content view
	void setupLayout() {
		//	set up mainLayout and rowContainer which will contain all elements
    	scrollLayout = new HorizontalScrollView(context);
    	
    	rowContainer = new LinearLayout(context);
    	rowContainer.setOrientation(LinearLayout.VERTICAL);
    	LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    	rowContainer.setLayoutParams(subParams);
    	
    	scrollLayout.addView(rowContainer);
    	
    	
    	//	set up structures for rows of card buttons
		row1 = new LinearLayout(context);
    	row2 = new LinearLayout(context);
    	row3 = new LinearLayout(context);
    	
    	//	set params for each row
    	LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CARD_PIC_HEIGHT);
    	rowParams.setMargins(SPACER_WIDTH, 0, SPACER_WIDTH, 0);
    	row1.setLayoutParams(rowParams);
    	
    	rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CARD_PIC_HEIGHT);
    	rowParams.setMargins(SPACER_WIDTH, SPACER_HEIGHT, SPACER_WIDTH, 0);
    	row2.setLayoutParams(rowParams);
    	
    	rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CARD_PIC_HEIGHT);
    	rowParams.setMargins(SPACER_WIDTH, SPACER_HEIGHT, SPACER_WIDTH, SPACER_HEIGHT);
    	row3.setLayoutParams(rowParams);
    	
    	//	add the rows to the rowContainer, then set the content view
    	rowContainer.addView(row1);
    	rowContainer.addView(row2);
    	rowContainer.addView(row3);
	}
	
	public void addButton(String text) {
		//	create the new button
		Button cardButton = new Button(context);
		cardButton.setId(numButtons);
    	cardButton.setLayoutParams(new ViewGroup.LayoutParams(CARD_PIC_WIDTH, ViewGroup.LayoutParams.MATCH_PARENT));
    	cardButton.setText(text);
    	cardButton.setTextColor(Color.WHITE);
    	cardButton.setBackgroundResource(R.drawable.card_back_small);
    	cardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	onButtonPressedListener.buttonPressed(v.getId());
            	dismiss();
            }
        });
    	
    	View spacer = new View(context);
    	spacer.setLayoutParams(new ViewGroup.LayoutParams(SPACER_WIDTH, SPACER_HEIGHT));
    	
		if (numButtons % 3 == 0) {	// add button to top row
			if (numButtons / 3 != 0) row1.addView(spacer);
			row1.addView(cardButton);
		} else if (numButtons % 3 == 1) {	// add button to middle row
			if (numButtons / 3 != 0) row2.addView(spacer);
			row2.addView(cardButton);
		} else {	// add button to bottom row
			if (numButtons / 3 != 0) row3.addView(spacer);
			row3.addView(cardButton);
		}
		
		numButtons++;
	}
}
