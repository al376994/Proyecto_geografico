package Geografico.model;

import java.util.Map;

public class Polucion {
//    "aqius": 21, //AQI value based on US EPA standard
//     "aqicn": 7, //AQI value based on China MEP standard
    private int aqius;
    private int aqcn;
    private String mainus;
    private String maincn;
    private final Map<String, String> tiposPolucion = Map.ofEntries(
            Map.entry("p1", "pm10"),
            Map.entry("p2", "pm2.5"),
            Map.entry("o3", "Ozono O3"),
            Map.entry("n2", "Dioxido de nitrogeno NO2"),
            Map.entry("s2", "Dioxido de sulfuro SO2"),
            Map.entry("co", "Monoxido de carbono XO")
    );
    private final String errorMessage;

    public Polucion(int aqius, int aqcn, String mainus, String maincn) {
        this.aqius = aqius;
        this.aqcn = aqcn;
        this.mainus = mainus;
        this.maincn = maincn;
        errorMessage = "";
    }

    public Polucion(String errorMessage) {
        this.errorMessage = errorMessage;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getTipoPolucion(boolean us) {
        String tipo;
        if (us) tipo = mainus;
        else tipo = maincn;

        return tiposPolucion.get(tipo);
    }

    public Map.Entry<String, String> getLevelOfConcern(boolean us) {
        int aq;
        if(us) aq = aqius;
        else aq = aqcn;

        if (aq < 51)  return Map.entry("LC_Good", "Calidad buena");;
        if (aq < 101) return Map.entry("LC_Moderate", "Calidad moderada");
        if (aq < 151) return Map.entry("LC_Unhealthy_1","Calidad insalubre para grupos sensibles");
        if (aq < 201) return Map.entry("LC_Unhealthy_2","Calidad insalubre para el público general");
        if (aq < 301) return Map.entry("LC_Unhealthy_3","Calidad altamenta insalubre");
        return Map.entry("LC_Hazardous","Calidad altamente contaminante, condiciones de emergencia");
    }
}
