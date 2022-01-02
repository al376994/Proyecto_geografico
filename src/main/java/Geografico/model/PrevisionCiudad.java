package Geografico.model;

import java.time.LocalDate;
import java.util.List;

public class PrevisionCiudad {
    private Ciudad ciudad;
    private List<Prevision> previsionList;

    public PrevisionCiudad(Ciudad ciudad, List<Prevision> previsionList) {
        this.ciudad = ciudad;
        for(Prevision p:previsionList){
            int i = previsionList.indexOf(p);
            LocalDate l = java.time.LocalDate.now();
            LocalDate plusOne = l.plusDays(i);
            p.setDate(plusOne);
        }
        this.previsionList = previsionList;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public List<Prevision> getPrevisionList() {
        return previsionList;
    }

    public void setPrevisionList(List<Prevision> previsionList) {
        this.previsionList = previsionList;
    }
}
