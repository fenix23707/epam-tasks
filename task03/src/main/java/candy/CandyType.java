package candy;

public enum CandyType {
    CARAMEL("CARAMEL"), IRIS("IRIS"), CHOCOLATE("CHOCOLATE");

    private String name;

    private CandyType(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
