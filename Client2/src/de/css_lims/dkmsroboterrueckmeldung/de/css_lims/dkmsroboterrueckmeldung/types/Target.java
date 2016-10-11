package de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.types;


public class Target {
    private String position;
    private String targetId;
    private String name;


    public Target(String position, String targetId, String name) {
        this.position = position;
        this.targetId = targetId;
        this.name = name;
    }

    @Override
    public String toString() {
        // TODO Implement this method
        return String.format("t%s: %s - %s ", position, targetId, name).toString();
    }

    public String getPosition() {
        return position;
    }

    public String getTargetId() {
        return targetId;
    }

    public String getName() {
        return name;
    }
}
