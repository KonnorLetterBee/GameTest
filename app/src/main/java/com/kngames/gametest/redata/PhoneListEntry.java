package com.kngames.gametest.redata;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

public class PhoneListEntry extends TextView {
	
	public PhoneListEntry (Context context, AttributeSet ats, int ds) {
		super(context, ats, ds);
	}

	public PhoneListEntry (Context context) {
		super(context);
	}

	public PhoneListEntry (Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onDraw(Canvas canvas) {
		// Move the text across from the margin
		canvas.save();

		// Use the TextView to render the text.
		super.onDraw(canvas);
		canvas.restore();
	}
	
	public void onClick () {
		
	}
}