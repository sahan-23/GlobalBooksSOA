package com.yourcompany.catalog.security;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;

import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

public class AuthInterceptor extends AbstractSoapInterceptor {

    public AuthInterceptor() {
        super(Phase.PRE_PROTOCOL);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        try {
            SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
            SOAPHeader header = soapMessage.getSOAPHeader();
            if (header == null) {
                throw new SecurityException("SOAP header එකක් නොමැත.");
            }

            // WS-Security header එකේ UsernameToken element එක සොයාගන්න
            Element usernameToken = (Element) header.getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken").item(0);
            if (usernameToken == null) {
                throw new SecurityException("WS-Security header එකේ UsernameToken එකක් නොමැත.");
            }

            // Username සහ password ලබාගන්න
            String username = usernameToken.getElementsByTagNameNS("*", "Username").item(0).getTextContent();
            String password = usernameToken.getElementsByTagNameNS("*", "Password").item(0).getTextContent();

            // --- ඔබගේ Authentication Logic එක මෙතනට යොදන්න ---
            // මෙම උදාහරණය සඳහා, අපි නියත අගයන් භාවිතා කරමු.
            if ("admin".equals(username) && "password123".equals(password)) {
                System.out.println("User: " + username + " සඳහා Authentication සාර්ථකයි.");
            } else {
                throw new SecurityException("Authentication අසාර්ථකයි: වැරදි username හෝ password.");
            }

        } catch (Exception e) {
            // Authentication අසාර්ථක වුවහොත්, Fault එකක් throw කර ක්‍රියාවලිය නවත්වන්න
            throw new Fault(new SecurityException("Authentication දෝෂය: " + e.getMessage()));
        }
    }
}