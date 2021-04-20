package text;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;

public class TextOperationsTest {
    @Test
    public void makeSentencesTestDivideIntoSentences1() {
        String text = "word, word , word dd , word         word         word ";
        Text textOperations = new Text(text);
        String expected = "word, word, word dd, word word word";
        assertEquals(true,expected.equals(textOperations.getText().toString()));
    }

    @Test
    public void makeSentencesTestDivideIntoSentences2() {
        String text = "word, word , word dd , word         word         word       . worddd , , worddd ,,worddd, worddd";
        Text textOperations = new Text(text);
        String expected = "word, word, word dd, word word word. worddd,, worddd,, worddd, worddd";
        assertEquals(true,expected.equals(textOperations.getText().toString()));
    }

    @Test
    public void makeSentencesTestDivideIntoSentences3() {
        String text = "";
        Text textOperations = new Text(text);
        String expected = "";
        assertEquals(true,expected.equals(textOperations.getText().toString()));
    }

    @Test
    public void makeSentencesTestDivideIntoSentences4() {
        String text = "+375(32)123-42-12";
        Text textOperations = new Text(text);
        String expected = "+375(32)123-42-12";
        assertEquals(true,expected.equals(textOperations.getText().toString()));
    }

    @Test
    public void makeSentencesTestDivideIntoSentences5() {
        String text = "sfa-fsa@mail.ru";
        Text textOperations = new Text(text);
        String expected = "sfa-fsa@mail.ru";
        assertEquals(true,expected.equals(textOperations.getText().toString()));
    }
}
