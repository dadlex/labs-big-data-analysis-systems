import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import junitx.framework.FileAssert;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

public class DataProcessTests {
    DataObfuscator obfuscator;

    @Before
    public void createObfuscator() {
        obfuscator = new DataObfuscator();
    }

    @Test
    public void testStringObfuscation() {
        Assert.assertEquals("7JpLFP", obfuscator.obfuscate("abcdef"));
    }

    @Test
    public void testStringDeobfuscation() {
        Assert.assertEquals("abcdef", obfuscator.deobfuscate("7JpLFP"));
    }

    @Test
    public void testXmlFileObfuscation() throws IOException, XMLStreamException {
        Lab1.process("src/test/resources/employees.xml", "src/test/resources/resultObfuscated.xml", obfuscator::obfuscate);
        FileAssert.assertEquals(new File("src/test/resources/employeesObfuscated.xml"),
                new File("src/test/resources/resultObfuscated.xml"));
    }

    @Test
    public void testXmlFileDeobfuscation() throws IOException, XMLStreamException {
        Lab1.process("src/test/resources/employeesObfuscated.xml", "src/test/resources/resultDeobfuscated.xml", obfuscator::deobfuscate);
        FileAssert.assertEquals(new File("src/test/resources/employees.xml"),
                new File("src/test/resources/resultDeobfuscated.xml"));
    }



}

