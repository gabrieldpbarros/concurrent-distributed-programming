import RLE.SequentialRLE;
import generator.StringPatternGenerator;
import interfaces.IRLE;
import timer.ExecTimer;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        String smallTestString = StringPatternGenerator.generate(15000);
        String mediumTestString = StringPatternGenerator.generate(15000000);
        String largeTestString = StringPatternGenerator.generate(45000000);

        ExecTimer smallStringTimer = new ExecTimer();
        ExecTimer mediumStringTimer = new ExecTimer();
        ExecTimer largeStringTimer = new ExecTimer();

        // System.out.println("String de 15000 caracteres: " + smallTestString);
        // System.out.println("String de 15000000 caracteres: " + mediumTestString);
        // System.out.println("String de 45000000 caracteres: " + largeTestString);

        IRLE sequentialCalc = new SequentialRLE();
        smallStringTimer.startTimer();
        String smallStringResult = sequentialCalc.calcRLE(smallTestString);
        smallStringTimer.endTimer();
        //System.out.println(smallStringResult);

        sequentialCalc = new SequentialRLE(); // reinstanciamos a fim de manter a validação justa
        mediumStringTimer.startTimer();
        String mediumStringResult = sequentialCalc.calcRLE(mediumTestString);
        mediumStringTimer.endTimer();
        //System.out.println(mediumStringResult);

        sequentialCalc = new SequentialRLE();
        largeStringTimer.startTimer();
        String largeStringResult = sequentialCalc.calcRLE(largeTestString);
        largeStringTimer.endTimer();
        //System.out.println(largeStringResult);

        System.out.printf(Locale.US, """
                =============TEMPOS DE EXECUCAO=============
                String de 15000 caracteres: %1$,.6f s
                String de 15000000 caracteres: %2$,.6f s
                String de 45000000 caracteres: %3$,.6f s
                """,
                smallStringTimer.calcExecTime(),
                mediumStringTimer.calcExecTime(),
                largeStringTimer.calcExecTime()
        );
    }
}