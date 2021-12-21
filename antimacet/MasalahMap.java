package id.kharisma.studio.antimacet;

public class MasalahMap {
    String reporter;
    String penjelasan;

    public MasalahMap(String reporter, String penjelasan) {
        this.reporter = reporter;
        this.penjelasan = penjelasan;
    }

    public String getReporter() {
        return reporter;
    }

    public String getPenjelasan() {
        return penjelasan;
    }
}
