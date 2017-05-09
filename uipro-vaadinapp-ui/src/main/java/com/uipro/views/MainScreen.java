package com.uipro.views;

import com.uipro.requesthandlers.MyUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.uipro_vaadinapp.samples.crud.RealTimeDesignView;

/**
 * Content of the UI when the user is logged in.
 * 
 * 
 */
public class MainScreen extends HorizontalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3170050308292436612L;
	private Menu menu;

	public MainScreen(MyUI ui) {

		setStyleName("main-screen");

		CssLayout viewContainer = new CssLayout();
		viewContainer.addStyleName("valo-content");
		viewContainer.setSizeFull();

		final Navigator navigator = new Navigator(ui, viewContainer);
		navigator.setErrorView(ErrorView.class);
		menu = new Menu(navigator);
		menu.addView(new RealTimeDesignView(MyUI.getGlobalLayout()),
				RealTimeDesignView.VIEW_NAME, RealTimeDesignView.VIEW_NAME,
				FontAwesome.EYE);
		menu.addView(new MyDesigns(), MyDesigns.VIEW_NAME,
				MyDesigns.VIEW_NAME, FontAwesome.DASHBOARD);
		menu.addView(new UserProfileView(), UserProfileView.VIEW_NAME,
				UserProfileView.VIEW_NAME, FontAwesome.USER);
//		menu.addView(new AboutView(), AboutView.VIEW_NAME, AboutView.VIEW_NAME,
//				FontAwesome.INFO_CIRCLE);
		navigator.addViewChangeListener(viewChangeListener);

		addComponent(menu);
		addComponent(viewContainer);
		setExpandRatio(viewContainer, 1);
		setSizeFull();
		navigator.navigateTo(RealTimeDesignView.VIEW_NAME);
	}

	// notify the view menu about view changes so that it can display which view
	// is currently active
	ViewChangeListener viewChangeListener = new ViewChangeListener() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 891468528102261083L;

		@Override
		public boolean beforeViewChange(ViewChangeEvent event) {
			return true;
		}

		@Override
		public void afterViewChange(ViewChangeEvent event) {
			menu.setActiveView(event.getViewName());
		}

	};
}
