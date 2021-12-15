package Geografico.model.excepciones;

public class CoordenadasExcepcion extends Exception {
    private int codigoError;
    //0 si la latitud es incorrecta
    //1 si la longitud es incorrecta
    //2 si ambas son incorrectas

    public CoordenadasExcepcion(int codigoError){
        super();
        this.codigoError = codigoError;
    }

    @Override
    public String getMessage(){
        String msg = "";
        switch (codigoError){
            case 0:
                msg = "La latitud esta fuera de rango (90 a -90)";
                break;
            case 1:
                msg = "La longitud esta fuera de rango (180 a -180)";
                break;
            case 2:
                msg = "La latitud y longitud estan fuera de rango (90 a -90) y (180 a -180)";
                break;
            case 3:
                msg = "Las coordenadas no apuntan a una ubicación válida";
                break;
            default:
                msg = "Error inesperado en la introducción de coordenadas";
                break;
        }

        return msg;
    }
}
