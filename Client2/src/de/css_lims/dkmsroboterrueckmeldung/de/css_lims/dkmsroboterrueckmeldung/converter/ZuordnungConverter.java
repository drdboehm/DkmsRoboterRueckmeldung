package de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.converter;

import com.beust.jcommander.IStringConverter;

import de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.parser.DkmsRoboterRueckmeldungCLI;
import de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.types.Material;
import de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.types.Target;
import de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.types.Zuordnung;

public class ZuordnungConverter implements IStringConverter<Zuordnung> {

    public ZuordnungConverter() {
        super();
    }

    @Override
    public Zuordnung convert(String zuordnung) {
        Zuordnung z = new Zuordnung();
        String[] args = zuordnung.split(":");
        String position;
        for (String s : args) {
            if (s.startsWith("m")) {
                position = s.substring(1);
                Material m = DkmsRoboterRueckmeldungCLI.getMaterialByPosition(position);
                z.getMaterialList().add(m);
            }
            if (s.startsWith("t")) {
                position = s.substring(1);
                Target t = DkmsRoboterRueckmeldungCLI.getTargetByPosition(position);
                z.getTargetList().add(t);
            }
        }
        return z;
    }
}
