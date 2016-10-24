package de.css_lims.dkmsroboterrueckmeldung.dkms.converter;

import com.beust.jcommander.IStringConverter;

import de.css_lims.dkmsroboterrueckmeldung.dkms.types.Target;

public class TargetConverter implements IStringConverter<Target> {
    public TargetConverter() {
        super();
    }
    private static int position = 0;

    @Override
    public Target convert(String string) {
        String[] args = string.split(":");
        if (args.length == 2)
            return new Target(String.valueOf(++position), args[0], args[1]);
        else if (args.length == 3)
            return new Target(args[0], args[1], args[2]);
        else
            return null;
    }
}
