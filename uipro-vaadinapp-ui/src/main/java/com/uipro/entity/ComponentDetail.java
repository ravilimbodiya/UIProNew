package com.uipro.entity;

import com.uipro.views.ContactUsView;
import com.uipro.views.FooterView;
import com.uipro.views.HeaderView;
import com.uipro.views.SimpleLoginView;
import com.uipro.views.TestView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;

public class ComponentDetail {
	private Component component;
	private Alignment alignment;
	private SimpleLoginView loginViewTemplate;
	private TestView testViewTemplate;
	private HeaderView headerViewTemplate;
	private FooterView footerViewTemplate;
	private ContactUsView contactUsViewTemplate;
	
	public HeaderView getHeaderViewTemplate() {
		return headerViewTemplate;
	}
	public void setHeaderViewTemplate(HeaderView headerView) {
		this.component = headerView;
	}
	public FooterView getFooterView() {
		return footerViewTemplate;
	}
	public void setFooterViewTemplate(FooterView footerView) {
		this.component = footerView;
	}
	public ContactUsView getContactUsViewTemplate() {
		return contactUsViewTemplate;
	}
	public void setContactUsViewTemplate(ContactUsView contactUsView) {
		this.component = contactUsView;
	}
	public SimpleLoginView getLoginViewTemplate() {
		return loginViewTemplate;
	}
	public TestView getTestViewTemplate() {
		return testViewTemplate;
	}
	public void setTestViewTemplate(TestView testViewTemplate) {
		this.component = testViewTemplate;
	}
	public void setLoginViewTemplate(SimpleLoginView loginViewTemplate) {
		this.component = loginViewTemplate;
	}
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
