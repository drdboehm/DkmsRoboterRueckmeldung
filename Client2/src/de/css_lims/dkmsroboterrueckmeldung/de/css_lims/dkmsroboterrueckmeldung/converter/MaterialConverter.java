package de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.converter;

import com.beust.jcommander.IStringConverter;

import de.css_lims.dkmsroboterrueckmeldung.de.css_lims.dkmsroboterrueckmeldung.types.Material;

public class MaterialConverter implements IStringConverter<Material> {
    private static int position = 0;

    public MaterialConverter() {
        super();
    }

    @Override
    public Material convert(String string) {
        String[] args = string.split(":");
        if (args.length == 2)
            return new Material(String.valueOf(++position), args[0], args[1]);
        else if (args.length == 3)
            return new Material(args[0], args[1], args[2]);
        else
            return null;
    }

}
