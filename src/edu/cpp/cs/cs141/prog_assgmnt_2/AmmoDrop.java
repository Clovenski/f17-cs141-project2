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
 * This class represents the item drop that replenishes the gun of the player to max ammo.
 * It is also designed to be able to replenish ammo of a gun of other entities other than the player.
 * @author Joel Tengco
 *
 */
public class AmmoDrop extends ItemDrops {
	/**
	 * Creates a new object of type {@code AmmoDrop} with the name: "Ammo Drop" and the description: "Refill gun to full ammo.".
	 */
	public AmmoDrop() {
		super("Ammo Drop", "Refill gun to full ammo.");
	}
	
	/**
	 * Replenishes the gun of the given {@code target} parameter to max ammo.
	 * @param target the target object of type {@code ActiveAgents} to be given max ammo
	 */
	public void giveAmmo(ActiveAgents target) {
		target.getGun().replenishAmmo();
	}
}
