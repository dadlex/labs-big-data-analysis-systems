import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class DataObfuscator {

    private static final String source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String target = "7JpLFPOcuK2qV13TjzrdB5RxlMW9GnCQ6v8IU4yeNbDgfaEAZ0siYokXhSHwmt";

    public String obfuscate(String s) {
        char[] result = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = source.indexOf(c);
            result[i] = target.charAt(index);
        }
        return new String(result);
    }

    public String deobfuscate(String s) {
        char[] result = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int index = target.indexOf(c);
            result[i] = source.charAt(index);
        }
        return new String(result);
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