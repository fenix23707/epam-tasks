package text.components;

import java.util.ArrayList;

public class CompositePartText implements PartText{
    private ArrayList<PartText> components = new ArrayList<>();

    public void addComponent(PartText partText) {
        components.add(partText);
    }

    @Override
    public int getNumberOfOccurrences(PartText partText) {
        int count = 0;
        for (PartText component: components) {
            count+=component.getNumberOfOccurrences(partText);
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        for (PartText partText: components) {
            if (partText.getClass() == PunctuationMark.class) {
                strBuilder.replace(strBuilder.length()-1,strBuilder.length(),"");
            }
            strBuilder.append(partText).append(" ");
        }
        return strBuilder.toString().trim();
    }
}
