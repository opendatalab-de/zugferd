package de.grundid.zugferd.xmp;

import java.io.InputStream;

import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPIterator;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPMetaFactory;
import com.adobe.xmp.properties.XMPPropertyInfo;

public class ZfMetaDataReader {

	public static ZfMetaData read(InputStream in) {
		try {
			XMPMeta element = XMPMetaFactory.parse(in);
			XMPIterator iterator = element.iterator();
			return parseZfData(iterator);
		}
		catch (XMPException e) {
			throw new RuntimeException(e);
		}
	}

	private static ZfMetaData parseZfData(XMPIterator it) {
		ZfMetaData metaData = new ZfMetaData();
		while (it.hasNext()) {
			XMPPropertyInfo pi = (XMPPropertyInfo)it.next();
			String path = pi.getPath();
			if (Schema.URN.equals(pi.getNamespace()) && path != null) {
				if (path.endsWith("ConformanceLevel")) {
					metaData.setConformanceLevel(ConformanceLevel.valueOf(pi.getValue()));
				}
				else if (path.endsWith("DocumentFileName")) {
					metaData.setDocumentFileName(pi.getValue());
				}
				else if (path.endsWith("DocumentType")) {
					metaData.setDocumentType(DocumentType.valueOf(pi.getValue()));
				}
				else if (path.endsWith("Version")) {
					metaData.setVersion(Version.valueOf(pi.getValue()));
				}
			}
		}
		return metaData;
	}
}
