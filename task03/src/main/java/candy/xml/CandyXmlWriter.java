package candy.xml;

import candy.Candy;
import candy.ingredient.Ingredient;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import java.util.List;

public class CandyXmlWriter {
    public static void write(List<Candy> candies, OutputStream outputStream) {
        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter writer = null;
        try {
            writer = factory.createXMLStreamWriter(outputStream);
            writer.writeStartDocument("UTF-8","1.0");
            writer.writeStartElement("candies");
            writer.writeAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            writer.writeAttribute("xmlns","http://www.epam.by/task03/candies");
            writer.writeAttribute("xsi:schemaLocation","http://www.epam.by/task03/candies candies.xsd");
            for (Candy candy: candies) {
                writer.writeStartElement("candy");
                writer.writeAttribute("id",candy.getId());

                writer.writeStartElement("name");
                writer.writeCharacters(candy.getName());
                writer.writeEndElement();

                writer.writeStartElement("energy");
                writer.writeCharacters(
                        String.valueOf(candy.getEnergy().getValue()));
                writer.writeEndElement();

                writer.writeStartElement("type");
                writer.writeCharacters(candy.getCandyType().getName());
                writer.writeEndElement();

                writer.writeStartElement("ingredients");
                for (Ingredient ingredient: candy.getIngredients()) {
                    writer.writeStartElement(ingredient.getName());
                    writer.writeCharacters(ingredient.getValue());
                    writer.writeEndElement();
                }
                writer.writeEndElement();

                writer.writeStartElement("value");
                writer.writeAttribute("protein",
                        String.valueOf(candy.getValue().getProtein()));
                writer.writeAttribute("fats",
                        String.valueOf(candy.getValue().getFats()));
                writer.writeAttribute("carbohydrates",
                        String.valueOf(candy.getValue().getCarbohydrates()));
                writer.writeEndElement();

                writer.writeStartElement("production");
                writer.writeCharacters(candy.getProduction());
                writer.writeEndElement();

                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }
}
