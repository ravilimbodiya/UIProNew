package com.uipro.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ProfileView extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5925925751088347869L;
	public static final String VIEW_NAME = "Profile";

    public ProfileView() {
        CustomLayout content = new CustomLayout("profileview");
        content.setStyleName("profile-content");
        content.addComponent(
                new Label(FontAwesome.INFO_CIRCLE.getHtml()
                        + " Profile View , Vaadin,  "
                        + Version.getFullVersion(), ContentMode.HTML), "info");

        setSizeFull();
        setStyleName("profile-view");
        addComponent(content);
        setComponentAlignment(content, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
