package com.stj.web.util;

import org.apache.wicket.request.resource.ByteArrayResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PdfResource extends ByteArrayResource {

	private static final long serialVersionUID = 1L;

	static int BUFFER_SIZE = 10 * 1024;

	public PdfResource() {
		super("application/pdf");
	}
	
	public PdfResource(byte[] stream) {
		super("application/pdf", stream);
	}

	public static byte[] bytes(InputStream is) {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			copy(is, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	public static void copy(InputStream is, OutputStream os) throws IOException {
		byte[] buf = new byte[BUFFER_SIZE];
		while (true) {
			int tam = is.read(buf);
			if (tam == -1) {
				return;
			}
			os.write(buf, 0, tam);
		}
	}
}
