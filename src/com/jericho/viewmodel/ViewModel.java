package com.jericho.viewmodel;

import java.io.File;

import com.jericho.model.ControlledFrameAction;
import com.jericho.model.Reader;
import com.jericho.model.ReaderFactory;
import com.jericho.model.StringExpander;
import com.jericho.model.settings.Setting;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * The view model for Jericho.
 * 
 * @author thomaswhaley
 *
 */
public class ViewModel {

	private DoubleProperty progressProperty;
	
	private ObjectProperty<Setting> settingsProperty;
	private ObjectProperty<Font> fontProperty;
	private ObjectProperty<Color> fontColorProperty;
	private ObjectProperty<Background> backgroundProperty;
	private IntegerProperty speedProperty;
	
	private StringProperty contentsProperty;
	
	private BooleanProperty isLoadingProperty;
    private BooleanProperty isPlayingProperty;
    private BooleanProperty isPausedProperty;
	
    private IntegerProperty numberOfLinesProperty;
    
	private StringExpander readContents;
	private ControlledFrameAction readingTimer;
	
	/**
	 * Creates a new ViewMoidel object. All property initializations occur
	 * in this constructor.
	 */
	public ViewModel() {
		this.initializeProperties();
		this.addSettingListener();
		this.addSpeedListener();
		this.addNumberOfLinesListener();
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
		
		this.setPropertiesForLoading();
		this.startLoadingTask(file);
	}
	
	private void startLoadingTask(File file) {
		Reader task = ReaderFactory.createReader(file);
		task.getLoadingProgress().addListener((observable, oldValue, newValue) -> {
			this.progressProperty.setValue(newValue);
		});
		task.setOnSucceeded(event -> {
			this.readContents = new StringExpander(task.getValue());
			this.readContents.setMaximumLines(this.numberOfLinesProperty.get());
			this.contentsProperty.setValue("");
			this.isLoadingProperty.setValue(false);
			this.addListenersForReadContents();
		});
		Thread loadThread = new Thread(task);
		loadThread.start();
	}
	
	private void addListenersForReadContents() {
		this.readContents.contentProperty().addListener((observable, oldValue, newValue) -> {
			this.contentsProperty.setValue(newValue);
		});
		this.readContents.isCompleteProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				this.isPlayingProperty.setValue(false);
				this.isPausedProperty.setValue(true);
				this.readContents.pause();
			}
		});
	}
	
	/**
	 * Iteratively increases the contents of the contentsProperty() by one character
	 * every frame. When there is no more content to be shown, then isCompleteProperty() == true
	 * and isPlayingProperty() == false.
	 * 
	 * @postcondition isPausedProperty() == false && isPlayingProperty() == true
	 */
	public void startPlaying() {
		if (!this.isPausedProperty.get() || this.readContents.isCompleteProperty().get()) {
			this.readContents.reset();
			this.readingTimer = new ControlledFrameAction(this.readContents, this.speedProperty.get());
			this.readingTimer.start();
		}
		
		this.isPlayingProperty.setValue(true);
		this.isPausedProperty.setValue(false);
		this.readContents.unPause();
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
		if (this.readContents != null) {
			this.readContents.pause();	
		}
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
		this.readContents.pause();
		this.isPlayingProperty.setValue(false);
		this.isPausedProperty.setValue(true);
	}
	
	private void initializeProperties() {
		this.progressProperty = new SimpleDoubleProperty();
		this.contentsProperty = new SimpleStringProperty();
		this.isLoadingProperty = new SimpleBooleanProperty();
    	this.isPlayingProperty = new SimpleBooleanProperty();
    	this.isPausedProperty = new SimpleBooleanProperty();
    	this.speedProperty = new SimpleIntegerProperty();
    	this.fontProperty = new SimpleObjectProperty<Font>();
    	this.settingsProperty = new SimpleObjectProperty<Setting>();
    	this.fontColorProperty = new SimpleObjectProperty<Color>();
    	this.backgroundProperty = new SimpleObjectProperty<Background>();
    	this.numberOfLinesProperty = new SimpleIntegerProperty();
	}
	
	private void setPropertiesForLoading() {
		this.progressProperty.setValue(0);
		this.contentsProperty.setValue(null);
		this.isPlayingProperty.setValue(false);
		this.isPausedProperty.setValue(false);
		this.isLoadingProperty.setValue(true);
		this.readContents = null;
		
		if (this.readingTimer != null) {
			this.readingTimer.stop();
		}
		this.readingTimer = null;
	}
	
	private void addSettingListener() {
		this.settingsProperty.addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				this.fontProperty.setValue(Font.font(newValue.getFont(), newValue.getFontSize()));
				this.speedProperty.setValue(newValue.getSpeed());
				this.fontColorProperty.setValue(newValue.getFontColor());
				this.backgroundProperty.setValue(new Background(
						new BackgroundFill(newValue.getBackgroundColor(), CornerRadii.EMPTY, Insets.EMPTY)));
				this.numberOfLinesProperty.setValue(newValue.getNumberOfLines());
			}
		});
	}
	
	private void addNumberOfLinesListener() {
		this.numberOfLinesProperty.addListener((observable, oldValue, newValue) -> {
			if (this.readContents != null) {
				this.readContents.setMaximumLines(newValue.intValue());
			}
		});
	}
	
	private void addSpeedListener() {
		this.speedProperty.addListener((observable, oldValue, newValue) -> {
			if (this.readingTimer != null) {
				this.readingTimer.setSpeed((int) newValue); 
			}
		});
	}
	
	/**
	 * A property indicating the number of lines that are displayed to the user.
	 * 
	 * @return the numberOfLinesProperty.
	 */
	public IntegerProperty numberOfLinesProperty() {
		return this.numberOfLinesProperty;
	}
	
	/**
	 * A property indicating the background for the text displayed to the user.
	 * 
	 * @return the backgroundProperty.
	 */
	public ObjectProperty<Background> backgroundProperty() {
		return this.backgroundProperty;
	}
	
	/**
	 * A property indicating the current color of the text displayed to the user.
	 * 
	 * @return the fontColorProperty.
	 */
	public ObjectProperty<Color> fontColorProperty() {
		return this.fontColorProperty;
	}
	
	/**
	 * A property indicating the current settings for the system.
	 * 
	 * @return the settingsProperty.
	 */
    public ObjectProperty<Setting> settingsProperty() {
		return this.settingsProperty;
	}
	
    /**
     * A property indicating the font for the contentProperty.
     * 
     * @return the fontProperty.
     */
    public ObjectProperty<Font> fontProperty() {
    	return this.fontProperty;
    }
    
	/**
	 * A property indicating how fast the text is flowing.
	 * 
	 * @return the speedProperty.
	 */
	public IntegerProperty speedProperty() {
		return this.speedProperty;
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
