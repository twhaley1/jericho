package com.jericho.viewmodel;

import java.io.File;

import com.jericho.model.ControlledFrameActions;
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
	private ControlledFrameActions<Void> textControl;
	
	/**
	 * Creates a new ViewMoidel object. All property initializations occur
	 * in this constructor.
	 */
	public ViewModel() {
		this.initializeProperties();
	}
	
	/**
	 * Loads text from the specified file object. 
	 * 
	 * @precondition file != null.
	 * @postcondition progressProperty(), contentsProperty(), and isCompleteProperty()
	 * are set to their initial values: 0, null, and false respectively. A new thread is
	 * created that handles the reading of the file. The progress of the text reading thread
	 * is continuously updated once per frame and can be read via the progressProperty(). Upon
	 * successful completion of the thread's task, capabilities to perform tasks on the contents of the
	 * file are ready to be performed. Also, contentsProperty().equals("") and isLoadingProperty() == false.
	 * 
	 * @param file a text file object.
	 */
	public void loadTextFromFile(File file) {
		if (file == null) {
			throw new IllegalArgumentException();
		}
		
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
	
	/**
	 * Iteratively increases the contents of the contentsProperty() by one character
	 * every frame. When there is no more content to be shown, then isCompleteProperty() == true
	 * and isPlayingProperty() == false.
	 * 
	 * @postcondition isPausedProperty() == false && isPlayingProperty() == true
	 */
	public void iterativelyIncreaseContents() {
		if (this.isPausedProperty.not().getValue() && this.isPlayingProperty.not().getValue()) {
			this.isPlayingProperty.setValue(true);
			this.contentsProperty.setValue("");
			this.readContents.reset();
			
	    	this.textControl = new ControlledFrameActions<Void>(1);
	    	this.textControl.addAction(nullParam -> this.contentsProperty.setValue(this.readContents.getNextString()));
	    	this.textControl.addActionOnCompletion(nullParam -> this.isCompleteProperty.setValue(true));
	    	this.textControl.addActionOnCompletion(nullParam -> this.isPlayingProperty.setValue(false));
	    	this.textControl.addPredicate(nullParam -> !this.readContents.isComplete());
	    	this.textControl.start();	
		} else {
			this.readContents.toggle();
			this.isPlayingProperty.setValue(true);
			this.isPausedProperty.setValue(false);
		}
	}
	
	/**
	 * Stops the contentsProperty()'s contents from growing in size. Effectively
	 * pausing the application. 
	 * 
	 * @postcondition isPlayingProperty() == false && isPausedProperty() == true
	 */
	public void pauseIncreasingContents() {
		this.isPlayingProperty.setValue(false);
		this.isPausedProperty.setValue(true);
		this.readContents.toggle();
	}
	
	/**
	 * Clears the contents of of the contentsProperty() without resetting the application.
	 * This method can be called to reset the screen. A call to iterativelyIncreaseContents()
	 * will restart the display of contentsProperty() from the beginning.
	 * 
	 * @postcondition contentsProperty().equals("") && isPlayingProperty() == false && 
	 * isPausedProperty() == false && isCompleteProperty() == false
	 */
	public void clearContents() {
		this.contentsProperty.setValue("");
		this.readContents.reset();
		this.readContents.unPause();
		this.textControl.stop();
		this.isPlayingProperty.setValue(false);
		this.isPausedProperty.setValue(false);
		this.isCompleteProperty.setValue(false);
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
	
	/**
	 * A property indicating if the state of the system is paused.
	 * 
	 * @return the isPausedProperty.
	 */
	public BooleanProperty isPausedProperty() {
		return this.isPausedProperty;
	}
	
	/**
	 * A property indicating if the system is loading some process.
	 * 
	 * @return the isLoadingProperty.
	 */
	public BooleanProperty isLoadingProperty() {
		return this.isLoadingProperty;
	}
	
	/**
	 * A property indicating if the system is currently playing something.
	 * e.g. contentsProperty() is changing every frame.
	 * 
	 * @return the isPlayingProperty.
	 */
	public BooleanProperty isPlayingProperty() {
		return this.isPlayingProperty;
	}
	
	/**
	 * A property indicating if the incrementing of contentsProperty()
	 * is complete or not.
	 * 
	 * @return the isCompleteProperty.
	 */
	public BooleanProperty isCompleteProperty() {
		return this.isCompleteProperty;
	}
	
	/**
	 * A property that a string. This string is read from a file chosen by the user.
	 * Calls to iterativelyIncreaseContents() will cause this property's value to 
	 * increase in size().
	 * 
	 * @return the contentsProperty.
	 */
	public StringProperty contentsProperty() {
		return this.contentsProperty;
	}
	
	/**
	 * A property that indicates the current state of progress in a current
	 * background thread. When a user selects a file to load, the progress of 
	 * reading the text from the file is accounted for in this property.
	 * 
	 * @return the progressProperty.
	 */
	public DoubleProperty progressProperty() {
		return this.progressProperty;
	}
}
