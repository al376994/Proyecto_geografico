package Geografico.model;

public class Polucion {
//    "aqius": 21, //AQI value based on US EPA standard
//     "aqicn": 7, //AQI value based on China MEP standard
    private int aqius;
    private int aqcn;
    private String mainus;
    private String maincn;

    public Polucion(int aqius, int aqcn, String mainus, String maincn) {
        this.aqius = aqius;
        this.aqcn = aqcn;
        this.mainus = mainus;
        this.maincn = maincn;
    }

    public int getAqius() {
        return aqius;
    }

    public void setAqius(int aqius) {
        this.aqius = aqius;
    }

    public int getAqcn() {
        return aqcn;
    }

    public void setAqcn(int aqcn) {
        this.aqcn = aqcn;
    }

    @Override
    public String toString() {
        return "Polucion{" +
                "aqius=" + aqius +
                ", aqcn=" + aqcn +
                ", mainus='" + mainus + '\'' +
                ", maincn='" + maincn + '\'' +
                '}';
    }

    public String getMainus() {
        return mainus;
    }

    public void setMainus(String mainus) {
        this.mainus = mainus;
    }

    public String getMaincn() {
        return maincn;
    }

    public void setMaincn(String maincn) {
        this.maincn = maincn;
    }
}
