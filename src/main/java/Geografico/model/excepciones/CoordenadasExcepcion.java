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
                msg = "La latitud es errónea";
                break;
            case 1:
                msg = "La longitud es errónea";
                break;
            case 2:
                msg = "La latitud y longitud son erróneas";
                break;
            case 3:
                msg = "La latitud y longitud no apuntan a una ubicación";
                break;
            default:
                msg = "Error inesperado en las coordenadas";
                break;
        }

        return msg;
    }
}
