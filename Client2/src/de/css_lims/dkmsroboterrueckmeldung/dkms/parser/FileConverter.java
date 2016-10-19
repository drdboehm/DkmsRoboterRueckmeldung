package de.css_lims.dkmsroboterrueckmeldung.dkms.parser;

import com.beust.jcommander.IStringConverter;

import java.io.File;

public class FileConverter implements IStringConverter<File> {

     @Override
     public File convert(String value) {
         // TODO Implement this method
         return new File(value);
     }
}