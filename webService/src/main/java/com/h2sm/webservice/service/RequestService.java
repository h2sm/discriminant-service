package com.h2sm.webservice.service;

import com.h2sm.webservice.dtos.EquationRequestDTO;
import com.h2sm.webservice.dtos.EquationResponseDTO;
import org.springframework.stereotype.Service;

import javax.xml.soap.*;

@Service
public class RequestService {

    public EquationResponseDTO makeRequest(EquationRequestDTO dto) {

        String soapEndpointUrl = "http://localhost:8081/ws";
        String soapAction = "EquationRequest";

        callSoapWebService(soapEndpointUrl, soapAction, dto);

        return EquationResponseDTO
                .builder()
                .build();
    }

    private void callSoapWebService(String soapEndpointUrl,
                                    String soapAction,
                                    EquationRequestDTO dto) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, dto), soapEndpointUrl);

            SOAPEnvelope envelope = soapResponse.getSOAPPart().getEnvelope();

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
    }


    private SOAPMessage createSOAPRequest(String soapAction, EquationRequestDTO dto) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage, dto);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

    private void createSoapEnvelope(SOAPMessage soapMessage, EquationRequestDTO dto) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "EquationRequest";
        String myNamespaceURI = "http://www.h2sm.com/testtask/objects";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            /*
            Constructed SOAP Request Message:
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:myNamespace="http://www.webserviceX.NET">
                <SOAP-ENV:Header/>
                <SOAP-ENV:Body>
                    <myNamespace:GetInfoByCity>
                        <myNamespace:USCity>New York</myNamespace:USCity>
                    </myNamespace:GetInfoByCity>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("EquationRequest", myNamespace);

        SOAPElement aElem = soapBodyElem.addChildElement("a", myNamespace);
        aElem.addTextNode(Integer.toString(dto.getA()));

        SOAPElement bElem = soapBodyElem.addChildElement("b", myNamespace);
        bElem.addTextNode(Integer.toString(dto.getB()));

        SOAPElement cElem = soapBodyElem.addChildElement("c", myNamespace);
        cElem.addTextNode(Integer.toString(dto.getC()));

    }
}


