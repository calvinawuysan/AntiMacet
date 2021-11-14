package id.kharisma.studio.antimacet;

public class Kecelakaan {
    String reporter;
    String wilayah;
    String penjelasan;
    String tanggal;

    public Kecelakaan(String reporter, String wilayah, String penjelasan, String tanggal) {
        this.reporter = reporter;
        this.wilayah = wilayah;
        this.penjelasan = penjelasan;
        this.tanggal = tanggal;
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

    public String getTanggal() {
        return tanggal;
    }
}
