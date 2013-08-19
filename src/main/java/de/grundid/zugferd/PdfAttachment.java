package de.grundid.zugferd;


public class PdfAttachment {

	private String name;
	private String desc;
	private byte[] data;

	public PdfAttachment(String name, String desc, byte[] data) {
		this.name = name;
		this.desc = desc;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public String getDesc() {
		return desc;
	}

	public byte[] getData() {
		return data;
	}
}
