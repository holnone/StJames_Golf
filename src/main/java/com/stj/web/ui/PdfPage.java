package com.stj.web.ui;

import com.stj.web.wicket.component.PdfPanel;
import org.apache.wicket.markup.html.WebPage;

import java.io.InputStream;

public class PdfPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public PdfPage(InputStream stream) {
		add(new PdfPanel("pdfPanel", stream));
	}
	
}
