package com.kngames.gametest.regame.graphics;

import android.view.MotionEvent;

import com.kngames.gametest.cards.graphics.ButtonZone;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.GameState.State;

//	a ButtonZone made specifically for the Explore button, changes the text and listener based on the game state
public class ExploreButtonZone extends ButtonZone{
	
	public ExploreButtonZone(int x, int y, int originCorner, float width, float height, int sizeMode, int drawOrder) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder, "", null);
	}
	
	public ExploreButtonZone(float x, float y, int originCorner, float width, float height, int sizeMode, int drawOrder) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder, "", null);
	}
	
	public void handlePressTouch(MotionEvent event) {
		if (active) {
			if (game.state().currentState() == State.MainPhase && game.getActivePlayer().explores > 0) {
				if (game.getActivePlayer().explores > 0 || Game.DEBUG_MODE) {
					game.getActivePlayer().explores--;
					game.getActivePlayer().mustExplore = false;
					game.state().setState(State.ExploreInitial, true);
				}
			}
			else if (game.state().currentState() == State.ExploreWeapons) {
				game.state().setState(State.ExploreReveal, true);
			}
			else if(game.state().currentState() == State.ExploreAgain) {
				game.getActivePlayer().explores--;
				game.state().setState(State.ExploreReveal, true);
			}
		}
	}
	
	public void update() {
		super.update();
		if (game.state().currentState() == State.MainPhase && game.getActivePlayer().explores > 0) {
			this.buttonText = "EXPLORE";
			this.active = true;
		}
		else if (game.state().currentState() == State.ExploreWeapons) {
			this.buttonText = "READY";
			this.active = true;
		}
		else if(game.state().currentState() == State.ExploreAgain) {
			this.buttonText = "EXPLORE";
			this.active = true;
		}
		else this.active = false;
	}
}
