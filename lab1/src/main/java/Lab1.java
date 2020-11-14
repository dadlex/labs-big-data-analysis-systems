import javax.xml.stream.*;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Function;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

public class Lab1 {
    private static final String unobfuscatedDataFileName = "src/main/resources/employees.xml";
    private static final String obfuscatedDataFileName = "src/main/resources/employeesObfuscated.xml";


    public static void main(String[] args) throws IOException, XMLStreamException {
        if (args.length != 1) {
            System.err.println("Use obfuscate or unobfuscate as the only parameter to the program");
            System.exit(1);
        }
        String mode = args[0];
        String inputFileName;
        String outputFileName;
        Function<String, String> dataProcessor;
        if (mode.equals("obfuscate")) {
            inputFileName = unobfuscatedDataFileName;
            outputFileName = obfuscatedDataFileName;
            dataProcessor = new DataObfuscator()::obfuscate;
        } else {
            inputFileName = obfuscatedDataFileName;
            outputFileName = unobfuscatedDataFileName;
            dataProcessor = new DataObfuscator()::deobfuscate;
        }
        process(inputFileName, outputFileName, dataProcessor);
    }

    public static void process(String inputFileName, String outputFileName, Function<String, String> dataProcessor)
            throws XMLStreamException, IOException {
        try (FileInputStream fis = new FileInputStream(inputFileName);
             FileOutputStream fos = new FileOutputStream(outputFileName)) {

            XMLStreamReader xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(fis);
            XMLOutputFactory xmlOutFact = XMLOutputFactory.newInstance();
            IndentingXMLStreamWriter writer = new IndentingXMLStreamWriter(xmlOutFact.createXMLStreamWriter(fos));
            writer.setIndentStep("    ");
            writer.writeStartDocument();
            while (xmlReader.hasNext()) {
                xmlReader.next();
                if (xmlReader.isStartElement()) {
                    writer.writeStartElement(xmlReader.getLocalName());
                    int attributesCount = xmlReader.getAttributeCount();
                    for (int i = 0; i < attributesCount; i++) {
                        writer.writeAttribute(xmlReader.getAttributeLocalName(i), dataProcessor.apply(xmlReader.getAttributeValue(i)));
                    }
                } else if (xmlReader.isEndElement()) {
                    writer.writeEndElement();
                } else if (xmlReader.hasText() && xmlReader.getText().trim().length() > 0) {
                    writer.writeCharacters(dataProcessor.apply(xmlReader.getText()));
                }
            }
            writer.writeEndDocument();
        }
    }
}
