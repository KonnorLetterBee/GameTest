package com.kngames.gametest.regame.graphics;

import android.graphics.Rect;

import com.kngames.gametest.cards.graphics.ButtonZone;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.GameState.State;

//	a ButtonZone made specifically for the Explore button, changes the text and listener based on the game state
public class ExploreButtonZone extends ButtonZone{
	private final OnPressListener mainPhaseListener = new OnPressListener() {
		public void buttonPressed() {
			if (game.getActivePlayer().explores > 0 || Game.DEBUG_MODE) {
				game.getActivePlayer().explores--;
				game.state().setState(State.ExploreInitial);
			}
		}
	};
	
	private final OnPressListener weaponPhaseListener = new OnPressListener() {
		public void buttonPressed() {
			game.state().setState(State.ExploreRespond);
		}
	};
	
	public ExploreButtonZone(Rect area) {
		super(area, "", null);
	}
	
	public ExploreButtonZone(int x, int y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode, "", null);
	}
	
	public ExploreButtonZone(float x, float y, int originCorner, float width, float height, int sizeMode) {
		super(x, y, originCorner, width, height, sizeMode, "", null);
	}
	
	public void update() {
		if (game.state().currentState() == State.MainPhase && game.getActivePlayer().explores > 0) {
			this.press = mainPhaseListener;
			this.buttonText = "EXPLORE";
			this.active = true;
		}
		else if (game.state().currentState() == State.ExploreWeapons) {
			this.press = weaponPhaseListener;
			this.buttonText = "READY";
			this.active = true;
		}
		else this.active = false;
	}
}
