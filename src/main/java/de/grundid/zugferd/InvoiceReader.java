package de.grundid.zugferd;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class InvoiceReader {

	@SuppressWarnings("unchecked")
	public static CBFBUYType read(InputStream in) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller u = jc.createUnmarshaller();
		JAXBElement<CBFBUYType> element = (JAXBElement<CBFBUYType>)u.unmarshal(in);
		return (CBFBUYType)element.getValue();
	}
}
