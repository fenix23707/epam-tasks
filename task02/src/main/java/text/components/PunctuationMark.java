package text.components;


import java.util.regex.Pattern;

public class PunctuationMark implements PartText{
    private String content;
    public static final String regexEnd = "\\.+|!+|\\?+ ";
    public static final String regex = "[,;:\\-()\"{}\\[\\]]|"+regexEnd;
    public PunctuationMark(String punctuationMark) {
        setContent(punctuationMark);
    }

    @Override
    public int getNumberOfOccurrences(PartText partText) {
        if (content.equalsIgnoreCase(partText.toString())) {
           return 1;
        }
        return 0;
    }

    protected void setContent(String content) throws IllegalArgumentException {
        if (isPunctuationMark(content)) {
           this.content = content;
        } else {
            throw new IllegalArgumentException(content + " is not punctuation mark");
        }
    }

    public static boolean isPunctuationMark(String content) {
        return Pattern.matches(regex,content);
    }

    public static boolean isEndPunctuationMark(String content) {
        return Pattern.matches(regexEnd,content);
    }

    @Override
    public String toString() {
        return content;
    }
}
