package com.kngames.gametest.redata.data;

import com.kngames.gametest.cards.Card.CardComp;
import com.kngames.gametest.cards.CardData;
import com.kngames.gametest.redata.REDeck;
import com.kngames.gametest.redata.data.ExploreEffects.*;
import com.kngames.gametest.redata.CardTypes.*;
import com.kngames.gametest.redata.CardTypes.RECard.*;
import com.kngames.gametest.regame.gamestruct.ExploreEffect;
import com.kngames.gametest.regame.gamestruct.Game;
import com.kngames.gametest.regame.gamestruct.GameState.*;
import com.kngames.gametest.regame.gamestruct.Player;
import com.kngames.gametest.regame.graphics.REDeckViewZone;
import com.kngames.gametest.regame.graphics.REZoneManager;

public class CardEffects {

	//region Action Effects
	
	public static class DeadlyAimEffect implements OnPlayListener {
		public void playCard(RECard card, Game game, Player actingPlayer) {
			game.exploreEffects().add(new ExploreEffect(game, actingPlayer, new BuffAllWeaponsEffect(10)));
		}
	}

	public static class ShatteredMemoriesEffect implements OnPlayListener {
		public static class ShatteredMemoriesState extends PlayerInputState {
			private int remaining = 2;
			private REDeckViewZone discardZone;
			public ShatteredMemoriesState (Game game, Player actingPlayer) {
				super(game, actingPlayer);
			}
			public void onPlayerInputStart() {
				//	if discard pile contains no cards, immediately end state for lack of possible actions
				if (actingPlayer.discard().size() <= 0) game.state().endPlayerInput();
				else {
					discardZone = (REDeckViewZone) REZoneManager.getREZoneManager().getZone("discard_view");
					discardZone.activate();
					actionButton.buttonText = "DONE";
					actionButton.activate();
					gameStateMessage = "You may trash up to 2 cards from your discard pile.";
					
				}
			}
			public boolean isSelectable (REDeck source, int index) throws IndexOutOfBoundsException {
				return source == actingPlayer.discard();
			}
			public void onCardSelected(REDeck source, int index) {
				if (isSelectable(source, index)) {
					game.shop().returnCard((RECard) source.pop(index));
					remaining--;
					if (remaining == 0 || actingPlayer.discard().size() <= 0) game.state().endPlayerInput();
					else gameStateMessage = "You may trash 1 more card from your discard pile.";
				}
				else { }
			}
			//	when extra button pressed, end the state
			public void onExtraButtonPressed() {
				game.state().endPlayerInput();
			}
			public void onPlayerInputFinish() {
				if (discardZone != null) discardZone.deactivate();
				if (actionButton != null) actionButton.deactivate();
			}
		}
		
		public void playCard(RECard card, Game game, Player actingPlayer) {
			game.state().startPlayerInput(new ShatteredMemoriesState(game, actingPlayer));
		}
	}

	public static class ReloadEffect implements OnPlayListener {
		public static class ReloadState extends PlayerInputState {
			private REDeckViewZone discardZone;
			public ReloadState (Game game, Player actingPlayer) {
				super(game, actingPlayer);
			}
			public void onPlayerInputStart() {
				//	if discard pile contains no weapons, immediately end state for lack of possible actions
				if (actingPlayer.discard().queryType(CardType.Weapon).size() <= 0) game.state().endPlayerInput();
				else {
					discardZone = (REDeckViewZone) REZoneManager.getREZoneManager().getZone("discard_view");
					discardZone.activate();
					actionButton.activate();
					actionButton.buttonText = "NONE";
					gameStateMessage = "You may return a weapon from your discard pile to your hand.";
				}
			}
			public boolean isSelectable (REDeck source, int index) throws IndexOutOfBoundsException {
				return source == actingPlayer.discard() && ((RECard) source.peek(index)).getCardType() == CardType.Weapon;
			}
			public void onCardSelected(REDeck source, int index) {
				if (isSelectable(source, index)) {
					actingPlayer.hand().addBack((RECard) source.pop(index));
					game.state().endPlayerInput();
				}
				else { }
			}
			//	when extra button pressed, end the state
			public void onExtraButtonPressed() {
				game.state().endPlayerInput();
			}
			public void onPlayerInputFinish() {
				if (discardZone != null) discardZone.deactivate();
				if (actionButton != null) actionButton.deactivate();
			}
		}
		
