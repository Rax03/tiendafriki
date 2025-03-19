package org.example.utils;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.example.model.conection.ConnectionProperties;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ManagerPDF {

    public static <T> boolean writePDF(T content, String filename) {
        boolean write = false;
        try {
            PdfWriter writer = new PdfWriter(filename);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph(content.toString()));
            document.close();

            write = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return write;
    }

    public static String readPDF(String filename) {
        StringBuilder content = new StringBuilder();
        try {
            PdfReader reader = new PdfReader(filename);
            PdfDocument pdf = new PdfDocument(reader);
            Document document = new Document(pdf);

            for (int i = 1; i <= pdf.getNumberOfPages(); i++) {
                content.append(document.getPdfDocument().getPage(i).getContentBytes());
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public static ConnectionProperties readPDFToConnectionProperties(String filename) {
        ConnectionProperties properties = new ConnectionProperties();
        try {
            PdfReader reader = new PdfReader(filename);
            PdfDocument pdf = new PdfDocument(reader);
            Document document = new Document(pdf);

            StringBuilder content = new StringBuilder();
            for (int i = 1; i <= pdf.getNumberOfPages(); i++) {
                content.append(document.getPdfDocument().getPage(i).getContentBytes());
            }
            document.close();

            // Assuming the content is in a specific format, parse it to set properties
            String[] lines = content.toString().split("\n");
            for (String line : lines) {
                if (line.startsWith("URL:")) {
                    properties.setURL(line.substring(4).trim());
                } else if (line.startsWith("User:")) {
                    properties.setUser(line.substring(5).trim());
                } else if (line.startsWith("Password:")) {
                    properties.setPassword(line.substring(9).trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }
}