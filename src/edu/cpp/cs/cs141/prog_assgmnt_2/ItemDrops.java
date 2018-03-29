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
 * This class lays the foundation of item drops for the game. Item drops for this game
 * will include a name and a description to be utilized by the game engine.
 * @author Joel Tengco
 *
 */
public class ItemDrops {
	/**
	 * This represented the name of the drop meant to be printed onto the screen.
	 */
	private String itemName;
	/**
	 * Outputting this onto the screen shows the main purpose of the item drop.
	 */
	private String itemDesc;
	
	/**
	 * Creates a new {@code ItemDrops} object with the given name and description of the item drop.
	 * @param name a string to represent the name of this item drop
	 * @param description a string representing the main purpose of what the item drop does
	 */
	public ItemDrops(String name, String description) {
		itemName = name;
		itemDesc = description;
	}
	
	/**
	 * Gets the name of this item drop.
	 * @return the name of the item drop as it was when it was created
	 */
	public String getName() {
		return itemName;
	}
	
	/**
	 * Gets the description of this item drop.
	 * @return a string containing the main purpose of this item drop
	 */
	public String getDesc() {
		return itemDesc;
	}
	
	/**
	 * Gets a string containing the item name and description in the format: "name: description".
	 * @return a string containing the item name along with its description
	 */
	public String toString() {
		return itemName + ": " + itemDesc;
	}
}
