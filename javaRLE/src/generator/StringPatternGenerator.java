package generator;

import java.util.Random;

public class StringPatternGenerator {
    public static String generate(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();
        char currChar = 'a';
        for (int i = 0; i < length; i++) {
            if (i % 10 == 0) {
                int randIndex = r.nextInt(26);
                currChar = (char) (randIndex + 97);
            }
            stringBuilder.append(currChar);
        }

        return stringBuilder.toString();
    }
}
