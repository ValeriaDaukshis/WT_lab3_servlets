package Parsers;

import Beans.Book;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlDeserializer {
    private static ArrayList<Book> bookList;
    private static final Logger log = Logger.getLogger(XmlDeserializer.class);
    public ArrayList<Book> DomParser(String path) {
        bookList = new ArrayList<Book>();
        try {
            File file = new File(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.parse(file);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("book");
            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    int id = Integer.parseInt(eElement.getAttribute("id"));
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String author = eElement.getElementsByTagName("author").item(0).getTextContent();
                    int pages = Integer.parseInt(eElement.getElementsByTagName("pages").item(0).getTextContent());
                    int price = Integer.parseInt(eElement.getElementsByTagName("price").item(0).getTextContent());
                    bookList.add(new Book(id, name, author, pages, price));
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return bookList;
    }

    public ArrayList<Book> SaxParser(String path) {
        bookList = new ArrayList<Book>();
        try{
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            AdvancedXMLHandler handler = new AdvancedXMLHandler();
            parser.parse(new File(path), handler);
        } catch (SAXException ex){
            log.error(ex.getMessage());
        }
        catch (ParserConfigurationException ex){
            log.error(ex.getMessage());
        }
        catch (IOException ex){
            log.error(ex.getMessage());
        }

        return bookList;
    }

    public ArrayList<Book> StaxParser(String path){
        int id = 0;
        String name = null;
        String author = null;
        int pages = 0;
        double price = 0.0;

        boolean bName = false;
        boolean bAuthor = false;
        boolean bPrice = false;
        boolean bPages = false;

        try {
            bookList = new ArrayList<Book>();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader =
                    factory.createXMLEventReader(new FileReader(path));

            while(eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                switch(event.getEventType()) {

                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();

                        if (qName.equalsIgnoreCase("book")) {
                            Iterator<Attribute> attributes = startElement.getAttributes();
                            id = Integer.parseInt(attributes.next().getValue());
                        } else if (qName.equalsIgnoreCase("name")) {
                            bName = true;
                        } else if (qName.equalsIgnoreCase("author")) {
                            bAuthor = true;
                        } else if (qName.equalsIgnoreCase("pages")) {
                            bPages = true;
                        }
                        else if (qName.equalsIgnoreCase("price")) {
                            bPrice = true;
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if(bName) {
                            name = characters.getData();
                            bName = false;
                        }
                        if(bAuthor) {
                            author = characters.getData();
                            bAuthor = false;
                        }
                        if(bPages) {
                            pages = Integer.parseInt(characters.getData());
                            bPages = false;
                        }
                        if(bPrice) {
                            price = Double.parseDouble(characters.getData());
                            bPrice = false;
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        EndElement endElement = event.asEndElement();

                        if(endElement.getName().getLocalPart().equalsIgnoreCase("book")) {
                            bookList.add(new Book(id, name, author, pages, price));
                        }
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage());
        } catch (XMLStreamException ex) {
            log.error(ex.getMessage());
        }
        return bookList;
    }

    private static class AdvancedXMLHandler extends DefaultHandler {
        private int id;
        private String name;
        private String author;
        private int pages;
        private double price;

        boolean bName = false;
        boolean bAuthor = false;
        boolean bPages = false;
        boolean bPrice = false;

        @Override
        public void startElement(String uri,
                                 String localName, String qName, Attributes attributes) throws SAXException {

            if (qName.equalsIgnoreCase("book")) {
                id = Integer.parseInt(attributes.getValue("id"));
            } else if (qName.equalsIgnoreCase("name")) {
                bName = true;
            } else if (qName.equalsIgnoreCase("author")) {
                bAuthor = true;
            } else if (qName.equalsIgnoreCase("pages")) {
                bPages = true;
            }
            else if (qName.equalsIgnoreCase("price")) {
                bPrice = true;
            }
        }

        @Override
        public void endElement(String uri,
                               String localName, String qName) throws SAXException {
            if (qName.equalsIgnoreCase("book")) {
                bookList.add(new Book(id, name, author, pages, price));
            }
        }

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {
            if (bName) {
                name = new String(ch, start, length);
                bName = false;
            } else if (bAuthor) {
                author = new String(ch, start, length);
                bAuthor = false;
            } else if (bPages) {
                pages = Integer.parseInt(new String(ch, start, length));
                bPages = false;
            } else if (bPrice) {
                price = Double.parseDouble(new String(ch, start, length));
                bPrice = false;
            }
        }
    }
}
