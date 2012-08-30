package com.kngames.gametest.regame.graphics;

import com.kngames.gametest.cards.graphics.ButtonZone;

//	a ButtonZone made specifically for the Action button, to be displayed when a PlayerInputState specifies it should be visible
public class ActionButtonZone extends ButtonZone {
	
	private final OnPressListener actionButton = this.press = new ButtonZone.OnPressListener() {
		public void buttonPressed() {
			if (game.state().playerState() != null) game.state().playerState().onExtraButtonPressed();
		}
	};
	
	public ActionButtonZone(int x, int y, int originCorner, float width, float height, int sizeMode, int drawOrder) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder, "", null);
		this.buttonText = "ACTION";
		this.press = actionButton;
		this.active = false;	//	should never be initially active, activate from within a PlayerInputState
	}
	
	public ActionButtonZone(float x, float y, int originCorner, float width, float height, int sizeMode, int drawOrder) {
		super(x, y, originCorner, width, height, sizeMode, drawOrder, "", null);
		this.buttonText = "ACTION";
		this.press = actionButton;
		this.active = false;	//	should never be initially active, activate from within a PlayerInputState
	}
	
	public void setActionText(String text) {
		this.buttonText = text;
	}
	
	public void update() { }
}
