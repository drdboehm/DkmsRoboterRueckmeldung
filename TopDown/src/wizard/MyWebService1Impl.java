package wizard;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.BindingType;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.soap.Addressing;
import javax.xml.ws.soap.SOAPBinding;

@WebService(name = "MyWebService1", targetNamespace = "http://wizard/", serviceName = "MyWebService1",
            portName = "MyWebService1Port", wsdlLocation = "/WEB-INF/wsdl/MyWebService1.wsdl")
@XmlSeeAlso({ ObjectFactory.class })
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@Addressing
public class MyWebService1Impl {
    public MyWebService1Impl() {
    }

    @RequestWrapper(localName = "sayHello", targetNamespace = "http://wizard/", className = "wizard.SayHello")
    @ResponseWrapper(localName = "sayHelloResponse", targetNamespace = "http://wizard/",
                     className = "wizard.SayHelloResponse")
    @Action(input = "http://wizard/MyWebService1/sayHelloRequest",
            output = "http://wizard/MyWebService1/sayHelloResponse")
    @WebMethod
    public String sayHello(@WebParam(name = "arg0") String arg0) {
        return "Hello " + arg0;
    }
}
