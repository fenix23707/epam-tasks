import candy.Candy;
import candy.comparators.EnergyComparator;
import candy.xml.CandyXmlReader;
import candy.xml.CandyXmlValidator;
import candy.xml.CandyXmlWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class App {

    public static void main( String[] args ) {
        CandyXmlValidator validator =  new CandyXmlValidator();
        try {
            String xmlFileName = "src\\main\\resources\\candies.xml";
            if (validator.validate(xmlFileName)) {
                FileInputStream inputStream =  new FileInputStream(xmlFileName);
                List<Candy> candies = CandyXmlReader.read(inputStream);
                Collections.sort(candies,new EnergyComparator());
                for (Candy candy: candies) {
                    System.out.println(candy);
                }
                FileOutputStream outputStream = new FileOutputStream(
                        "src\\main\\resources\\newCandies.xml");
                CandyXmlWriter.write(candies,outputStream);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
