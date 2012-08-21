package com.kngames.gametest.regame.graphics.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

import com.kngames.gametest.engine.graphics.AnimationComponent;
import com.kngames.gametest.engine.graphics.DrawObject;
import com.kngames.gametest.engine.graphics.MovementComponent;
import com.kngames.gametest.redata.CardTypes.RECard;

public class TestRECard extends DrawObject {
	//	logging structures
	protected static int classIds = 0;
	protected int id;
	//private static final String TAG = TestRECard.class.getSimpleName();

	private String name;
	private String[] text;
	
	private RECard card;
	
	//private static Bitmap cardBack = ContentManager.getContentManager().getBitmap(R.drawable.card_back_small);
	
	//	constructs a TestRECard
	public TestRECard(int x, int y, RECard card, Bitmap back) {
		super(x, y, back, true);
		id = classIds;
		classIds++;
		
		this.card = card;
		if (card != null) this.name = card.getName();
		else this.name = "";
		this.text = new String[] { "" };
		
		move = new MovementComponent(x, y, 0, 0);
		anim = new AnimationComponent(move);
	}
	
	public String getTag() { return card.getTag(); }
	
	//	draws this DrawObject to the screen
	private final int TITLE_TEXT_SIZE = 20;
	private final int EFFECT_TEXT_SIZE = 17;
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setStyle(Style.FILL); 
		paint.setTextSize(TITLE_TEXT_SIZE);
		canvas.drawText(name, X() - halfWidth + 5, Y() - halfHeight + TITLE_TEXT_SIZE + 5, paint);
		paint.setTextSize(EFFECT_TEXT_SIZE);
		for (int i = 0; i < text.length; i++) {
			canvas.drawText(text[i], X() - halfWidth + 5, 
					Y() - halfHeight + TITLE_TEXT_SIZE + ((EFFECT_TEXT_SIZE + 5) * (i+1)) + 15, paint);
		}
		canvas.drawPoint(X(), Y(), paint);
	}

	///
	///	Touch Events
	///
	public void handleDownTouch(MotionEvent event) {}
	public void handleMoveTouch(MotionEvent event) {}
	public void handleUpTouch(MotionEvent event) {}
}
