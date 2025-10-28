package org.weasel;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.util.XMLHelper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStrings;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class ExcelLoader{

    //Shamelessly stolen from the docs - need to looking into more but right now just want it to work for loading data
    public LinkedList<String> processAllSheets(String filename) throws Exception {
        System.out.println("Running loader");
        // LinkedList<String> rowList = new LinkedList<String>();
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStrings sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);

        Iterator<InputStream> sheets = r.getSheetsData();
        while(sheets.hasNext()) {
            System.out.println("Processing new sheet:\n");
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
            System.out.println("");
        }
        SheetHandler handler = (SheetHandler) parser.getContentHandler();
        return handler.getRows();
    }

    public XMLReader fetchSheetParser(SharedStrings sst) throws SAXException, ParserConfigurationException {
        XMLReader parser = XMLHelper.newXMLReader();
        ContentHandler handler = new SheetHandler(sst);
        parser.setContentHandler(handler);
        return parser;
    }


    /**
     * See org.xml.sax.helpers.DefaultHandler javadocs
     */
    private class SheetHandler extends DefaultHandler {
        private SharedStrings sst;
        private String lastContents;
        private boolean nextIsString;
        private int nextRowIndex = 0;
        private LinkedList<String> rows = new LinkedList<String>();

        private SheetHandler(SharedStrings sst) {
            this.sst = sst;
        }

        public LinkedList<String> getRows(){
            return rows;
        }

        public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

            // c => cell
            if(name.equals("c")) {
                // Print the cell reference
                System.out.print(attributes.getValue("r") + " - ");

                nextRowIndex = Integer.parseInt(attributes.getValue("r").split("[A-Z]+")[1])-1;

                // Figure out if the value is an index in the SST
                String cellType = attributes.getValue("t");
                if(cellType != null && cellType.equals("s")) {
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }
            }

            // Clear contents cache
            lastContents = "";
        }

        public void endElement(String uri, String localName, String name) throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if(nextIsString) {
                int idx = Integer.parseInt(lastContents);
                lastContents = sst.getItemAt(idx).getString();
                nextIsString = false;
            }

            // v => contents of a cell
            // Output after we've seen the string contents
            if(name.equals("v")) {
                //Make sure we have the row added into the linked list
                if(rows.size()-1 < nextRowIndex)
                    while(rows.size()-1 < nextRowIndex) rows.add(lastContents);
                else
                    rows.set(nextRowIndex, rows.get(nextRowIndex).concat("\t|\t"+lastContents));
                    
                System.out.println(lastContents);
            }
        }

        public void characters(char[] ch, int start, int length) {
            lastContents += new String(ch, start, length);
        }
    }
}