		public void playCard(RECard card, Game game, Player actingPlayer) {
			game.state().startPlayerInput(new ReloadState(game, actingPlayer));
		}
	}

	public static class UmbrellaCorporationEffect implements OnPlayListener {
		public static class GetCardFromHandState extends PlayerInputState {
			public GetCardFromHandState (Game game, Player actingPlayer) { super(game, actingPlayer); }
			public void onPlayerInputStart() {
				//	if hand size == 0, immediately end state for lack of possible actions
				if (actingPlayer.hand().size() <= 0) game.state().endPlayerInput();
			}
			public boolean isSelectable (REDeck source, int index) { return source == actingPlayer.hand(); }
			public void onCardSelected(REDeck source, int index) throws IndexOutOfBoundsException {
				if (isSelectable(source, index)) {
					actingPlayer.deck().addTop(source.pop(index));
					game.state().endPlayerInput();
				}
				else { }
			}
			public void onExtraButtonPressed() { }
			public void onPlayerInputFinish() { }
		}
		
		public void playCard(RECard card, Game game, Player actingPlayer) {
			game.state().startPlayerInput(new GetCardFromHandState(game, actingPlayer));
		}
		public void finish(RECard card, Game game, Player actingPlayer) { actingPlayer.inPlay().addBack(card); }
	}

	public static class TrashOnFinish implements OnFinishListener {
		public void finish(RECard card, Game game, Player actingPlayer) {
			game.shop().returnCard(card);
		}
	}
	
	public static class TrashOnFinishComp extends CardComp {
		public TrashOnFinishComp() { super(RECard.HAND_FINISH); }
		public void execute() {
			Game.getGame().shop().returnCard((RECard) parent);
		}
	}

	//	not implemented
	public static class BackToBackEffect implements OnPlayListener {
		public void playCard(RECard card, Game game, Player actingPlayer) {
			
		}
	}
	
	//	not tested
	public static class BackToBackTrigger implements OnTriggerListener {
		public boolean isTriggered(RECard card, Game game, Player actingPlayer) {
			//	your character is attacked
			return game.defendingPlayer() != null && game.defendingPlayer() == actingPlayer;
		}
	}

	//	not fully implemented
	//	TODO: add dialog for gaining an ammunition card from the shop
	public static class ItemManagementEffect implements OnPlayListener {
		public static class ItemManagementState extends PlayerInputState {
			public ItemManagementState (Game game, Player actingPlayer) {
				super(game, actingPlayer);
				gameStateMessage = "You must trash an ammunition card from your hand.";
			}
			public void onPlayerInputStart() {
				//	if hand contains no ammunition, immediately end state for lack of possible actions
				if (actingPlayer.hand().queryType(CardType.Ammunition).size() <= 0) game.state().endPlayerInput();
			}
			public boolean isSelectable (REDeck source, int index) throws IndexOutOfBoundsException {
				return source == actingPlayer.hand() && ((RECard) source.peek(index)).getCardType() == CardType.Ammunition;
			}
			public void onCardSelected(REDeck source, int index) {
				if (isSelectable(source, index)) {
					game.shop().returnCard((RECard) source.pop(index));
					game.state().endPlayerInput();
				}
				else { }
			}
			public void onExtraButtonPressed() { }
			public void onPlayerInputFinish() { }
		}
		
		public void playCard(RECard card, Game game, Player actingPlayer) {
			game.state().startPlayerInput(new ItemManagementState(game, actingPlayer));
		}
	}
	
