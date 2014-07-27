package com.stj.web.ui;

import java.io.InputStream;

import org.apache.wicket.markup.html.WebPage;

import com.stj.web.wicket.component.PdfPanel;

public class PdfPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public PdfPage(InputStream stream) {
		add(new PdfPanel("pdfPanel", stream));
	}
	
}
