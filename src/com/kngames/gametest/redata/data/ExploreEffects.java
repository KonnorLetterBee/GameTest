package com.kngames.gametest.redata.data;

import com.kngames.gametest.regame.gamestruct.*;
import com.kngames.gametest.regame.gamestruct.ExploreEffect.*;
import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.CardTypes.*;
import com.kngames.gametest.redata.CardTypes.WeaponCard.WeaponType;

public class ExploreEffects {
	//	an effect that buffs (or nerfs for negative values) all weapons used in this explore
	public static class BuffAllWeaponsEffect implements OnApplyEffectListener {
		int amountChange;
		public BuffAllWeaponsEffect (int amountChange) {
			this.amountChange = amountChange;
		}
		public void applyEffect(Game game, Player actingPlayer) {
			REDeck weapons = actingPlayer.weapons();
			for(int i = 0; i < weapons.size(); i++) {
				((WeaponCard)weapons.peek(i)).damageThisRound += amountChange;
			}
		}
	}
	
	public static class SurvivalKnifeBuff implements OnApplyEffectListener {
		WeaponCard survival;
		public SurvivalKnifeBuff(WeaponCard survival) {
			this.survival = survival;
		}
		public void applyEffect(Game game, Player actingPlayer) {
			REDeck temp = actingPlayer.weapons();
			for (int i = 0; i < temp.size(); i++) {
				if (temp.peek(i) instanceof WeaponCard && ((WeaponCard)temp.peek(i)).getWeaponType() == WeaponType.Knife) {
					survival.damageThisRound += 5;
				}
			}
		}
	}
}
