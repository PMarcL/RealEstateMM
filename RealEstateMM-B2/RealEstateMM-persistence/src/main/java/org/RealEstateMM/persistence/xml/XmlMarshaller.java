package org.RealEstateMM.persistence.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlMarshaller {

	private File file;
	private boolean isNewFile;

	public XmlMarshaller(File file) {
		this.file = file;
		this.isNewFile = !(this.file.exists());

		if (this.isNewFile) {
			createFile();
		}
	}

	private void createFile() {
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			throw new ErrorCreatingXmlFileException(e);
		}
	}

	public <T> void marshal(Class<T> typeToMarshall, T objectToMarshall) {
		try {
			Marshaller marshaller = createJaxbMarshaller(typeToMarshall);
			marshaller.marshal(objectToMarshall, file);
		} catch (JAXBException e) {
			throw new XmlMarshallingException(e);
		}
	}

	private <T> Marshaller createJaxbMarshaller(Class<T> typeToMarshall) throws JAXBException {
		JAXBContext context = createContext(typeToMarshall);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		return marshaller;
	}

	private <T> JAXBContext createContext(Class<T> typeToMarshall) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(typeToMarshall);
		return context;
	}

	@SuppressWarnings("unchecked")
	public <T> T unmarshal(Class<T> typeToMarshall) {
		if (isNewFile) {
			throw new EmptyXmlFileException();
		}

		try {
			Unmarshaller unmarshaller = createJaxbUnmarshaller(typeToMarshall);
			T result = (T) unmarshaller.unmarshal(file);
			return result;
		} catch (JAXBException e) {
			throw new XmlMarshallingException(e);
		}
	}

	private <T> Unmarshaller createJaxbUnmarshaller(Class<T> typeToMarshall) throws JAXBException {
		JAXBContext context = createContext(typeToMarshall);
		return context.createUnmarshaller();
	}
}
