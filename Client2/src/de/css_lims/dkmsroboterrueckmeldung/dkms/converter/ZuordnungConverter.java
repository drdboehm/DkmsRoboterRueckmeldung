package de.css_lims.dkmsroboterrueckmeldung.dkms.converter;

import com.beust.jcommander.IStringConverter;

import de.css_lims.dkmsroboterrueckmeldung.dkms.parser.DkmsRoboterRueckmeldungCLI;
import de.css_lims.dkmsroboterrueckmeldung.dkms.types.Material;
import de.css_lims.dkmsroboterrueckmeldung.dkms.types.Target;
import de.css_lims.dkmsroboterrueckmeldung.dkms.types.Zuordnung;

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
