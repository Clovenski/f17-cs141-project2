/**
 * CS 141: Intro to Programming and Problem Solving
 * Professor: Edwin Rodr&iacute;guez
 *
 * Programming Assignment #2
 *
 * This assignment involves creating a game where the user has ten steps to escape a dungeon
 * where each step has a chance for an enemy to spawn and block their way. If or when that happens,
 * a turn-based game involving guns ensues. If the player manages to reach the exit without dying, they win.
 * 
 * Joel Tengco
 */
package edu.cpp.cs.cs141.prog_assgmnt_2;

import java.util.Random;

/**
 * This class is the engine of the game. It holds all the vital information to keep the game advancing.
 * It mainly utilizes the classes {@link ActiveAgents} and {@link Gun} to form the game.
 * @author Joel Tengco
 *
 */
public class GameEngine {
	/**
	 * Represents the amount of steps left for the player to take before reaching the exit.
	 */
	private int stepsLeft;
	/**
	 * Object to hold the player entity.
	 */
	private ActiveAgents player;
	/**
	 * Object to hold all the enemy entities, if any.
	 */
	private ActiveAgents enemy;
	/**
	 * The random number generator for this game.
	 */
	private Random rng;
	/**
	 * An array to hold the {@code Gun} object models for this game.
	 */
	private Gun[] weapons;
	
	/**
	 * Creates a new game with 10 as the default amount of steps needed to take to reach the exit and win the game.
	 */
	public GameEngine() {
		setUpEngine(10);
	}
	
	/**
	 * Creates a new game with the number of steps needed to take to reach the exit and win the game
	 * with the given argument. Zero and negative values will default to 10 steps.
	 * @param numOfSteps the number of steps separating the player and the exit at the start of the game
	 */
	public GameEngine(int numOfSteps) {
		if(numOfSteps <= 0)
			setUpEngine(10);
		else
			setUpEngine(numOfSteps);
	}
	
	/**
	 * This method is used by the constructors to initialize the fields of this game engine the appropriate values.
	 * This method is where the three types of guns are created: pistol, rifle, and shotgun, in that respective order.
	 * @param length the length of the dungeon, in other words the number of steps left to take before reaching the exit
	 */
	private void setUpEngine(int length) {
		stepsLeft = length;
		rng = new Random();
		weapons = new Gun[3];
		weapons[0] = new Gun("pistol", 15, 75, 1);
		weapons[1] = new Gun("rifle", 10, 65, 2);
		weapons[2] = new Gun("shotgun", 5, 40, 5);
	}
	
	/**
	 * Gets the gun that represents option n, with n being the argument given.
	 * Use this method in conjunction with {@linkplain #getNumOfWeapons()} to access all of the options.
	 * This method is generally used for the purpose of displaying the properties of the gun choices this game engine
	 * has created. For the argument given, start with 1 to get the first option, 2 for the second, etc.
	 * @param optionNum the number of the option desired to get from the game engine
	 * @return a copy of the {@code Gun} model this game engine created at the specified option number
	 */
	public Gun getWeaponOptNum(int optionNum) {
		return weapons[optionNum].getCopy();
	}
	
	/**
	 * Gets the number of weapons that this game engine has created as options for the player to choose from.
	 * @return the number of weapons the player can choose from
	 */
	public int getNumOfWeapons() {
		return weapons.length;
	}
	
	/**
	 * Sets up the player entity of the game, expecting an integer that represents the chosen weapon, and creates
	 * a new {@code ActiveAgents} object, specified as the player, with the specified gun as its weapon.
	 * This method expects arguments within [0, 2], and any other value will default the chosen weapon to an integer
	 * of 0
	 * @param chosenWeapon a number n representing the chosen weapon at option number n + 1
	 */
	public void setUpPlayer(int chosenWeapon) {
		switch(chosenWeapon) {
			case 0: player = new ActiveAgents(true, weapons[0].getCopy());
					break;
			case 1: player = new ActiveAgents(true, weapons[1].getCopy());
					break;
			case 2: player = new ActiveAgents(true, weapons[2].getCopy());
					break;
			default: player = new ActiveAgents(true, weapons[0].getCopy());
		}
	}
	
	/**
	 * Advances the player one step towards the exit.
	 */
	public void stepForward() {
		stepsLeft = stepsLeft - 1;
	}

	/**
	 * Retreats the player one step backwards, effectively making the player lose progress towards the exit.
	 */
	public void stepBack() {
		stepsLeft = stepsLeft + 1;
	}

