package com.kngames.gametest.regame.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;

import com.kngames.gametest.cards.graphics.GameZone;
import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.regame.Game;
import com.kngames.gametest.regame.Player;

public abstract class REGameZone extends GameZone {

	public REGameZone(Rect area) {
		super(area);
	}
	public REGameZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	public REGameZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode);
	}
	
	//	sets the game this GameZone will use to look for data
	public void setGame(Game g) {
		game = g;
	}
	
	protected Game game;
	
	protected REDeck getVisibleDeck() { return getVisiblePlayer().deck(); }
	protected REDeck getVisibleHand() { return getVisiblePlayer().hand(); }
	protected REDeck getVisibleInPlay() { return getVisiblePlayer().inPlay(); }
	protected REDeck getVisibleDiscard() { return getVisiblePlayer().discard(); }
	protected REDeck getVisibleAttached() { return getVisiblePlayer().attachedCards(); }
	protected Player getVisiblePlayer() { return game.players()[game.viewingPlayer()]; }
	
	private final int BORDER_THICKNESS = 4;
	public void drawTestBorder(Canvas canvas) {
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE); 
		paint.setStyle(Style.FILL); 
		canvas.drawRect(area, paint);

		paint.setColor(Color.BLACK);
		canvas.drawRect(new Rect (
				area.left + BORDER_THICKNESS,
				area.top + BORDER_THICKNESS,
				area.right - BORDER_THICKNESS,
				area.bottom - BORDER_THICKNESS), paint);
	}
	
	private final int DATA_TEXT_SIZE = 20;
	protected void drawTestData(Canvas canvas, String[] data, int startHeight) {
		Paint paint = new Paint();
		paint.setTextSize(DATA_TEXT_SIZE);
		paint.setColor(Color.WHITE);
		int height = startHeight;
		for (int i = 0; i < data.length; i++) {
			if (data[i] != null) {
				canvas.drawText(data[i], area.left + 10, height, paint);
				height += DATA_TEXT_SIZE + 5;
			}
		}
	}
}
