package com.uipro.entity;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;

public class ComponentDetail {
	private Component component;
	private Alignment alignment;
	
	public Component getComponent() {
		return component;
	}
	public void setComponent(Component component) {
		this.component = component;
	}
	public Alignment getAlignment() {
		return alignment;
	}
	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
}
