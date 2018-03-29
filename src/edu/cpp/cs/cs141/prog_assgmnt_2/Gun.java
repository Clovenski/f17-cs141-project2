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
 * The Gun class represents the basic properties of a gun.
 * These properties include the name of the gun, its accuracy,
 * the damage it inflicts, its maximum amount of ammo and the amount
 * of ammo currently in it.
 * @author Joel Tengco
 *
 */
public class Gun {
	/**
	 * This basically identifies the gun.
	 */
	private String gunName;
	/**
	 * This was the percentage of the likelihood of the gun hitting the target.
	 */
	private int chanceToHit;
	/**
	 * This represented the amount of damage this gun inflicts, as a unit of hit points.
	 */
	private int damage;
	/**
	 * This holds the maximum amount of ammo that this gun can have.
	 */
	private int maxAmmo;
	/**
	 * This represented the amount of ammo currently inside this gun during this object's lifetime.
	 */
	private int currentAmmo;
	
	/**
	 * Creates a new object of type {@code Gun} with the given properties.
	 * Invalid arguments for the {@code chanceToHit} parameter will default to a 50% chance to hit its target.
	 * @param gunName the name to identify this gun as
	 * @param maxAmmo the maximum amount of ammo this gun can have
	 * @param chanceToHit the percentage chance this gun <i>will</i> hit its target, must be 0 - 100
	 * @param damage the amount of damage this gun can inflict, as a unit of hit points
	 */
	public Gun(String gunName, int maxAmmo, int chanceToHit, int damage) {
		this.gunName = gunName.toLowerCase();
		this.maxAmmo = maxAmmo;
		if(chanceToHit < 0 || chanceToHit > 100)
			this.chanceToHit = 50;
		else
			this.chanceToHit = chanceToHit;
		this.damage = damage;
		
		currentAmmo = maxAmmo;
	}
	
	/**
	 * Copy constructor to create a new {@code Gun} object with the same properties of the given {@code Gun} object.
	 * @param otherGun the gun containing the desired properties
	 */
	public Gun(Gun otherGun) {
		gunName = otherGun.gunName;
		maxAmmo = otherGun.maxAmmo;
		chanceToHit = otherGun.chanceToHit;
		damage = otherGun.damage;
		
		currentAmmo = otherGun.currentAmmo;
	}

	/**
	 * Gets the string that identifies this gun.
	 * @return the name of this gun
	 */
	public String getName() {
		return gunName;
	}
	
	/**
	 * Gets the chance this gun has to hit its target. Returns within the range [0, 100].
	 * @return an integer representing the percentage chance out of 100 to hit the target with this gun
	 */
	public int getChanceToHit() {
		return chanceToHit;
	}
	
	/**
	 * Gets the amount of damage this gun inflicts when hitting its target. Returns a positive integer.
	 * @return the amount of damage that should be taken from the target
	 */
	public int getDamage() {
		return damage;
	}
	
	/**
	 * Gets the current amount of ammo inside this gun. Returns an integer greater than or equal to zero.
	 * @return the number of ammo in reserve in this gun
	 */
	public int getCurrentAmmo() {
		return currentAmmo;
	}
	
	/**
	 * Gets the maximum amount of ammo this gun can hold.  Returns a positive integer.
	 * @return this gun's maximum amount of ammo
	 */
	public int getMaxAmmo() {
		return maxAmmo;
	}
	
	/**
	 * Represents the action of shooting this gun, decrementing its current amount of ammo once.
	 */
	public void useAmmo() {
		if(currentAmmo > 0)
			currentAmmo = currentAmmo - 1;
	}

	/**
	 * Determines if this gun currently has no ammo in it.
	 * @return true if this gun is currently empty, having no ammo in it; false otherwise
	 */
	public boolean isEmpty() {
		return currentAmmo == 0 ? true : false;
	}

	/**
	 * Represents the action of basically reloading this gun. The current amount of ammo in this gun
	 * replenishes back to max.
	 */
	public void replenishAmmo() {
		currentAmmo = maxAmmo;
	}
	
	/**
	 * Gets a copy of this {@code Gun} object.
	 * @return a reference to a {@code Gun} object containing the same properties as this gun
	 */
	public Gun getCopy() {
		Gun temp = new Gun(this);
		return temp;
	}

	/**
	 * Gets the name of this gun, making it capitalized if it isn't already.
	 * @return a capitalized string of the name of this gun
	 */
	public String toString() {
		return gunName.substring(0, 1).toUpperCase() + gunName.substring(1);
	}
}
