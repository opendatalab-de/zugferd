package de.grundid.zugferd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfArray;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfNameTree;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfString;

public class PdfInvoiceReader {

	private PdfReader reader;

	public PdfInvoiceReader(PdfReader reader) {
		this.reader = reader;
	}

	@SuppressWarnings("unchecked")
	public List<PdfAttachment> extractAttachments() {
		List<PdfAttachment> result = new ArrayList<PdfAttachment>();
		try {
			PdfDictionary catalog = reader.getCatalog();
			PdfDictionary names = catalog.getAsDict(PdfName.NAMES);
			if (names != null) {
				PdfDictionary embFiles = names.getAsDict(new PdfName("EmbeddedFiles"));
				if (embFiles != null) {
					HashMap<String, PdfObject> embMap = PdfNameTree.readTree(embFiles);
					for (Iterator<PdfObject> i = embMap.values().iterator(); i.hasNext();) {
						PdfDictionary filespec = (PdfDictionary)PdfReader.getPdfObject(i.next());
						PdfAttachment attachment = unpackFile(filespec);
						if (attachment != null)
							result.add(attachment);
					}
				}
			}
			for (int k = 1; k <= reader.getNumberOfPages(); ++k) {
				PdfArray annots = reader.getPageN(k).getAsArray(PdfName.ANNOTS);
				if (annots == null)
					continue;
				for (Iterator<PdfObject> i = annots.listIterator(); i.hasNext();) {
					PdfDictionary annot = (PdfDictionary)PdfReader.getPdfObject(i.next());
					PdfName subType = annot.getAsName(PdfName.SUBTYPE);
					if (!PdfName.FILEATTACHMENT.equals(subType))
						continue;
					PdfDictionary filespec = annot.getAsDict(PdfName.FS);
					PdfAttachment attachment = unpackFile(filespec);
					if (attachment != null)
						result.add(attachment);
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	public PdfAttachment unpackFile(PdfDictionary filespec) throws IOException {
		if (filespec == null)
			return null;
		PdfName type = filespec.getAsName(PdfName.TYPE);
		if (!PdfName.F.equals(type) && !PdfName.FILESPEC.equals(type))
			return null;
		PdfDictionary ef = filespec.getAsDict(PdfName.EF);
		if (ef == null)
			return null;
		PRStream prs = (PRStream)PdfReader.getPdfObject(ef.get(PdfName.F));
		if (prs == null)
			return null;
		PdfString pdfDesc = filespec.getAsString(PdfName.DESC);
		String desc = pdfDesc != null ? pdfDesc.toString() : "";
		PdfString pdfName = filespec.getAsString(PdfName.F);
		String name = pdfName != null ? pdfName.toString() : "";
		byte[] data = PdfReader.getStreamBytes(prs);
		return new PdfAttachment(name, desc, data);
	}
}
