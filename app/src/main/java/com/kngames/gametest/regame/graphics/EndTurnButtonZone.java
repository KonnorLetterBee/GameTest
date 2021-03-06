package com.kngames.gametest.regame.graphics;

import com.kngames.gametest.cards.graphics.ButtonZone;
import com.kngames.gametest.regame.gamestruct.GameState.State;

//	a ButtonZone made specifically for the Explore button, changes the text and listener based on the game state
public class EndTurnButtonZone extends ButtonZone{
	
	private final OnPressListener endTurn = this.press = new ButtonZone.OnPressListener() {
		public void buttonPressed() {
			game.state().setState(State.EndTurn, true);
		}
	};
	
	public EndTurnButtonZone(int x, int y, int originCorner, float width, float height, int sizeMode, int drawOrder) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder, "", null);
		this.buttonText = "END TURN";
		this.press = endTurn;
	}
	
	public EndTurnButtonZone(float x, float y, int originCorner, float width, float height, int sizeMode, int drawOrder) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder, "", null);
		this.buttonText = "END TURN";
		this.press = endTurn;
	}
	
	public void update() {
		super.update();
		if (game.state().currentState() == State.MainPhase) {
			this.active = true;
		}
		else this.active = false;
	}
}
