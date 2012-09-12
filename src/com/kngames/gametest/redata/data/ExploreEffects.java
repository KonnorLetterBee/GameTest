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
	
	//	explore effect for the Survival Knife
	//	"This Weapon gets +5 Damage for every other Knife Weapon used this turn."
	public static class SurvivalKnifeBuff implements OnApplyEffectListener {
		WeaponCard survival;
		public SurvivalKnifeBuff(WeaponCard survival) {	this.survival = survival; }
		public void applyEffect(Game game, Player actingPlayer) {
			REDeck temp = actingPlayer.weapons();
			for (int i = 0; i < temp.size(); i++) {
				//	temp card is a weapon, the weapon type is Knife, and is not the same Survival Knife
				if (temp.peek(i) instanceof WeaponCard && ((WeaponCard)temp.peek(i)).getWeaponType() == WeaponType.Knife && temp.peek(i) != survival) {
					survival.damageThisRound += 5;
				}
			}
		}
	}
	
	//	explore effect for the Burst-Fire Handgun
	//	"While Attacking with more than 1 Weapon, this Weapon gets +20 Damage during this turn."
	public static class BurstFireHandgunBuff implements OnApplyEffectListener {
		WeaponCard burstfire;
		public BurstFireHandgunBuff(WeaponCard burstfire) { this.burstfire = burstfire; }
		public void applyEffect(Game game, Player actingPlayer) {
			//	if using more than 1 weapon, add 20 damage
			if (actingPlayer.weapons().size() > 1) burstfire.damageThisRound += 20;
		}
	}
}
