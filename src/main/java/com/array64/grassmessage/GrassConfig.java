package com.array64.grassmessage;

import java.io.InputStream;

public class GrassConfig {
    private final String xsdLocation;
    private final String xmlFilename;
    private final InputStream xmlInputStream;

    private GrassConfig(String xsdLocation, String xmlFilename, InputStream xmlInputStream) {
        this.xsdLocation = xsdLocation;
        this.xmlFilename = xmlFilename;
        this.xmlInputStream = xmlInputStream;
    }

    String getXSDLocation() {
        return xsdLocation;
    }

    InputStream getInputStream() {
        return xmlInputStream;
    }

    public String getXMLFilename() {
        return xmlFilename;
    }

    public static class Builder {
        private String xsdLocation = "schema.xsd";
        private String xmlFilename = "messages.xml";
        private InputStream xmlInputStream = null;

        public Builder xmlFilename(String filename) {
            this.xmlFilename = filename;
            return this;
        }

        public Builder xmlInputStream(InputStream xmlInputStream) {
            this.xmlFilename = "";
            this.xmlInputStream = xmlInputStream;
            return this;
        }

        public Builder schemaLocation(String schemaLocation) {
            this.xsdLocation = schemaLocation;
            return this;
        }

        public GrassConfig build() {
            return new GrassConfig(xsdLocation, xmlFilename, xmlInputStream);
        }
    }
}
