package com.stj.web.wicket.component;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.jfree.chart.JFreeChart;

public class JFreeChartImage extends Image {
	private static final long serialVersionUID = 1L;

	private int width;
	private int height;

	public JFreeChartImage(String id, JFreeChart chart, int width, int height) {
		super(id, new Model<JFreeChart>(chart));
		this.width = width;
		this.height = height;
	}

	@Override
	protected AbstractResource getImageResource() {
		return new DynamicImageResource() {

			private static final long serialVersionUID = 1L;

			@Override
			protected byte[] getImageData(Attributes attributes) {
				JFreeChart chart = (JFreeChart) getDefaultModelObject();
				return toImageData(chart.createBufferedImage(width, height));
			}
		};
	}
}
