package com.uipro.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.Version;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


public class RealTimeView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "RealtimeView";
    
    public static CustomLayout contentToBeAdded = new CustomLayout();
    
    public RealTimeView() {
        System.out.println("realtime view");
        //aboutContent.setStyleName("about-content");

        // you can add Vaadin components in predefined slots in the custom
        // layout
        addComponent(new Label("RTV"));
    }
    
    public void addComponentToRealTimeView(Component compToAdd){
    	addComponent(compToAdd);

        setSizeFull();
       // setStyleName("about-view");
        //addComponent(contentToBeAdded);
        setComponentAlignment(compToAdd, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeEvent event) {
    }

}
