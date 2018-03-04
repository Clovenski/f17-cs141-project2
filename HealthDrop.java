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
 * This class represents the item drop that gives health to the player.
 * It is also designed to be able to give hit points to other entities other than the player.
 * @author Joel Tengco
 *
 */
public class HealthDrop extends ItemDrops {
	/**
	 * Creates a new object of type {@code HealthDrop} with the name: "Health Drop" and description: "Gain 5 hit points.".
	 */
	public HealthDrop() {
		super("Health Drop", "Gain 5 hit points.");
	}
	
	/**
	 * Replenishes 5 hit points to the given {@code target} parameter.
	 * @param target the target {@code ActiveAgents} object to gain 5 hit points
	 */
	public void giveHealth(ActiveAgents target) {
		target.gainHealth(5);
	}
}
