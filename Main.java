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

/**
 * This class is the driver for the Escape the Dungeon Game. It creates a new UserInterface object and invokes its
 * {@linkplain UserInterface#start()} method to start the game.
 * @author Joel Tengco
 *
 */
public class Main {

	/**
	 * Entry point for the program.
	 * @param args not used for the driver
	 */
	public static void main(String[] args) {
		UserInterface newGame = new UserInterface();
		newGame.start();
	}

}
