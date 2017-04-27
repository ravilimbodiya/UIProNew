package com.uipro.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


public class MyDesigns extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5904382487227378657L;

	public static final String VIEW_NAME = "My Designs";
    
    public static CustomLayout contentToBeAdded = new CustomLayout();
    
    public MyDesigns() {
        addComponent(new Label("Your older designs are:"));
    }
    
    public void addComponentToRealTimeView(Component compToAdd){
    	addComponent(compToAdd);
        setSizeFull();
        setComponentAlignment(compToAdd, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
