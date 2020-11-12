import javax.xml.stream.*;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

public class Lab1 {
    private static final String unobfuscatedDataFileName = "src/main/resources/employees.xml";
    private static final String obfuscatedDataFileName = "src/main/resources/employeesObfuscated.xml";
    private static final String source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String target = "7JpLFPOcuK2qV13TjzrdB5RxlMW9GnCQ6v8IU4yeNbDgfaEAZ0siYokXhSHwmt";

    public static void main(String[] args) throws IOException, XMLStreamException {
        if (args.length != 1) {
            System.err.println("Use obfuscate or unobfuscate as the only parameter to the program");
            System.exit(1);
        }
        process(args[0]);
    }

    public static void process(String mode) throws XMLStreamException, IOException {
        String inputFileName;
        String outputFileName;
        Function<String, String> dataProcessor;
        if (mode.equals("obfuscate")) {
            inputFileName = unobfuscatedDataFileName;
            outputFileName = obfuscatedDataFileName;
            dataProcessor = new Obfuscator();
        } else {
            inputFileName = obfuscatedDataFileName;
            outputFileName = unobfuscatedDataFileName;
            dataProcessor = new Unobfuscator();
        }
        try (FileInputStream fis = new FileInputStream(inputFileName);
             FileOutputStream fos = new FileOutputStream(outputFileName)) {

            XMLStreamReader xmlReader = XMLInputFactory.newInstance().createXMLStreamReader(fis);
            XMLOutputFactory xmlOutFact = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = new IndentingXMLStreamWriter(xmlOutFact.createXMLStreamWriter(fos));
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

    private static class Obfuscator implements Function<String, String> {

        @Override
        public String apply(String s) {
            char[] result = new char[s.length()];
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int index = source.indexOf(c);
                result[i] = target.charAt(index);
            }
            return new String(result);
        }
    }

    private static class Unobfuscator implements Function<String, String> {

        @Override
        public String apply(String s) {
            char[] result = new char[s.length()];
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                int index = target.indexOf(c);
                result[i] = source.charAt(index);
            }
            return new String(result);
        }
    }

    public static void shuffleString() {
        List<String> letters = Arrays.asList(source.split(""));
        Collections.shuffle(letters);
        StringBuilder builder = new StringBuilder();
        for (String letter : letters) {
            builder.append(letter);
        }
        System.out.println(builder.toString());
    }

}
