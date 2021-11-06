package id.kharisma.studio.antimacet;

public class Kecelakaan {
    String reporter;
    String wilayah;
    String penjelasan;

    public Kecelakaan(String reporter, String wilayah, String penjelasan) {
        this.reporter = reporter;
        this.wilayah = wilayah;
        this.penjelasan = penjelasan;
    }

    public String getReporter() {
        return reporter;
    }

    public String getWilayah() {
        return wilayah;
    }

    public String getPenjelasan() {
        return penjelasan;
    }
}
