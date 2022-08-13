package com.h2sm.webservice.service;

import com.h2sm.webservice.dtos.EquationRequestDTO;
import com.h2sm.webservice.dtos.EquationResponseDTO;
import org.springframework.stereotype.Service;

import javax.xml.soap.*;
import java.util.Iterator;

@Service
public class RequestService {

    public EquationResponseDTO makeRequest(EquationRequestDTO dto) {

        String soapEndpointUrl = "http://localhost:8081/ws";
        String soapAction = "EquationRequest";

        return callSoapWebService(soapEndpointUrl, soapAction, dto);

    }

    private EquationResponseDTO callSoapWebService(String soapEndpointUrl,
                                                   String soapAction,
                                                   EquationRequestDTO dto) {
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, dto), soapEndpointUrl);

            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
            return parseAnswer(soapResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private SOAPMessage createSOAPRequest(String soapAction, EquationRequestDTO dto) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage, dto);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        return soapMessage;
    }

    private void createSoapEnvelope(SOAPMessage soapMessage, EquationRequestDTO dto) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "EquationRequest";
        String myNamespaceURI = "http://www.h2sm.com/testtask/objects";

        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("EquationRequest", myNamespace);

        SOAPElement aElem = soapBodyElem.addChildElement("a", myNamespace);
        aElem.addTextNode(Integer.toString(dto.getA()));

        SOAPElement bElem = soapBodyElem.addChildElement("b", myNamespace);
        bElem.addTextNode(Integer.toString(dto.getB()));

        SOAPElement cElem = soapBodyElem.addChildElement("c", myNamespace);
        cElem.addTextNode(Integer.toString(dto.getC()));

    }

    private EquationResponseDTO parseAnswer(SOAPMessage soapResponse) throws SOAPException {
        var resp = new EquationResponseDTO();

        var body = soapResponse.getSOAPBody();
        Iterator replyIt = body.getChildElements();

        while(replyIt.hasNext()) {

            SOAPBodyElement replysbe = (SOAPBodyElement) replyIt.next();
            Iterator replyIt2 = replysbe.getChildElements();

            while (replyIt2.hasNext()){
                var ele = (SOAPElement) replyIt2.next();
                var name = ele.getElementName().getLocalName();

                switch (name) {
                    case "formula":
                        resp.setFormula(ele.getValue());
                        break;
                    case "D":
                        resp.setD(Double.parseDouble(ele.getValue()));
                        break;
                    case "x1":
                        resp.setX1(Double.parseDouble(ele.getValue()));
                        break;
                    case "x2":
                        resp.setX2(Double.parseDouble(ele.getValue()));
                        break;
                    default:
                        resp.setFormula(ele.getValue());
                        break;
                }
            }
        }

        return resp;
    }
}