	public static class OminousBattleEffect implements OnPlayListener {
		public static class OminousBattleState extends PlayerInputState {
			public OminousBattleState (Game game, Player actingPlayer) {
				super(game, actingPlayer);
				gameStateMessage = "You must trash a card from your hand.";
			}
			public void onPlayerInputStart() {
				//	if hand contains no cards, immediately end state for lack of possible actions
				if (actingPlayer.hand().size() <= 0) game.state().endPlayerInput();
			}
			public boolean isSelectable (REDeck source, int index) throws IndexOutOfBoundsException {
				return source == actingPlayer.hand();
			}
			public void onCardSelected(REDeck source, int index) {
				if (isSelectable(source, index)) {
					game.shop().returnCard((RECard) source.pop(index));
					game.state().endPlayerInput();
				}
				else { }
			}
			public void onExtraButtonPressed() { }
			public void onPlayerInputFinish() { }
		}
		
		public void playCard(RECard card, Game game, Player actingPlayer) {
			game.state().startPlayerInput(new OminousBattleState(game, actingPlayer));
		}
		public void finish(RECard card, Game game, Player actingPlayer) { actingPlayer.inPlay().addBack(card); }
	}
	
	//	not implemented
	public static class MasterOfUnlockingEffect implements OnPlayListener {
		public void playCard(RECard card, Game game, Player actingPlayer) {
			
		}
	}
	
	//	not implemented
	public static class StruggleForSurvivalEffect implements OnPlayListener {
		public void playCard(RECard card, Game game, Player actingPlayer) {
			
		}
	}
	
	public static class StruggleForSurvivalTrigger implements OnTriggerListener {
		public boolean isTriggered(RECard card, Game game, Player actingPlayer) {
			//	triggers during a combat phase that isn't yours
			return game.state().currentState() == State.ExploreRespond && !game.isActivePlayer(actingPlayer);
		}
	}
	
	//endregion
	
	//region Item and Token Effects
	
	public static class GreenHerbPlayed extends CardComp {
		public GreenHerbPlayed() { super(RECard.HAND_PLAY); }
		public static class AnotherHerbState extends PlayerInputState {
			//	duuuuude...  there's two Krausers...  hand me some more a' that Green Herb...
			private int healAmount;
			public AnotherHerbState (Game game, Player actingPlayer) {
				super(game, actingPlayer);
				healAmount = 20;	//	minimum heal amount is 20
			}
			public void onPlayerInputStart() {
				//	if discard pile contains no other Green Herbs, immediately end state for lack of possible actions
				if (actingPlayer.hand().indexOf("IT;1") == -1) game.state().endPlayerInput();
				else {
					actionButton.activate();
					actionButton.buttonText = "NONE";
					gameStateMessage = "You may trash another Green Herb card from your hand to heal 60 instead of 20.";
				}
			}
			public boolean isSelectable (REDeck source, int index) throws IndexOutOfBoundsException {
				return source == actingPlayer.hand() && ((RECard) source.peek(index)).getName().equalsIgnoreCase("Green Herb");
			}
			public void onCardSelected(REDeck source, int index) {
				if (isSelectable(source, index)) {
					//	trash the second herb and heal health by an additional 40
					game.shop().returnCard((RECard)source.pop(index));
					healAmount += 40;
					game.state().endPlayerInput();
				}
				else { }
			}
			//	when extra button pressed, end the state
			public void onExtraButtonPressed() {
				game.state().endPlayerInput();
			}
			public void onPlayerInputFinish() {
				actingPlayer.changeHealth(healAmount, true);
				if (actionButton != null) actionButton.deactivate();
			}
		}
		public void execute() {
			Game game = Game.getGame();
			game.state().startPlayerInput(new AnotherHerbState(game, game.getActivePlayer()));
		}
	}
	
	public static class YellowHerbRevealed extends CardComp {
		public YellowHerbRevealed() { super(RECard.MANSION_REVEAL); }
		public void execute() {
			Player p = Game.getGame().attackingPlayers().get(0);
			p.attachedCards().addBack(parent);
			p.changeMaxHealth(10);
			p.changeHealth(10, true);
		}
	}
	