	/**
	 * Gets the number of steps left for the player to take to win the game.
	 * @return a string containing "n steps" where n is the number of steps left
	 */
	public String getStepsLeft() {
		if(stepsLeft == 1)
			return stepsLeft + " step";
		else
			return stepsLeft + " steps";
	}

	/**
	 * Determines if there is an enemy present at this current step.
	 * Use of this method in conjunction with {@linkplain #setUpEnemy()} is recommended.
	 * @return true if there is an encounter with an enemy at this step, false otherwise
	 */
	public boolean enemyExists() {
		return rng.nextInt(100) < 15;
	}
	
	/**
	 * Sets up the enemy entity to oppose the player. Enemies are equipped with a random
	 * weapon from the same list of options the player chose from. Use of this method after a
	 * true value returned from method {@linkplain #enemyExists()} is recommended.
	 */
	public void setUpEnemy() {
		int randNum = rng.nextInt(100);
		
		if(randNum >= 50)
			enemy = new ActiveAgents(false, weapons[0].getCopy());
		else if(randNum >= 15)
			enemy = new ActiveAgents(false, weapons[1].getCopy());
		else
			enemy = new ActiveAgents(false, weapons[2].getCopy());
	}
	
	/**
	 * Determines if the player has escaped the enemy successfully or not.
	 * @return true if the player has escaped the enemy, false otherwise
	 */
	public boolean playerEscaped() {
		if(rng.nextInt(100) < 10) {
			enemy = null;
			return true;
		} else
			return false;
	}

	/**
	 * This method models the turn-based property of this game. It takes in
	 * a boolean value representing whether or not it is the player's turn.
	 * It then calculates the damage done to the target and applies that effect, if any.
	 * @param isPlayerShooting true if it the player this is the one shooting, false if it is the enemy
	 */
	public void shootTarget(boolean isPlayerShooting) {
		ActiveAgents shooter;
		ActiveAgents target;
		Gun gunUsed;
		int randNum;
		
		if(isPlayerShooting) {
			shooter = player;
			target = enemy;
		} else {
			shooter = enemy;
			target = player;
		}
		gunUsed = shooter.getGun();
		
		if(gunUsed.getCurrentAmmo() == 0)
			return;
		gunUsed.useAmmo();
		
		randNum = rng.nextInt(100);
		if(randNum < gunUsed.getChanceToHit())
			target.loseHealth(gunUsed.getDamage());
	}
	
	/**
	 * Gets an object containing all the current statistics of the player, at the time of invocation and only at that time.
	 * @return an {@code ActiveAgents} object duplicate of the player
	 */
	public ActiveAgents getPlayerStats() {
		return player.getCopy();
	}

	/**
	 * Gets an object containing all the current statistics of the enemy, at the time of invocation and only at that time.
	 * @return an {@code ActiveAgents} object duplicate of the enemy
	 */
	public ActiveAgents getEnemyStats() {
		return enemy.getCopy();
	}

	/**
	 * Determines if the current battle between the player and an enemy is over.
	 * @return true if the player or enemy is not alive or there is no enemy present, false otherwise
	 */
	public boolean isBattleOver() {
		return enemy == null || !enemy.isAlive() || !player.isAlive();
	}
	
	/**
	 * Randomly picks an item drop, applies its effect, and returns a string containing that effect.
	 * @return the effect of the randomly picked item drop
	 */
	public String getItemDrop() {
		ItemDrops item;
		
		if(rng.nextInt(100) < 30) {
			item = new HealthDrop();
			((HealthDrop) item).giveHealth(player);
		} else {
			item = new AmmoDrop();
			((AmmoDrop) item).giveAmmo(player);
		}
		return item.toString();
	}

	/**
	 * Determines if the player is alive or not.
	 * @return true if the player has hit points greater than zero, false otherwise
	 */
	public boolean isPlayerAlive() {
		return player.isAlive();
	}

	/**
	 * Determines if the player has reached the dungeon exit or not.
	 * @return true if the number of steps left is zero, false otherwise
	 */
	public boolean reachedExit() {
		return stepsLeft == 0;
	}
	
	/**
	 * Determines if the game itself is done or not.
	 * @return true when the player is no longer alive or the player has zero steps left to take to reach the exit
	 */
	public boolean isDone() {
		return !player.isAlive() || stepsLeft == 0;
	}
}
