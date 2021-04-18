package text;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

public class TextOperationsTest {
    @Test
    public void makeSentencesTestDivideIntoSentences1() {
        String text = "word, word , word dd , word         word         word ";
        TextOperations textOperations = new TextOperations(text);
        String expected = "word, word, word dd, word word word";
        assertEquals(true,expected.equals(textOperations.getText().toString()));
    }

    @Test
    public void makeSentencesTestDivideIntoSentences2() {
        String text = "word, word , word dd , word         word         word       . worddd , , worddd ,,worddd, worddd";
        TextOperations textOperations = new TextOperations(text);
        String expected = "word, word, word dd, word word word. worddd,, worddd,, worddd, worddd";
        assertEquals(true,expected.equals(textOperations.getText().toString()));
    }

    @Test
    public void makeSentencesTestDivideIntoSentences3() {
        String text = "";
        TextOperations textOperations = new TextOperations(text);
        String expected = "";
        assertEquals(true,expected.equals(textOperations.getText().toString()));
    }

    @Test
    public void makeSentencesTestDivideIntoSentences4() {
        String text = "+375(32)123-42-12";
        TextOperations textOperations = new TextOperations(text);
        String expected = "+375(32)123-42-12";
        assertEquals(true,expected.equals(textOperations.getText().toString()));
    }

    @Test
    public void makeSentencesTestDivideIntoSentences5() {
        String text = "sfa-fsa@mail.ru";
        TextOperations textOperations = new TextOperations(text);
        String expected = "sfa-fsa@mail.ru";
        assertEquals(true,expected.equals(textOperations.getText().toString()));
    }
}
