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

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class acts as the interface for the user to interact with. It handles the direction of where
 * the game goes in terms of execution. It directly communicates with the game engine to advance the game.
 * <p>
 * It handles all of the prompts for the user as well as any exceptions
 * that the user can input. The start of the game is implemented here with the {@linkplain #start()} method.
 * @author Joel Tengco
 *
 */
public class UserInterface {
	/**
	 * Holds the reference to the object containing the game engine.
	 */
	private GameEngine game;
	/**
	 * Used entirely for capturing user input.
	 */
	private Scanner input;
	/**
	 * The main tool used to help handle the majority of input exceptions.
	 */
	private StringTokenizer tokenizer;
	
	/**
	 * Creates a new {@code UserInterface} object ready to start. Please use {@link #start()} to start the game as this constructor does not do so.
	 */
	public UserInterface() {}
	
	/**
	 * Key method to start the game. It introduces the name of the game and its brief description, sets up the game and starts it.
	 */
	public void start() {
		game = new GameEngine();
		input = new Scanner(System.in);
		
		System.out.println("Hello and welcome to TEN STEPS TO ESCAPE THE DUNGEON."
				+ "\n\tby: Joel Tengco\n");
		
		System.out.println("You wake up in a dark dungeon and realize you need to get out. You see what it looks like to be a door in the vicinity. "
							+ "\nIt seems to be 10 steps away from you. You hear ominous noises around you in the dark. You are quick to reach for "
							+ "a weapon on the ground. \nWhat weapon should you reach for?\n");
		setUpGame();
		startGame();
	}
	
	/**
	 * This method is used to set up the game, as in prompting the user for a valid input for determining which gun to be used throughout the game.
	 */
	private void setUpGame() {
		int chosenWeapon;
		
		System.out.println("You cannot change your weapon throughout the game so choose wisely. . .");
		System.out.println("Please choose your weapon: Enter 1 for the first weapon, "
							+ "2 for the second weapon, and so forth.\n");
		displayWeaponChoices();
		do {
			try {
				System.out.print("> ");
				tokenizer = new StringTokenizer(input.nextLine());
				chosenWeapon = Integer.parseInt(tokenizer.nextToken()) - 1;
				if(chosenWeapon < 0 || chosenWeapon >= game.getNumOfWeapons())
					throw new IllegalArgumentException();
			} catch(IllegalArgumentException iae) {
				System.err.println("Error: please enter an integer within [1, " + game.getNumOfWeapons() + "]");
				chosenWeapon = -1;
			} catch(Exception e) {
				System.err.println("Error, please give a valid input.");
				chosenWeapon = -1;
			}
		} while(chosenWeapon == -1);
		
		game.setUpPlayer(chosenWeapon);
		System.out.println("Wow, a " + game.getPlayerStats().getGun().getName() + ". . .\n"
				+ " Good luck!\n");
	}
	
	/**
	 * This is used to display all of the choices for the user to choose from in picking which gun to use throughout the game.
	 * It lists out the options in the format for example:
	 * <p>
	 * 1. Pistol
	 * <p>
	 * Max Ammo: 15 Accuracy: 75% Damage: 1
	 */
	private void displayWeaponChoices() {
		Gun gunOption;
		
		for(int i = 0; i < game.getNumOfWeapons(); i++) {
			gunOption = game.getWeaponOptNum(i);
			
			System.out.println((i + 1) + ". " + gunOption.toString() + ": \n\tMax Ammo: "
								+ gunOption.getMaxAmmo() + " Accuracy: "
								+ gunOption.getChanceToHit() + "% Damage: "
								+ gunOption.getDamage() + "\n");
		}
	}

	/**
	 * Once the game is properly set up, this method is called to introduce the main game loop.
	 * <p>
	 * The loop consists of stepping forward, determining if there is an enemy present and handling the
	 * event in which there is one, all towards progressing the player towards the exit to the dungeon
	 * <p>
	 * It executes the game loop until the game engine determines the game is over, and properly calls
	 * the method {@linkplain #endGame(boolean)} with the appropriate boolean value depending on the game
	 * results.
	 */
	private void startGame() {
		int userInput;
		
		System.out.println("You are currently " + game.getStepsLeft() + " away from the exit!");
		
		do {
			System.out.println("Press [ENTER] to take a step forward towards the exit.");
			input.nextLine();
			game.stepForward();
			
			if(game.enemyExists()) {
				game.setUpEnemy();
				System.out.println("There is an enemy with a " + game.getEnemyStats().getGun().getName() + " in front of you!");
				displayGameStats();
				System.out.println("Will you fight it or try to escape? Enter 0 to try to escape or 1 to fight.");
				
				userInput = getUserInput();
				
				if(userInput == 1)
					startBattle(true);
				else if(game.playerEscaped()) {
					game.stepBack();
					System.out.println("You successfully escaped the enemy, but are now "
										+ game.getStepsLeft() + " away from the exit.");
				} else {
					System.out.println("The enemy blocks your way and you are unable to escape!\n");
					startBattle(false);
				} // end of if-else regarding userInput being 1 || 0
				
			} else { // there is no enemy present, game.enemyExists() == false
				System.out.println("You hear ominous noises but nothing is blocking your way towards the exit!");
				System.out.println("You are now " + game.getStepsLeft() + " away from the exit!");
			}
		} while(!game.isDone());
		
		System.out.println();
		if(game.isPlayerAlive() && game.reachedExit())
			endGame(true);
		else
			endGame(false);
	}
	
	/**
	 * This is the method that mainly handles the input exception within the main game loop.
	 * <p>
	 * It accepts all of what the user inputs before the {@code ENTER} key is pressed, and utilizes a {@code StringTokenizer}
	 * to focus only on the first token in the series, catching any exceptions that can be thrown and will not return until
	 * a valid input of 0 or 1 is given.
	 * @return either 0 or 1 depending on the input of the user
	 */
	private int getUserInput() {
		int userInput;
		
		do {
			try {
				System.out.print("> ");
				tokenizer = new StringTokenizer(input.nextLine());
				userInput = Integer.parseInt(tokenizer.nextToken());
				if(userInput < 0 || userInput > 1)
					throw new IllegalArgumentException();
			} catch(IllegalArgumentException iae) {
				System.err.println("Error: please enter either 0 or 1.");
				userInput = -1;
			} catch(Exception e) {
				System.err.println("Error: please enter a valid input.");
				userInput = -1;
			}
		} while(userInput == -1);	// any invalid input will make this statement true
		return userInput;			// therefore the returned value will always be either 0 or 1
	}

	/**
	 * This method is used when starting a battle with an enemy that the player has encountered.
	 * It handles the whole process of a battle before returning to the main game loop.
	 * @param playerStartsFirst true if the player starts first in the battle, false if the enemy does
	 */
	private void startBattle(boolean playerStartsFirst) {
		boolean isPlayersTurn;
		int userInput;
		
		if(playerStartsFirst) {
			advanceTurn(true);
			if(game.isBattleOver()) {			// player one-shot-killed the enemy in one turn
				System.out.println("Press [ENTER] to continue.");
				input.nextLine();
				awardPlayer();
				return;
			}
			isPlayersTurn = !playerStartsFirst;
		} else
			isPlayersTurn = playerStartsFirst;

		do {
			if(isPlayersTurn) {
				displayGameStats();
				System.out.println("Enter 0 to try to escape or 1 to continue fighting.");
				
				userInput = getUserInput();
				if(userInput == 1)
					advanceTurn(true);
				else {						// player wants to try to escape
					if(game.playerEscaped()) {
						game.stepBack();
						System.out.println("You successfully escaped the enemy, but are now "
											+ game.getStepsLeft() + " away from the exit.");
						return;
					} else {
						System.out.println("The enemy blocks your way and you are unable to escape!\n");
					}
				}
			} else	// it is the enemy's turn
				advanceTurn(false);
			
			isPlayersTurn = !isPlayersTurn;
		} while(!game.isBattleOver());
		
		System.out.println("Press [ENTER] to continue.");
		input.nextLine();
		
		if(game.isPlayerAlive())
			awardPlayer();
	}
	
	/**
	 * Displays the current game statistics, including both player and enemy health, ammo, and how much damage their respective guns inflict.
	 * <p>
	 * The general format in which the statistics are printed is:
	 * <p>
	 * Player Health: n		Player Ammo: n		g: inflicts n damage
	 * <p>
	 * Enemy Health:  n		Enemy Ammo:  n		g: inflicts n damage
	 */
	private void displayGameStats() {
		int playerHealth = game.getPlayerStats().getHealth();
		Gun playerGun = game.getPlayerStats().getGun();
		int enemyHealth = game.getEnemyStats().getHealth();
		Gun enemyGun = game.getEnemyStats().getGun();
	
		// displays game statistics in a certain format for better alignment, for example:
		// Player Health: n		Player Ammo: n		g: inflicts n damage
		// Enemy Health:  n		Enemy Ammo:  n		g: inflicts n damage
		System.out.printf("%-15s%-10d%-13s%-10d%10s%s\n", "Player Health: ", playerHealth, "Player Ammo: ", playerGun.getCurrentAmmo(),
							playerGun.toString() + ": ", "inflicts " + playerGun.getDamage() + " damage");
		
		System.out.printf("%-15s%-10d%-13s%-10d%10s%s\n", "Enemy Health: ", enemyHealth, "Enemy Ammo: ", enemyGun.getCurrentAmmo(),
				enemyGun.toString() + ": ", "inflicts " + enemyGun.getDamage() + " damage");
		
		System.out.println();
	}

	/**
	 * This method implements the turn-based property of the game, handling the output to properly match
	 * the statistics given from the game engine.
	 * <p>
	 * The output of this method, as well as the game's advancement. heavily relies on the given argument.
	 * @param isPlayersTurn true if the game engine was to process the turn of the player, false for the turn of the enemy
	 */
	private void advanceTurn(boolean isPlayersTurn) {
		Gun playerGun = game.getPlayerStats().getGun();
		Gun enemyGun = game.getEnemyStats().getGun();
		
		if(isPlayersTurn) {
			System.out.println("You aim at the enemy with your " + playerGun.getName() + ".");
			System.out.println("Press [ENTER] to shoot!!");
			input.nextLine();
			
			if(playerGun.isEmpty()) {
				System.out.println("As you pull the trigger, you hear a soft click and see the enemy just smirk at you.\n");
				return;
			}
			
			if(targetHit(true))
				System.out.println("HIT for " + playerGun.getDamage() + " damage!\n");
			else
				System.out.println("MISS!\n");
		} else {
			System.out.println("The enemy is aiming at you with its " + enemyGun.getName() + ".");
			System.out.println("Press [ENTER] to try to dodge!!");
			input.nextLine();
			
			if(enemyGun.isEmpty()) {
				System.out.println("You hear a loud click, and you cannot help but let out a chuckle while your enemy is drenched in sweat.\n");
				return;
			}
			
			if(targetHit(false))
				System.out.println("HIT for " + enemyGun.getDamage() + " damage!\n");
			else
				System.out.println("MISS!\n");
		}
	}

	/**
	 * This method determined whether or not the gun used in method {@linkplain #advanceTurn(boolean)} hit its target.
	 * It essentially checks if there was a change at all in the player's or enemy's health after invoking the game engine
	 * to process the shot.
	 * @param isEnemyTarget true if the enemy is the target, false if the player is the target
	 * @return true if there was a change in health in the target, false if there was none
	 */
	private boolean targetHit(boolean isEnemyTarget) {
		int targetOldHealth;
		boolean result;
		
		if(isEnemyTarget) {
			targetOldHealth = game.getEnemyStats().getHealth();
			game.shootTarget(true);
			result = game.getEnemyStats().getHealth() < targetOldHealth;
		} else {
			targetOldHealth = game.getPlayerStats().getHealth();
			game.shootTarget(false);
			result = game.getPlayerStats().getHealth() < targetOldHealth;
		}
		return result;
	}

	/**
	 * This method awards the player with a random item drop, and is only used when the player has defeated an enemy.
	 * It properly notifies the user of the effect after the item drop has affected the statistics in the game engine.
	 */
	private void awardPlayer() {
		System.out.println("ENEMY DEFEATED!");
		System.out.println("\nAs your enemy dissolves into a pile of dust, you notice a glowing object within it.");
		
		System.out.println("Press [ENTER] to pick it up.");
		input.nextLine();
		
		System.out.println("ITEM GET! " + game.getItemDrop());
		System.out.println();
		System.out.println("You are now " + game.getStepsLeft() + " away from the exit!");
		
	}

	/**
	 * Properly ends the game with the appropriate message depending on the argument given.
	 * @param playerWon true if the player has won the game, false if the player has died during the game
	 */
	private void endGame(boolean playerWon) {
		if(playerWon)
			System.out.println("CONGRATS, YOU HAVE ESCAPED THE DUNGEON!!");
		else
			System.out.println("GAME OVER! You were " + game.getStepsLeft() + " away from the exit.");
		
		System.out.println("Thank you for playing!");
	}
}
