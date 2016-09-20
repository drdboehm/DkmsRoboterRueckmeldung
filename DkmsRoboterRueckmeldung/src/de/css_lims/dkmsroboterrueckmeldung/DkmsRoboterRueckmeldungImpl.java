package de.css_lims.dkmsroboterrueckmeldung;

import java.math.BigInteger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import javax.xml.bind.annotation.XmlSeeAlso;

@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ ObjectFactory.class })
@WebService(name = "DkmsRoboterRueckmeldung", serviceName = "DkmsRoboterRueckmeldung",
            targetNamespace = "http://www.css-lims.de/DkmsRoboterRueckmeldung/", portName = "DkmsRoboterRueckmeldung",
            wsdlLocation = "/WEB-INF/wsdl/DkmsRoboterRueckmeldung.wsdl")
public class DkmsRoboterRueckmeldungImpl {
    public DkmsRoboterRueckmeldungImpl() {
    }

    @WebResult(name = "RueckmeldeungResponse", partName = "RueckmeldeungResponse",
               targetNamespace = "http://www.css-lims.de/DkmsRoboterRueckmeldung/")
    @WebMethod(operationName = "DkmsRoboterRueckmeldung",
               action = "http://www.css-lims.de/DkmsRoboterRueckmeldung/DkmsRoboterRueckmeldung")
    public RueckmeldeungResponse dkmsRoboterRueckmeldung(@WebParam(name = "RueckmeldungRequest",
                                                                   partName = "RueckmeldungRequest",
                                                                   targetNamespace =
                                                                   "http://www.css-lims.de/DkmsRoboterRueckmeldung/")
                                                         RueckmeldungRequest rueckmeldungRequest) {
        RueckmeldeungResponse rueckmeldungResponse = new RueckmeldeungResponse();
        rueckmeldungResponse.setMeldung("Success");
        rueckmeldungResponse.setRequestId(rueckmeldungRequest.requestId);
        rueckmeldungResponse.setStatus(new BigInteger("1000"));
        return rueckmeldungResponse;

    }
}
