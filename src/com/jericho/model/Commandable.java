package com.jericho.model;

/**
 * This interface defines a contract for classes such that
 * signifies an action, a value indicating if that action
 * should be executed, and if the Commandable should be disposed.
 * 
 * @author thomawhaley
 *
 */
public interface Commandable {

	/**
	 * Executes an action.
	 */
	void execute();
	
	/**
	 * Determines whether this Commandable can execute.
	 * 
	 * @return true if this commandable can execute; false otherwise.
	 */
	boolean canExecute();
	
	/**
	 * Disposes of all resources used by this Commandable.
	 */
	void dispose();
}
