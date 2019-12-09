package com.jericho.model;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FrameHandler<T> extends AnimationTimer {

	private T parameter;
	private Collection<Consumer<T>> actions;
	private Collection<Predicate<T>> predicates;
	private Collection<Consumer<T>> actionsOnCompletion;
	
	public FrameHandler() {
		this.actions = new ArrayList<Consumer<T>>();
		this.predicates = new ArrayList<Predicate<T>>();
		this.actionsOnCompletion = new ArrayList<Consumer<T>>();
	}
	
	public void addAction(Consumer<T> consumer) {
		this.actions.add(consumer);
	}
	
	public void addActionOnCompletion(Consumer<T> consumer) {
		this.actionsOnCompletion.add(consumer);
	}
	
	public void addPredicate(Predicate<T> predicate) {
		this.predicates.add(predicate);
	}
	
	public void setParameter(T parameter) {
		this.parameter = parameter;
	}
	
	public T getConsumerParam() {
		return this.parameter;
	}
	
	public void fireAllStandardActions() {
		for (Consumer<T> consumer : this.actions) {
			consumer.accept(this.parameter);
		}
	}
	
	public void fireAllActionsOnCompletion() {
		for (Consumer<T> consumer : this.actionsOnCompletion) {
			consumer.accept(this.parameter);
		}
	}
	
	@Override
	public void handle(long arg0) {
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
