import RLE.SequentialRLE;
import generator.StringPatternGenerator;
import interfaces.IRLE;
import timer.ExecTimer;

public class Main {
    public static void main(String[] args) {
        String smallTestString = StringPatternGenerator.generate(15000);
        String mediumTestString = StringPatternGenerator.generate(15000000);
        String largeTestString = StringPatternGenerator.generate(45000000);

        ExecTimer smallStringTimer = new ExecTimer();
        ExecTimer mediumStringTimer = new ExecTimer();
        ExecTimer largeStringTimer = new ExecTimer();

        System.out.println("String de 15000 caracteres: " + smallTestString);
        // System.out.println("String de 15000000 caracteres: " + mediumTestString);
        // System.out.println("String de 45000000 caracteres: " + largeTestString);

        IRLE sequentialCalc = new SequentialRLE();
        String smallStringResult = sequentialCalc.calcRLE(smallTestString);
        System.out.println(smallStringResult);
    }
}