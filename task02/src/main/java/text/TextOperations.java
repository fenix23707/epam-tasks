package text;

import text.components.CompositePartText;
import text.components.PartText;
import text.components.PunctuationMark;
import text.components.Word;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextOperations {
    private CompositePartText text;

    public CompositePartText getText() {
        return text;
    }

    public TextOperations(String str) {
        text = makeParagraph(str);
    }

    private CompositePartText makeParagraph(String text) {
        ArrayList<CompositePartText> sentences = makeSentences(text);
        if (sentences.size() == 1) {
           return sentences.get(0);
        }

        CompositePartText paragraph = new CompositePartText();
        for (CompositePartText sentence: sentences) {
            paragraph.addComponent(sentence);
        }
        return paragraph;
    }

    private ArrayList<CompositePartText> makeSentences(String text) {
        Pattern pattern = Pattern.compile(Word.regex + "|" + PunctuationMark.regex);
        Matcher matcher = pattern.matcher(text);
        ArrayList<CompositePartText> sentences = new ArrayList<>();
        while (matcher.find()) {
            CompositePartText sentence = new CompositePartText();
            sentences.add(sentence);
            String partText;
            do {
                partText = matcher.group()/*text.substring(matcher.start(), matcher.end())*/;
                if (Word.isWord(partText)) {
                    sentence.addComponent(new Word(partText));
                } else if (PunctuationMark.isPunctuationMark(partText)) {
                    sentence.addComponent(new PunctuationMark(partText));
                    if(PunctuationMark.isEndPunctuationMark(partText)) {
                        break;
                    }
                }
            }while(matcher.find());
        }
        return sentences;
    }

    public Map<PartText,Integer> findNumberOfOccurForEach(Set<? extends PartText> textParts) {
        Map<PartText, Integer> mapPartText = new HashMap<>(textParts.size());
        for (PartText partText: textParts) {
            mapPartText.put(partText, text.getNumberOfOccurrences(partText));
        }
        return mapPartText;
    }

}
