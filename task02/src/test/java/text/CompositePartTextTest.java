package text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import text.components.CompositePartText;
import text.components.PunctuationMark;
import text.components.Word;

public class CompositePartTextTest {
    private CompositePartText sentence = new CompositePartText();

    @Before
    public void setUp() {
        Word word1 = new Word("yes");
        Word word4 = new Word("yes");
        Word word2 = new Word("no");
        Word word3 = new Word("yefsas");
        PunctuationMark mark1 = new PunctuationMark(",");
        PunctuationMark mark2 = new PunctuationMark(".");


        sentence.addComponent(word1);
        sentence.addComponent(word2);
        sentence.addComponent(word3);
        sentence.addComponent(mark1);
        sentence.addComponent(word1);
        sentence.addComponent(word4);
        sentence.addComponent(mark2);
    }
    @Test
    public void getNumberOfOccurrencesTestForSentence() {
        assertEquals(3,sentence.getNumberOfOccurrences(new Word("yes")));
    }

    @Test
    public void getNumberOfOccurrencesTestForParagraph() {
        CompositePartText paragraph = new CompositePartText();
        paragraph.addComponent(sentence);
        paragraph.addComponent(sentence);
        paragraph.addComponent(sentence);

        assertEquals(9,paragraph.getNumberOfOccurrences(new Word("yes")));
    }

    @Test
    public void toStringTestForSentence() {
        assertEquals(true,"yes no yefsas, yes yes.".equals(sentence.toString()));
    }

    @Test
    public void toStringTestForParagraph() {
        CompositePartText paragraph = new CompositePartText();
        paragraph.addComponent(sentence);
        paragraph.addComponent(sentence);
        assertEquals(true,"yes no yefsas, yes yes. yes no yefsas, yes yes.".equals(paragraph.toString()));
    }

    @Test
    public void getNumberOfOccurrencesTestForSentencePhone() {
        Word phone = new Word("+375(32)213-32-12");
        sentence.addComponent(phone);
        sentence.addComponent(new Word("+675(32)277-32-13"));
        sentence.addComponent(new Word("+675(32)277-32-13"));
        sentence.addComponent(phone);
        sentence.addComponent(phone);

        assertEquals(3,sentence.getNumberOfOccurrences(phone));
    }

    @Test
    public void getNumberOfOccurrencesTestForSentencePhoneZero() {
        Word phone = new Word("+375(32)213-32-12");
        sentence.addComponent(phone);
        sentence.addComponent(new Word("+675(32)277-32-13"));
        sentence.addComponent(new Word("+675(32)277-32-13"));
        sentence.addComponent(phone);
        sentence.addComponent(phone);
        assertEquals(0,sentence.getNumberOfOccurrences(new Word("+345(32)213-32-12")));
    }

    @Test
    public void getNumberOfOccurrencesTestForParagraphMail() {
        Word mail = new Word("fsa@mail.ru");
        sentence.addComponent(mail);
        sentence.addComponent(new Word("fsa@fas.fs"));
        sentence.addComponent(new Word("fsagds@sa.fsaf"));
        sentence.addComponent(mail);
        sentence.addComponent(mail);

        CompositePartText paragraph = new CompositePartText();
        paragraph.addComponent(sentence);
        paragraph.addComponent(sentence);
        paragraph.addComponent(sentence);

        assertEquals(9,paragraph.getNumberOfOccurrences(mail));
    }

    @Test
    public void getNumberOfOccurrencesTestForParagraphMailZero() {
        Word mail = new Word("fsa@mail.ru");
        sentence.addComponent(mail);
        sentence.addComponent(new Word("fsa@fas.fs"));
        sentence.addComponent(new Word("fsagds@sa.fsaf"));
        sentence.addComponent(mail);
        sentence.addComponent(mail);

        CompositePartText paragraph = new CompositePartText();
        paragraph.addComponent(sentence);
        paragraph.addComponent(sentence);
        paragraph.addComponent(sentence);

        assertEquals(0,paragraph.getNumberOfOccurrences(new Word("fsafsa@fas.fs")));
    }
}
