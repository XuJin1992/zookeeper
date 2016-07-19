<?xml version="1.0" encoding="utf-8"?>
<wsdl:definitions xmlns:s="http://www.w3.org/2001/XMLSchema" xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/" xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/" xmlns:tns="http://ctrip.com/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/" xmlns:http="http://schemas.xmlsoap.org/wsdl/http/" xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/" targetNamespace="http://ctrip.com/" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/">
  <wsdl:types>
    <s:schema elementFormDefault="qualified" targetNamespace="http://ctrip.com/">
      <s:element name="Request">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="requestXML" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
      <s:element name="RequestResponse">
        <s:complexType>
          <s:sequence>
            <s:element minOccurs="0" maxOccurs="1" name="RequestResult" type="s:string" />
          </s:sequence>
        </s:complexType>
      </s:element>
    </s:schema>
  </wsdl:types>
  <wsdl:message name="RequestSoapIn">
    <wsdl:part name="parameters" element="tns:Request" />
  </wsdl:message>
  <wsdl:message name="RequestSoapOut">
    <wsdl:part name="parameters" element="tns:RequestResponse" />
  </wsdl:message>
  <wsdl:portType name="OTA_UserUniqueIDSoap">
    <wsdl:operation name="Request">
      <wsdl:input message="tns:RequestSoapIn" />
      <wsdl:output message="tns:RequestSoapOut" />
    </wsdl:operation>
  </wsdl:portType>
  <wsdl:binding name="OTA_UserUniqueIDSoap" type="tns:OTA_UserUniqueIDSoap">
    <soap:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="Request">
      <soap:operation soapAction="http://ctrip.com/Request" style="document" />
      <wsdl:input>
        <soap:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:binding name="OTA_UserUniqueIDSoap12" type="tns:OTA_UserUniqueIDSoap">
    <soap12:binding transport="http://schemas.xmlsoap.org/soap/http" />
    <wsdl:operation name="Request">
      <soap12:operation soapAction="http://ctrip.com/Request" style="document" />
      <wsdl:input>
        <soap12:body use="literal" />
      </wsdl:input>
      <wsdl:output>
        <soap12:body use="literal" />
      </wsdl:output>
    </wsdl:operation>
  </wsdl:binding>
  <wsdl:service name="OTA_UserUniqueID">
    <wsdl:port name="OTA_UserUniqueIDSoap" binding="tns:OTA_UserUniqueIDSoap">
      <soap:address location="http://openapi.ctrip.com/vacations/OTA_UserUniqueID.asmx" />
    </wsdl:port>
    <wsdl:port name="OTA_UserUniqueIDSoap12" binding="tns:OTA_UserUniqueIDSoap12">
      <soap12:address location="http://openapi.ctrip.com/vacations/OTA_UserUniqueID.asmx" />
    </wsdl:port>
  </wsdl:service>
</wsdl:definitions>