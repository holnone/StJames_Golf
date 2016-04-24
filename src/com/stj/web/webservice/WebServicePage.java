package com.stj.web.webservice;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.MarkupType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class WebServicePage extends WebPage {

	private static final long serialVersionUID = 1L;

	public WebServicePage() {
		setStatelessHint(true);
	}

	protected final void onRender(MarkupStream markupStream) {
		PrintWriter pw = new PrintWriter(getResponse().getOutputStream());
		pw.write(getXML().toString());
		pw.close();
	}

	protected XStream createXStream() {
		XStream xstream = new XStream(new XppDriver());
		xstream.setMode(XStream.ID_REFERENCES);
		return xstream;
	}

	protected String getXML() {
		Serializer serializer = new Persister();
		StringWriter writer = new StringWriter();
		try {
			serializer.write(getDefaultModelObject(), writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.getBuffer().toString();
		/*
		 * XStream xstream = createXStream(); return
		 * xstream.toXML(getDefaultModelObject());
		 */
	}

	@Override
	public final MarkupType getMarkupType() {
		return new MarkupType("xml", MarkupType.XML_MIME);
	}

	
	/*@Override
	public final boolean hasAssociatedMarkup() {
		return false;
	}*/

	@Override
	public final Component add(Behavior... behaviors) {
		throw new UnsupportedOperationException("WebServicePage does not support IBehaviours");
	}

	@Override
	protected void configureResponse(WebResponse response) {
		super.configureResponse(response);
		response.setContentType("text/xml"); 
	}

}
