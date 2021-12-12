package Geografico.model.API;

import Geografico.model.Ciudad;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.List;

public interface APIOpenDataSoftInterface {
    public List<Ciudad> getCapitales() throws JSONException, FileNotFoundException;
    public List<Ciudad> getCapitalesCV() throws JSONException, FileNotFoundException;
}
