package candy.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class CandyXmlValidator extends DefaultHandler {
    public static final String SCHEMA_FILE_NAME = "src\\main\\resources\\candies.xsd";
    private boolean xmlFileIsValid = true;
    private Logger logger;

    public CandyXmlValidator() {
        logger = LogManager.getLogger();
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        xmlFileIsValid = false;
        logger.warn(e);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        xmlFileIsValid = false;
        logger.error(e);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        xmlFileIsValid = false;
        logger.fatal(e);
    }

    public boolean validate(String xmlFileName) throws IOException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = factory.newSchema(new File(SCHEMA_FILE_NAME));
            Validator validator = schema.newValidator();
            validator.setErrorHandler(this);
            validator.validate(new StreamSource(xmlFileName)) ;
            return xmlFileIsValid;
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        }
    }
}
