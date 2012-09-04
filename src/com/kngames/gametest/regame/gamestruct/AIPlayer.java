package com.kngames.gametest.regame.gamestruct;

import com.kngames.gametest.redata.CardTypes.CharacterCard;
import com.kngames.gametest.regame.gamestruct.GameState.State;

public abstract class AIPlayer extends Player {

	public AIPlayer(int id, Game g, CharacterCard ch, String customInventory) {
		super(id, g, ch, customInventory);
	}
	
	public abstract void makeMove(Game game, State state);
}
