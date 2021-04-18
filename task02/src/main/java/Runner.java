import text.TextOperations;
import text.components.PartText;
import text.components.Word;

import java.util.*;

public class Runner {
    //TODO: найти нормальный споособ сортировать
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
    public static void main(String[] args) {
        String text = "Lorem Ipsum, is simply LoremLoremLoremL+375(43)123-34-32orem dummy Lorem ] Lorem Lorem \n"
                + "Lorem Ipsum, ,isfa@mail.ru simply dummy text, of the printing and typesetting industry. \n"
                + "Lorem Ipsum, is si+375(43)123-34-32mply dummy text, of the printing and typesetting industry. \n";

        TextOperations textOperations = new TextOperations(text);

        Set<Word> words = new HashSet<>();
        words.add(new Word("fa@mail.ru"));
        words.add(new Word("lorem"));
        words.add(new Word("Ipsum"));

        words.add(new Word("+375(43)123-34-32"));
        words.add(new Word("fafsa@mail.ru"));

        for (Map.Entry<PartText,Integer> partText: sortByValue(textOperations.findNumberOfOccurForEach(words)).entrySet()) {
            System.out.println(partText.getKey() + " " + partText.getValue());
        }
    }
}
