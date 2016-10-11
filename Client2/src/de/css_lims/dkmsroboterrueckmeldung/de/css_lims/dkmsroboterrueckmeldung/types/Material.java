package de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.types;


public class Material {
    private String position;
    private String materialId;
    private String name;


    public Material(String position, String materialId, String name) {
        this.position = position;
        this.materialId = materialId;
        this.name = name;
    }

    @Override
    public String toString() {
        // TODO Implement this method
        return String.format("m%s: %s - %s ", position, materialId, name).toString();
    }

    public String getPosition() {
        return position;
    }

    public String getMaterialId() {
        return materialId;
    }

    public String getName() {
        return name;
    }
}
