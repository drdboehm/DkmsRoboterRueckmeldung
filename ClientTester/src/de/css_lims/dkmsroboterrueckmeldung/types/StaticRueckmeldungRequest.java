package de.css_lims.dkmsroboterrueckmeldung.types;

import de.css_lims.dkmsroboterrueckmeldung.RueckmeldungRequest;

import java.math.BigInteger;

import java.util.GregorianCalendar;


public class StaticRueckmeldungRequest extends RueckmeldungRequest {
private GregorianCalendar cal;  
private String requestId;
private BigInteger requestTyp;
private String rackId;
private String roboterId;
private String userBedienerId;
private String programmId;
private String hinweisText;
private String userKontrolleurId;

private DynamischerTeil dynTeil;
    
    public StaticRueckmeldungRequest() {
        super();
    }
    
}
