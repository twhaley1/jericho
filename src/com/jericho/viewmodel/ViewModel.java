package com.jericho.viewmodel;

import java.io.File;

import com.jericho.model.ControlledFrameHandler;
import com.jericho.model.StringExpander;
import com.jericho.model.TextReader;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

/**
 * The view model for Jericho.
 * 
 * @author thomaswhaley
 *
 */
public class ViewModel {

	private DoubleProperty progressProperty;
	
	private StringProperty contentsProperty;
	
	private BooleanProperty isCompleteProperty;
	private BooleanProperty isLoadingProperty;
    private BooleanProperty isPlayingProperty;
    private BooleanProperty isPausedProperty;
	
	private StringExpander readContents;
	
	public ViewModel() {
		this.initializeProperties();
	}
	
	public void loadTextFromFile(File file) {
		this.clearProperties();
		this.isLoadingProperty.setValue(true);
		Task<StringBuilder> task = new TextReader(file, progress -> this.progressProperty.setValue(progress));
		task.setOnSucceeded(event -> {
			this.readContents = new StringExpander(task.getValue());
			this.contentsProperty.setValue("");
			this.isLoadingProperty.setValue(false);
		});
		Thread loadThread = new Thread(task);
		loadThread.start();
	}
	
	public void iterativelyIncreaseContents() {
		if (this.isPausedProperty.not().getValue() && this.isPlayingProperty.not().getValue()) {
			this.isPlayingProperty.setValue(true);
			this.contentsProperty.setValue("");
			this.readContents.reset();
			
	    	ControlledFrameHandler<Void> handler = new ControlledFrameHandler<Void>(1);
	    	handler.addAction(nullParam -> this.contentsProperty.setValue(this.readContents.getNextString()));
	    	handler.addActionOnCompletion(nullParam -> this.isCompleteProperty.setValue(true));
	    	handler.addActionOnCompletion(nullParam -> this.isPlayingProperty.setValue(false));
	    	handler.addPredicate(nullParam -> !this.readContents.isComplete());
	    	handler.start();	
		} else {
			this.readContents.toggle();
			this.isPlayingProperty.setValue(true);
			this.isPausedProperty.setValue(false);
		}
	}
	
	public void pauseIncreasingContents() {
		this.isPlayingProperty.setValue(false);
		this.isPausedProperty.setValue(true);
		this.readContents.toggle();
	}
	
	private void initializeProperties() {
		this.progressProperty = new SimpleDoubleProperty();
		this.contentsProperty = new SimpleStringProperty();
		this.isCompleteProperty = new SimpleBooleanProperty();
		this.isLoadingProperty = new SimpleBooleanProperty();
    	this.isPlayingProperty = new SimpleBooleanProperty();
    	this.isPausedProperty = new SimpleBooleanProperty();
	}
	
	private void clearProperties() {
		this.progressProperty.setValue(0);
		this.contentsProperty.setValue(null);
		this.isCompleteProperty.setValue(false);
	}
	
	public BooleanProperty isPausedProperty() {
		return this.isPausedProperty;
	}
	
	public BooleanProperty isLoadingProperty() {
		return this.isLoadingProperty;
	}
	
	public BooleanProperty isPlayingProperty() {
		return this.isPlayingProperty;
	}
	
	public BooleanProperty isCompleteProperty() {
		return this.isCompleteProperty;
	}
	
	public StringProperty contentsProperty() {
		return this.contentsProperty;
	}
	
	public DoubleProperty progressProperty() {
		return this.progressProperty;
	}
}
