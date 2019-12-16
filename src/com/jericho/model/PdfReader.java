package com.jericho.model;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfReader extends Reader {

	public PdfReader(File file) {
		super(file);
	}

	@Override
	protected String getFileContents() throws IOException {
		StringBuilder sb = new StringBuilder();
		try (PDDocument document = PDDocument.load(this.getFile())) {
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setSortByPosition(true);
			
			int numberOfPages = document.getNumberOfPages();
			for (int i = 1; i <= numberOfPages; i++) {
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				
				String text = stripper.getText(document);
				sb.append(text.strip());
				
				this.getLoadingProgress().setValue(i / (double) numberOfPages);
			}
		} 
		
		return sb.toString();
	}

}
