package com.stj.web.wicket.component;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PdfPanel extends Panel {
	private static final long serialVersionUID = 1L;

	private static final int BUFFER_SIZE = 10 * 1024;

	/*
	 * public PdfPanel(String id) { super(id);
	 * 
	 * setRenderBodyOnly(true);
	 * 
	 * ResourceReference rr = new ResourceReference("weeklyPdf") { private
	 * static final long serialVersionUID = 1L;
	 * 
	 * @Override public IResource getResource() { return new PdfResource(); } };
	 * 
	 * WebMarkupContainer wmc = new WebMarkupContainer("pdfPanel"); wmc.add(new
	 * AttributeModifier("src", (String) urlFor(rr, null))); add(wmc); }
	 */

	public PdfPanel(String id, final InputStream stream) {
		super(id);

		setRenderBodyOnly(true);

		ResourceReference rr = new ResourceReference("weeklyPdf") {
			private static final long serialVersionUID = 1L;

			@Override
			public IResource getResource() {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				try {
					byte[] buf = new byte[BUFFER_SIZE];
					while (true) {
						int tam = stream.read(buf);
						if (tam == -1) {
							return null;
						}
						out.write(buf, 0, tam);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				return new ByteArrayResource("application/pdf", out.toByteArray());
			}
		};

		if (rr.canBeRegistered()) {
			getApplication().getResourceReferenceRegistry().registerResourceReference(rr);
		}

		WebMarkupContainer wmc = new WebMarkupContainer("pdfPanel");
		wmc.add(new AttributeModifier("src", (String) urlFor(rr, null)));
		add(wmc);
	}
}
