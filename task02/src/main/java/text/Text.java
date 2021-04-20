package text;

import text.components.CompositePartText;
import text.components.PartText;
import text.components.PunctuationMark;
import text.components.Word;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Text {
    private CompositePartText text;

    public CompositePartText getText() {
        return text;
    }

    public Text(String str) {
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
                partText = matcher.group();
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

    public Map<Integer,ArrayList<PartText>> findNumberOfOccurForEach(Set<? extends PartText> setTextParts) {
        Map<Integer, ArrayList<PartText>> mapPartText = new TreeMap<>();
        for (PartText partText: setTextParts) {
            int numberOfOccur = text.getNumberOfOccurrences(partText);
            if (mapPartText.containsKey(numberOfOccur)) {
                mapPartText.get(numberOfOccur).add(partText);
            } else {
                ArrayList<PartText> identicalPartsOfText = new ArrayList<>();
                identicalPartsOfText.add(partText);
                mapPartText.put(numberOfOccur,identicalPartsOfText);
            }
        }
        return mapPartText;
    }

}
