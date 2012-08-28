package com.kngames.gametest.regame.graphics;

import android.graphics.Rect;

import com.kngames.gametest.cards.graphics.ButtonZone;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.GameState.State;

//	a ButtonZone made specifically for the Explore button, changes the text and listener based on the game state
public class BuyCardButtonZone extends ButtonZone{
	
	private final OnPressListener buyCard = this.press = new ButtonZone.OnPressListener() {
		public void buttonPressed() {
			if (game.state().currentState() == State.MainPhase && game.getActivePlayer().buys > 0 || Game.DEBUG_MODE)
				game.shop().popupBuyDialog();
		}
	};
	
	public BuyCardButtonZone(Rect area) {
		super(area, "", null);
		this.buttonText = "BUY CARD";
		this.press = buyCard;
	}
	
	public BuyCardButtonZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode, "", null);
		this.buttonText = "BUY CARD";
		this.press = buyCard;
	}
	
	public BuyCardButtonZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode, "", null);
		this.buttonText = "BUY CARD";
		this.press = buyCard;
	}
	
	public void update() {
		super.update(); 
		
		//	keeps the button active if it's the main phase and if the player has a buy (unless debug mode is on)
		if (game.state().currentState() == State.MainPhase && (game.getActivePlayer().buys > 0 || Game.DEBUG_MODE)) {
			this.active = true;
		}
		else this.active = false;
	}
}
