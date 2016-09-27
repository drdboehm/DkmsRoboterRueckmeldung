package wizard;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;


@WebService(name = "MyWebService1", serviceName = "MyWebService1", portName = "MyWebService1Port")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class HelloService {
    public HelloService() {
        super();
    }

    @WebMethod
    public String sayHello(@WebParam(name = "arg0") String s) {
        return "Hello " +s;
    }
}
