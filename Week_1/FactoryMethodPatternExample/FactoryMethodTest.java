// package FactoryMethodPatternExample;

// Document Interface
interface Document {
    void open();
}

// Concrete Document Classes
class WordDocument implements Document {
    public void open() {
        System.out.println("Opening Word Document.");
    }
}

class PdfDocument implements Document {
    public void open() {
        System.out.println("Opening PDF Document.");
    }
}

class ExcelDocument implements Document {
    public void open() {
        System.out.println("Opening Excel Document.");
    }
}

// Abstract Factory Class
abstract class DocumentFactory {
    public abstract Document createDocument();

    public void openDocument() {
        Document document = createDocument();
        document.open();
    }
}

// Concrete Factory Classes
class WordDocumentFactory extends DocumentFactory {
    public Document createDocument() {
        return new WordDocument();
    }
}

class PdfDocumentFactory extends DocumentFactory {
    public Document createDocument() {
        return new PdfDocument();
    }
}

class ExcelDocumentFactory extends DocumentFactory {
    public Document createDocument() {
        return new ExcelDocument();
    }
}

// Test Class
public class FactoryMethodTest {
    public static void main(String[] args) {

        DocumentFactory wordFactory = new WordDocumentFactory();
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        DocumentFactory excelFactory = new ExcelDocumentFactory();

        wordFactory.openDocument();
        pdfFactory.openDocument();
        excelFactory.openDocument();
    }
}