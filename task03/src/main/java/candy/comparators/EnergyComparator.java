package candy.comparators;

import candy.Candy;

import java.util.Comparator;

public class EnergyComparator implements Comparator<Candy> {
    @Override
    public int compare(Candy o1, Candy o2) {
        return o1.getEnergy().getValue() - o2.getEnergy().getValue();
    }
}
