package candy.xml;

import candy.Candy;
import candy.CandyType;
import candy.Energy;
import candy.NutritionalValue;
import candy.ingredient.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CandyXmlReader {
    public static List<Candy> read(InputStream inputStream) {
        List<Candy> candies = new ArrayList<>();
        Candy candy = null;
        XMLStreamReader reader = null;
        XMLInputFactory factory = XMLInputFactory.newFactory();
        try {
            reader = factory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                switch (type) {
                    case XMLStreamConstants.START_ELEMENT: {
                        String tagName = reader.getLocalName();
                        switch (tagName) {
                            case "candy": {
                                candy = new Candy();
                                candy.setId(reader.getAttributeValue(null,"id"));
                                break;
                            }
                            case "name": {
                                candy.setName(reader.getElementText());
                                break;
                            }
                            case "energy": {
                                candy.setEnergy(new Energy(reader.getElementText()));
                                break;
                            }
                            case "type": {
                                CandyType typ = CandyType.valueOf(reader.getElementText());
                                candy.setCandyType(typ);
                                break;
                            }
                            case "water": {
                                candy.addIngredient(new Water(reader.getElementText()));
                                break;
                            }
                            case "sugar": {
                                candy.addIngredient(new Sugar(reader.getElementText()));
                                break;
                            }
                            case "fructose": {
                                candy.addIngredient(new Fructose(reader.getElementText()));
                                break;
                            }
                            case "chocolateType": {
                                candy.addIngredient(
                                        new ChocolateType(reader.getElementText()));
                                break;
                            }
                            case "vanillin": {
                                candy.addIngredient(new Vanillin(reader.getElementText()));
                                break;
                            }
                            case "value": {
                                NutritionalValue value = new NutritionalValue(
                                        reader.getAttributeValue(null,"protein"),
                                        reader.getAttributeValue(null,"fats"),
                                        reader.getAttributeValue(null,"carbohydrates")
                                );
                                candy.setValue(value);
                                break;
                            }
                            case "production": {
                                candy.setProduction(reader.getElementText());
                                break;
                            }
                        }
                        break;
                    }
                    case XMLStreamConstants.END_ELEMENT: {
                        String tagName =reader.getLocalName();
                        if ("candy".equals(tagName)) {
                           candies.add(candy);
                        }
                        break;
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
        return candies;
    }
}
