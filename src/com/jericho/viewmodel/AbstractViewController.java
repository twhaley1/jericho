package com.jericho.viewmodel;

/**
 * An abstraction for code behind files. This class allows
 * an abstraction to be built that makes it easier to generically
 * create/set a view model to a particular controller for the PageLoader.
 * 
 * @author thomaswhaley
 *
 */
public abstract class AbstractViewController {

	private ViewModel viewModel;
	
	/**
	 * Sets the view model of this abstract view controller to the
	 * specified view model.
	 * 
	 * @precondition viewModel != null
	 * @postcondition getViewModel().equals(viewModel)
	 * 
	 * @param viewModel the view model to give to this abstract view controller.
	 */
	public void setViewModel(ViewModel viewModel) {
		if (viewModel == null) {
			throw new IllegalArgumentException();
		}
		
		this.viewModel = viewModel;
	}
	
	/**
	 * Gets this abstract view controller's view model.
	 * 
	 * @return this abstract view controller's view model.
	 */
	public ViewModel getViewModel() {
		return this.viewModel;
	}
}
