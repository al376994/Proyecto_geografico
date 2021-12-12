package Geografico.model.API;

import Geografico.model.Ciudad;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class APIOpenDataSoft implements  APIOpenDataSoftInterface{
    public APIOpenDataSoft(){}

    public List<Ciudad> getCapitales() throws JSONException, FileNotFoundException {
        List<Ciudad> ciudades = new ArrayList<>();
        try{
//            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            String text = new String(Files.readAllBytes(Paths.get(".\\src\\main\\java\\Geografico\\model\\data\\capitales.json")), StandardCharsets.UTF_8);
            JSONObject obj = new JSONObject(text);
            JSONArray array = obj.getJSONArray("Sheet");
            int i = 0;
            while(i < array.length()){
                JSONObject prov = (JSONObject) array.get(i);
                String ciudad = prov.getString("Texto");
                ciudad = ciudad.replace(" ","%20");
//                System.out.println(ciudad);
                String provincia = prov.getString("Provincia");
                String comunidad = prov.getString("CCAA");
                Ciudad c = new Ciudad(ciudad, provincia, comunidad,"Spain");
//                System.out.println(c.toString());
                ciudades.add(c);
                i++;
            }

        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
        return ciudades;
    }

    public List<Ciudad> getCapitalesCV() throws JSONException, FileNotFoundException {
        List<Ciudad> ciudades = new ArrayList<>();
        try{
//            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            String text = new String(Files.readAllBytes(Paths.get(".\\src\\main\\java\\Geografico\\model\\data\\capitalesCV.json")), StandardCharsets.UTF_8);
            JSONObject obj = new JSONObject(text);
            JSONArray array = obj.getJSONArray("Sheet");
            int i = 0;
            while(i < array.length()){
                JSONObject prov = (JSONObject) array.get(i);
                String ciudad = prov.getString("Texto");
                ciudad = ciudad.replace(" ","%20");
//                System.out.println(ciudad);
                String provincia = prov.getString("Provincia");
                String comunidad = prov.getString("CCAA");
                Ciudad c = new Ciudad(ciudad, provincia, comunidad,"Spain");
//                System.out.println(c.toString());
                ciudades.add(c);
                i++;
            }

        }
        catch(Exception ex){
            System.out.println(ex.toString());
        }
        return ciudades;
    }
}
