package de.grundid.zugferd.xmp;

public class ZfMetaData {

	private DocumentType documentType;
	private String documentFileName;
	private Version version;
	private ConformanceLevel conformanceLevel;

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public String getDocumentFileName() {
		return documentFileName;
	}

	public void setDocumentFileName(String documentFileName) {
		this.documentFileName = documentFileName;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public ConformanceLevel getConformanceLevel() {
		return conformanceLevel;
	}

	public void setConformanceLevel(ConformanceLevel conformanceLevel) {
		this.conformanceLevel = conformanceLevel;
	}
}
