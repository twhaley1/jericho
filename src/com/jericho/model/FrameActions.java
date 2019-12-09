package com.jericho.model;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * An abstract class that is based on an animation timer. A user may 
 * provide a variable amount of standard actions, a variable amount
 * of completion actions, and a variable amount of predicates.
 * 
 * @author thomaswhaley
 *
 * @param <T>
 */
public abstract class FrameActions<T> extends AnimationTimer {

	private T parameter;
	
	private Collection<Consumer<T>> actions;
	private Collection<Predicate<T>> predicates;
	private Collection<Consumer<T>> actionsOnCompletion;
	
	protected FrameActions() {
		this.actions = new ArrayList<Consumer<T>>();
		this.predicates = new ArrayList<Predicate<T>>();
		this.actionsOnCompletion = new ArrayList<Consumer<T>>();
	}
	
	/**
	 * Adds the specified consumer to the standard list of 
	 * actions.
	 * 
	 * @precondition consumer != null
	 * 
	 * @param consumer the consumer to add.
	 */
	public void addAction(Consumer<T> consumer) {
		if (consumer == null) {
			throw new IllegalArgumentException();
		}
		
		this.actions.add(consumer);
	}
	
	/**
	 * Adds a variable number of actions to the standard list of actions.
	 * 
	 * @precondition consumers does not contain a null value.
	 * 
	 * @param consumers the variable number of consumers to add.
	 */
	public void addActions(Iterable<Consumer<T>> consumers) {
		for (Consumer<T> consumer : consumers) {
			this.addAction(consumer);
		}
	}
	
	/**
	 * Adds a consumer to the actions that are executed once at least one
	 * predicate returns false.
	 * 
	 * @precondition consumer != null
	 * 
	 * @param consumer the consumer to add.
	 */
	public void addActionOnCompletion(Consumer<T> consumer) {
		if (consumer == null) {
			throw new IllegalArgumentException();
		}
		
		this.actionsOnCompletion.add(consumer);
	}
	
	/**
	 * Adds a variable number of consumers to the actions that are executed once
	 * at least one predicate returns false.
	 * 
	 * @precondition consumers does not contain a null value.
	 * 
	 * @param consumers the consumers to add.
	 */
	public void addActionsOnCompletion(Iterable<Consumer<T>> consumers) {
		for (Consumer<T> consumer : consumers) {
			this.addActionOnCompletion(consumer);
		}
	}
	
	/**
	 * Adds a checking predicate. Once per frame, all predicates are executed.
	 * If any one of them return false, then the animation timer stops and executes
	 * all actions on completion consumers.
	 * 
	 * @precondition predicate != null
	 * 
	 * @param predicate the predicate to add.
	 */
	public void addPredicate(Predicate<T> predicate) {
		if (predicate == null) {
			throw new IllegalArgumentException();
		}
		
		this.predicates.add(predicate);
	}
	
	/**
	 * Adds a variable amount of predicates to check every frame.
	 * 
	 * @precondition predicates does not contain a null value.
	 * 
	 * @param predicates the predicates to add.
	 */
	public void addPredicates(Iterable<Predicate<T>> predicates) {
		for (Predicate<T> predicate : predicates) {
			this.addPredicate(predicate);
		}
	}
	
	/**
	 * Sets the parameter that will be passed to each consumer and predicate.
	 * 
	 * @param parameter the parameter that is passed to each consumer and predicate.
	 */
	public void setParameter(T parameter) {
		this.parameter = parameter;
	}
	
	/**
	 * Gets the parameter that is passed to each consumer and predicate.
	 * 
	 * @return the paramter that is passed to each consumer and predicate.
	 */
	public T getParameter() {
		return this.parameter;
	}
	
	/**
	 * Executes all consumers that are standard actions.
	 */
	public void fireAllStandardActions() {
		for (Consumer<T> consumer : this.actions) {
			consumer.accept(this.parameter);
		}
	}
	
	/**
	 * Executes all consumers that are completion actions.
	 */
	public void fireAllActionsOnCompletion() {
		for (Consumer<T> consumer : this.actionsOnCompletion) {
			consumer.accept(this.parameter);
		}
	}
	
	@Override
	public void handle(long currentTime) {
		if (this.arePredicatesTrue()) {
			this.fireAllStandardActions();	
		} else {
			this.fireAllActionsOnCompletion();
			this.stop();
		}
	}
	
	private boolean arePredicatesTrue() {
		boolean result = true;
		for (Predicate<T> predicate : this.predicates) {
			if (!predicate.test(this.parameter)) {
				result = false;
			}
		}
		
		return result;
	}

}
