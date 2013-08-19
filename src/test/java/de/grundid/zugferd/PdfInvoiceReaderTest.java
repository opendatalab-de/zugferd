package de.grundid.zugferd;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.junit.Test;

import com.lowagie.text.pdf.PdfReader;

public class PdfInvoiceReaderTest {

	@Test
	public void itShouldReadAnInvoiceInAPdfFile() throws Exception {
		PdfReader reader = new PdfReader(
				PdfInvoiceReader.class.getResourceAsStream("/Beispielrechnung_ZUGFeRD_RC_COMFORT-1.pdf"));
		PdfInvoiceReader pdfInvoiceReader = new PdfInvoiceReader(reader);
		List<PdfAttachment> attachments = pdfInvoiceReader.extractAttachments();
		assertEquals(1, attachments.size());
		PdfAttachment pdfAttachment = attachments.get(0);
		assertEquals("ZUGFeRD-invoice.xml", pdfAttachment.getName());
		CBFBUYType cbfbuyType = InvoiceReader.read(new ByteArrayInputStream(pdfAttachment.getData()));
		ExchangedDocumentType headerExchangedDocument = cbfbuyType.getHeaderExchangedDocument();
		assertEquals("471102", headerExchangedDocument.getID().getValue());
	}
}
