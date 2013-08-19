package de.grundid.zugferd;

import static org.junit.Assert.*;

import org.junit.Test;

public class XmlReaderTest {

	@Test
	public void itShouldReadAInvoice() throws Exception {
		CBFBUYType cbfbuyType = InvoiceReader.read(XmlReaderTest.class.getResourceAsStream("/ZUGFeRD-invoice-1.xml"));
		ExchangedDocumentType headerExchangedDocument = cbfbuyType.getHeaderExchangedDocument();
		assertEquals("471102", headerExchangedDocument.getID().getValue());
	}
}
