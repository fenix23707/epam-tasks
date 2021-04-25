package text.components;

import java.util.regex.Pattern;

public class Word implements PartText {
    private String content;
    public static final String regex =
                    "[a-zA-Z0-9_.-]+@\\w+\\.\\w+"//почта
                    +"|\\+\\d{3}\\(\\d{2}\\)\\d{3}(-\\d{2}){2}"//телефон
                    +"|[^\\s\\t\\n,.:;!()\\[\\]?]+";//слово

    public Word(String word) {
        setContent(word);
    }

    @Override
    public int getNumberOfOccurrences(PartText partText) {
        if(content.equalsIgnoreCase(partText.toString())) {
            return 1;
        }
        return 0;
    }

    protected void setContent(String content) throws IllegalArgumentException {
        if (isWord(content)) {
            this.content = content;
        } else {
            throw new IllegalArgumentException(content + " is not word");
        }
    }

    public static boolean isWord(String content) {
        return Pattern.matches(regex,content);
    }

    @Override
    public String toString() {
        return content;
    }
}
