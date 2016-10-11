package de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.types;

import java.util.ArrayList;
import java.util.List;

public class Zuordnung {
    private List<Material> materialList = new ArrayList<>(); 
    private List<Target> targetList = new ArrayList<>(); 
    
    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setTargetList(List<Target> targetList) {
        this.targetList = targetList;
    }

    public List<Target> getTargetList() {
        return targetList;
    }
    

    @Override
    public String toString() {
        // TODO Implement this method
        StringBuffer sb = new StringBuffer();
        for (Material  m: materialList)
            sb.append(m.toString() + "\n");
        for (Target  t: targetList)
            sb.append(t.toString() + "\n");
        return sb.toString();
    }
}
