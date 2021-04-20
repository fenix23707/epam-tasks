import text.Text;
import text.components.PartText;
import text.components.Word;

import java.util.*;

public class Runner {
    public static void main(String[] args) {
        String str = "Lorem Ipsum, is  simply LoremLoremLoremL: +375(43)123-34-32orem dummy Lorem Lorem Lorem "
                + "Lorem Ipsum ,isfa@mail.ru simply dummy text, of the printing and typesetting industry. "
                + "Lorem Ipsum, is is +375(43)123-34-32 dummy text, of the printing and typesetting industry. ";

        Text text = new Text(str);

        Set<Word> words = new HashSet<>();
        words.add(new Word("isfa@mail.ru"));
        words.add(new Word("lorem"));
        words.add(new Word("Ipsum"));
        words.add(new Word("+375(43)123-34-32"));
        words.add(new Word("fafsa@mail.ru"));
        words.add(new Word("is"));


        Map<Integer,ArrayList<PartText>> mapPartsText = text.findNumberOfOccurForEach(words);

        for (Map.Entry<Integer,ArrayList<PartText>> identicalPartsOfText:mapPartsText.entrySet()) {
            for (PartText partText: identicalPartsOfText.getValue()) {
                System.out.println(identicalPartsOfText.getKey() + " " + partText);
            }
        }
        System.out.println(text.getText());
    }
}
