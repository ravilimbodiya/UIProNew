package com.vaadin.uipro_vaadinapp.samples.crud;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;

import com.uipro.entity.Product;
import com.uipro.utility.Constants;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid.SelectionModel;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.JavaScriptFunction;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

import elemental.json.JsonArray;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link SampleCrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */

public class RealTimeDesignView extends CssLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8984717224420228155L;
	public static final String VIEW_NAME = "Viewer";
    private ProductGrid grid;
    private ProductForm form;

    private SampleCrudLogic viewLogic = new SampleCrudLogic(this);
    private Button saveDesignButton;

    public RealTimeDesignView(VerticalLayout realTimeDesignViewerLayout) {
        setSizeFull();
        addStyleName("crud-view");
        HorizontalLayout topLayout = createTopBar("See how your design looks like");
        VerticalLayout vLayout = new VerticalLayout();
        	vLayout.addComponent(topLayout);
        	vLayout.addComponent(realTimeDesignViewerLayout);
        	vLayout.setMargin(true);
        	vLayout.setSpacing(true);
        	
        	vLayout.setSizeFull();
        	vLayout.setExpandRatio(topLayout, 1);
        	vLayout.setExpandRatio(realTimeDesignViewerLayout, 25);
        	vLayout.setStyleName("crud-main-layout");
        	
        	addComponent(vLayout);
    }

	public HorizontalLayout createTopBar(String heading) {
        Label headingLabel = new Label(heading);
        headingLabel.addStyleName("heading-label");
        saveDesignButton = new Button("Save Design");
        saveDesignButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveDesignButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7198740271637519085L;

			@Override
            public void buttonClick(Button.ClickEvent event) {
				String APP_URL = "";
				if(Page.getCurrent().getLocation().getPort() != -1){
					//Port is available, i.e Localhost
					APP_URL = "http://"+Page.getCurrent().getLocation().getHost()+":"+Page.getCurrent().getLocation().getPort()+"/uipro-vaadinapp-ui";
				} else {
					// No Port i.e. IBM bluemix cloud URL
					APP_URL = "https://"+Page.getCurrent().getLocation().getHost();
				}
				JavaScript.getCurrent().execute("var saveAs=saveAs||function(e){'use strict';if(typeof e==='undefined'||typeof navigator!=='undefined'&&/MSIE [1-9]\\./.test(navigator.userAgent)){return}var t=e.document,n=function(){return e.URL||e.webkitURL||e},r=t.createElementNS('http://www.w3.org/1999/xhtml','a'),o='download'in r,a=function(e){var t=new MouseEvent('click');e.dispatchEvent(t)},i=/constructor/i.test(e.HTMLElement)||e.safari,f=/CriOS\\/[\\d]+/.test(navigator.userAgent),u=function(t){(e.setImmediate||e.setTimeout)(function(){throw t},0)},s='application/octet-stream',d=1e3*40,c=function(e){var t=function(){if(typeof e==='string'){n().revokeObjectURL(e)}else{e.remove()}};setTimeout(t,d)},l=function(e,t,n){t=[].concat(t);var r=t.length;while(r--){var o=e['on'+t[r]];if(typeof o==='function'){try{o.call(e,n||e)}catch(a){u(a)}}}},p=function(e){if(/^\\s*(?:text\\/\\S*|application\\/xml|\\S*\\/\\S*\\+xml)\\s*;.*charset\\s*=\\s*utf-8/i.test(e.type)){return new Blob([String.fromCharCode(65279),e],{type:e.type})}return e},v=function(t,u,d){if(!d){t=p(t)}var v=this,w=t.type,m=w===s,y,h=function(){l(v,'writestart progress write writeend'.split(' '))},S=function(){if((f||m&&i)&&e.FileReader){var r=new FileReader;r.onloadend=function(){var t=f?r.result:r.result.replace(/^data:[^;]*;/,'data:attachment/file;');var n=e.open(t,'_blank');if(!n)e.location.href=t;t=undefined;v.readyState=v.DONE;h()};r.readAsDataURL(t);v.readyState=v.INIT;return}if(!y){y=n().createObjectURL(t)}if(m){e.location.href=y}else{var o=e.open(y,'_blank');if(!o){e.location.href=y}}v.readyState=v.DONE;h();c(y)};v.readyState=v.INIT;if(o){y=n().createObjectURL(t);setTimeout(function(){r.href=y;r.download=u;a(r);h();c(y);v.readyState=v.DONE});return}S()},w=v.prototype,m=function(e,t,n){return new v(e,t||e.name||'download',n)};if(typeof navigator!=='undefined'&&navigator.msSaveOrOpenBlob){return function(e,t,n){t=t||e.name||'download';if(!n){e=p(e)}return navigator.msSaveOrOpenBlob(e,t)}}w.abort=function(){};w.readyState=w.INIT=0;w.WRITING=1;w.DONE=2;w.error=w.onwritestart=w.onprogress=w.onwrite=w.onabort=w.onerror=w.onwriteend=null;return m}(typeof self!=='undefined'&&self||typeof window!=='undefined'&&window||this.content);if(typeof module!=='undefined'&&module.exports){module.exports.saveAs=saveAs}else if(typeof define!=='undefined'&&define!==null&&define.amd!==null){define('FileSaver.js',function(){return saveAs})}"
						+ "var resources = \"<link rel='stylesheet' type='text/css' href='"+APP_URL+"/VAADIN/themes/mytheme/styles.css?v=7.7.8'>\";"
						+ "var innerHTML = \"<div class='v-app mytheme myui'>\"+document.getElementsByClassName('class-for-download')[0].innerHTML+\"</div>\";"
						+ "var contentToSave = resources + innerHTML;"
						+ "var blob = new Blob([contentToSave], {type: 'text/html;charset=utf-8'});"
						+ "saveAs(blob, 'your_design.html');"
						);
				showSaveNotification("Your design is saved to local disk.");
            }
        });

     
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth("100%");
        topLayout.addComponent(headingLabel);
        topLayout.addComponent(saveDesignButton);
        topLayout.setComponentAlignment(headingLabel, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(headingLabel, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        viewLogic.enter(event.getParameters());
    }

    public void showError(String msg) {
        Notification.show(msg, Type.ERROR_MESSAGE);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg, Type.TRAY_NOTIFICATION);
    }

    public void setNewProductEnabled(boolean enabled) {
        saveDesignButton.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().reset();
    }

    public void selectRow(Product row) {
        ((SelectionModel.Single) grid.getSelectionModel()).select(row);
    }

    public Product getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void editProduct(Product product) {
        if (product != null) {
            form.addStyleName("visible");
            form.setEnabled(true);
        } else {
            form.removeStyleName("visible");
            form.setEnabled(false);
        }
        form.editProduct(product);
    }

    public void showProducts(Collection<Product> products) {
        grid.setProducts(products);
    }

    public void refreshProduct(Product product) {
        grid.refresh(product);
        grid.scrollTo(product);
    }

    public void removeProduct(Product product) {
        grid.remove(product);
    }

}
