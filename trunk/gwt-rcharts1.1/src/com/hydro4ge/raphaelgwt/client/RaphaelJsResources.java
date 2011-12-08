package com.hydro4ge.raphaelgwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface RaphaelJsResources extends ClientBundle {

	public static final RaphaelJsResources RAPHAEL = GWT.create(RaphaelJsResources.class);
	
	@Source("raphael-min.js")
	public TextResource raphaelText();
}
