
package com.baeldung.jaxws.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "EmployeeService", targetNamespace = "http://bottomup.server.jaxws.baeldung.com/", wsdlLocation = "http://localhost:8080/employeeservice?wsdl")
public class EmployeeService_Service
    extends Service
{

    private final static URL EMPLOYEESERVICE_WSDL_LOCATION;
    private final static WebServiceException EMPLOYEESERVICE_EXCEPTION;
    private final static QName EMPLOYEESERVICE_QNAME = new QName("http://bottomup.server.jaxws.baeldung.com/", "EmployeeService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/employeeservice?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        EMPLOYEESERVICE_WSDL_LOCATION = url;
        EMPLOYEESERVICE_EXCEPTION = e;
    }

    public EmployeeService_Service() {
        super(__getWsdlLocation(), EMPLOYEESERVICE_QNAME);
    }

    public EmployeeService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), EMPLOYEESERVICE_QNAME, features);
    }

    public EmployeeService_Service(URL wsdlLocation) {
        super(wsdlLocation, EMPLOYEESERVICE_QNAME);
    }

    public EmployeeService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, EMPLOYEESERVICE_QNAME, features);
    }

    public EmployeeService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public EmployeeService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns EmployeeService
     */
    @WebEndpoint(name = "EmployeeServiceImplPort")
    public EmployeeService getEmployeeServiceImplPort() {
        return super.getPort(new QName("http://bottomup.server.jaxws.baeldung.com/", "EmployeeServiceImplPort"), EmployeeService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns EmployeeService
     */
    @WebEndpoint(name = "EmployeeServiceImplPort")
    public EmployeeService getEmployeeServiceImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://bottomup.server.jaxws.baeldung.com/", "EmployeeServiceImplPort"), EmployeeService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (EMPLOYEESERVICE_EXCEPTION!= null) {
            throw EMPLOYEESERVICE_EXCEPTION;
        }
        return EMPLOYEESERVICE_WSDL_LOCATION;
    }

}