	public static class GGunCaseRevealed extends CardComp {
		public GGunCaseRevealed() { super(RECard.MANSION_REVEAL); }
		public void execute() {
			Game game = Game.getGame();
			RECard temp = game.shop().gainCardSearch(game.attackingPlayers().get(0), "WE09");
			if (temp == null) game.attackingPlayers().get(0).discard().addTop(CardData.getCardData().getCard("WE;09"));
			else game.attackingPlayers().get(0).discard().addTop(temp);
			game.mansionRemoved().addTop(parent);
		}
	}
	
	public static class RocketCaseRevealed extends CardComp {
		public RocketCaseRevealed() { super(RECard.MANSION_REVEAL); }
		public void execute() {
			Game game = Game.getGame();
			RECard temp = game.shop().gainCardSearch(game.attackingPlayers().get(0), "WE10");
			if (temp == null) game.attackingPlayers().get(0).discard().addTop(CardData.getCardData().getCard("WE;10"));
			else game.attackingPlayers().get(0).discard().addTop(temp);
			game.mansionRemoved().addTop(parent);
		}
	}
	
	public static class FirstAidSprayPlayed extends CardComp {
		public FirstAidSprayPlayed() { super(RECard.HAND_PLAY); }
		public void execute() {
			Game.getGame().getActivePlayer().changeHealth(9999, true);
		}
	}
	
	//endregion
	
	//region Weapon Effects
	
	public static class SurvivalKnifeEffect implements OnPlayListener {
		public void playCard(RECard card, Game game, Player actingPlayer) {
			if (card instanceof WeaponCard)
				game.exploreEffects().add(new ExploreEffect(game, actingPlayer, new SurvivalKnifeBuff((WeaponCard)card)));
		}
	}
	
	public static class BurstFireHandgunEffect implements OnPlayListener {
		public void playCard(RECard card, Game game, Player actingPlayer) {
			if (card instanceof WeaponCard)
				game.exploreEffects().add(new ExploreEffect(game, actingPlayer, new BurstFireHandgunBuff((WeaponCard)card)));
		}
	}
	
	//	"Story Mode or Mercenary Mode:  Deal 5 additional damage to each adjacent Player to the Attacking Player.
	//	Versus Mode:  Deal an additional 5 Damage to each adjacent Player to the Attacked Player."
	public static class GrenadeEffect implements OnPlayListener {
		public void playCard(RECard card, Game game, Player actingPlayer) {
			int middle = 0, left, right;
			
			//	determine middle player
			switch (game.gameMode()) {
			case Story:
			case Mercenary:
			case Outbreak:
			case PartnerStory:
				middle = game.activePlayer();
				break;
			case Versus:
				middle = game.defendingPlayer().playerId();
				break;
			}
			
			//	determine left and right players and inflict damage to them
			left = game.playerBefore(middle);
			right = game.playerAfter(middle);
			if (left != middle) game.getPlayer(left).changeHealth(5, true);
			if (right != middle && left != right) game.getPlayer(right).changeHealth(5, true);
		}
	}

	//	"Story Mode or Mercenary Mode:  This Weapon gets +5 Damage while your Character's Health is 80 or higher.
	//	Versus Mode:  Attacked Player must discard a Weapon from their hand."
	//	not fully implemented
	//	TODO: implement versus effect
	public static class SubmissionEffect implements OnPlayListener {
		public void playCard(RECard card, Game game, Player actingPlayer) {
			switch (game.gameMode()) {
			case Story:
			case Mercenary:
			case Outbreak:
			case PartnerStory:
				if (actingPlayer.health() >= 80) ((WeaponCard)card).damageThisRound += 5;
				break;
			case Versus:
				break;
			}
		}
	}
	
	//	"When Trashed, shuffle the the "Rocket Launcher Case" Token into the Mansion."
	//	not fully implemented
	public static class RocketLauncherEffect implements OnPlayListener {
		public void playCard(RECard card, Game game, Player actingPlayer) {
			
		}
	}
	
	//endregion
}
