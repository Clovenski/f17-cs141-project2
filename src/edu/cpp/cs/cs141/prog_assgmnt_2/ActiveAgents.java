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
 * This class represents an entity which is either a player or an enemy, in the context of a game.
 * Each object of this type will have health in the form of hit points and a gun as a weapon.
 * @author Joel Tengco
 *
 */
public class ActiveAgents {
	/**
	 * This represented the amount of hit points this agent currently has.
	 */
	private int health;
	/**
	 * This represented the maximum amount of hit points this agent can have.
	 */
	private int maxHealth;
	/**
	 * Determines if the agent has 0 hit points or not.
	 */
	private boolean isAlive;
	/**
	 * Represents the gun that is owned and equipped by this agent.
	 */
	private Gun equippedWeapon;
	
	/**
	 * Constructs a new {@code ActiveAgents} object that is either the player or not the player, equipped with the given {@code Gun} object.
	 * If this agent is the player, then this object has 20 max hit points, otherwise it has 5 max hit points.
	 * @param isPlayer true if this agent is the player, false if not
	 * @param weapon the gun that this agent will be equipped with
	 */
	public ActiveAgents(boolean isPlayer, Gun weapon) {
		if(isPlayer)
			maxHealth = 20;
		else
			maxHealth = 5;
		health = maxHealth;
		
		equippedWeapon = weapon;
		isAlive = true;
	}
	
	/**
	 * Copy constructor. The new object will have the same properties as the given.
	 * @param otherAgent the {@code ActiveAgents} object containing the desired properties.
	 */
	public ActiveAgents(ActiveAgents otherAgent) {
		health = otherAgent.health;
		maxHealth = otherAgent.maxHealth;
		isAlive = otherAgent.isAlive;
		equippedWeapon = otherAgent.equippedWeapon.getCopy();
	}

	/**
	 * Gets the current amount of health this agent has.
	 * @return the current amount of hit points this agent has
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Gets the gun this agent is equipped with.
	 * @return a reference to the object of type {@code Gun} this agent is connected to
	 */
	public Gun getGun() {
		return equippedWeapon;
	}

	/**
	 * Lets this agent receive {@code i} amount of hit points. It will not gain any more health once it matches the maximum amount
	 * of health this agent can have.
	 * @param i the amount of hit points to be gained for this agent
	 */
	public void gainHealth(int i) {
		if(health + i > maxHealth)
			health = maxHealth;
		else
			health = health + i;
	}
	
	/**
	 * Lets this agent lose {@code i} amount of hit points, affecting whether it is alive or not. It is possible for this agent
	 * to have negative amount of health after this method returns.
	 * @param i the amount of health for this agent to lose
	 */
	public void loseHealth(int i) {
		health = health - i;
		if(health <= 0)
			isAlive = false;
	}
	
	/**
	 * Determines if this agent is alive or not.
	 * @return true if this agent's current health is greater than zero, false if it is zero or less than zero
	 */
	public boolean isAlive() {
		return isAlive;
	}
	
	/**
	 * Gets a copy of this agent.
	 * @return a reference to an object containing the same properties as this agent
	 */
	public ActiveAgents getCopy() {
		ActiveAgents temp = new ActiveAgents(this);
		return temp;
	}
}
