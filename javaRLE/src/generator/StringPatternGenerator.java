package generator;

import java.util.Random;

public class StringPatternGenerator {
    public static String generate(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random r = new Random();
        char currChar = 'a';
        int randSize = r.nextInt(1,20);
        for (int i = 0; i < length; i++) {
            if (i % randSize == 0) {
                randSize = r.nextInt(1,20);
                int randIndex = r.nextInt(26);
                currChar = (char) (randIndex + 97);
            }
            stringBuilder.append(currChar);
        }

        return stringBuilder.toString();
    }
}
