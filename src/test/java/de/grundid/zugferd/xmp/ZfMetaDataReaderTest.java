package de.grundid.zugferd.xmp;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

import de.grundid.zugferd.XmlReaderTest;
import de.grundid.zugferd.xmp.ConformanceLevel;
import de.grundid.zugferd.xmp.DocumentType;
import de.grundid.zugferd.xmp.Version;
import de.grundid.zugferd.xmp.ZfMetaData;
import de.grundid.zugferd.xmp.ZfMetaDataReader;

public class ZfMetaDataReaderTest {

	@Test
	public void itShouldParseMetaData() throws Exception {
		InputStream in = XmlReaderTest.class.getResourceAsStream("/metadata.xml");
		ZfMetaData metaData = ZfMetaDataReader.read(in);
		assertEquals("ZUGFeRD-invoice.xml", metaData.getDocumentFileName());
		assertEquals(ConformanceLevel.COMFORT, metaData.getConformanceLevel());
		assertEquals(DocumentType.INVOICE, metaData.getDocumentType());
		assertEquals(Version.RC, metaData.getVersion());
	}
}
