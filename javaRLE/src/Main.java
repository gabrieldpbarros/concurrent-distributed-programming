import RLE.RunnableRLE;
import RLE.SequentialRLE;
import generator.StringPatternGenerator;
import interfaces.IRLE;
import timer.ExecTimer;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        int MaxThreads = 8;

        String smallTestString = StringPatternGenerator.generate(15000);
        String mediumTestString = StringPatternGenerator.generate(15000000);
        String largeTestString = StringPatternGenerator.generate(45000000);

        ExecTimer smallStringTimer = new ExecTimer();
        ExecTimer mediumStringTimer = new ExecTimer();
        ExecTimer largeStringTimer = new ExecTimer();

        // System.out.println("String de 15000 caracteres: " + smallTestString);
        // System.out.println("String de 15000000 caracteres: " + mediumTestString);
        // System.out.println("String de 45000000 caracteres: " + largeTestString);

        //IRLE sequentialCalc = new SequentialRLE();
        //smallStringTimer.startTimer();
        //String smallStringResult = sequentialCalc.calcRLE(smallTestString);
        //smallStringTimer.endTimer();
        //System.out.println(smallStringResult);

        //sequentialCalc = new SequentialRLE(); // reinstanciamos a fim de manter a validação justa
        //mediumStringTimer.startTimer();
        //String mediumStringResult = sequentialCalc.calcRLE(mediumTestString);
        //mediumStringTimer.endTimer();
        //System.out.println(mediumStringResult);

        //sequentialCalc = new SequentialRLE();
        //largeStringTimer.startTimer();
        //String largeStringResult = sequentialCalc.calcRLE(largeTestString);
        //largeStringTimer.endTimer();
        //System.out.println(largeStringResult);

        //Thread[] smallTh = new Thread[MaxThreads];
        //Thread[] mediumTh = new Thread[MaxThreads];
        Thread[] largeTh = new Thread[MaxThreads];
        //IRLE[] smallRle = new IRLE[MaxThreads];
        //IRLE[] mediumRle = new IRLE[MaxThreads];
        IRLE[] largeRle = new IRLE[MaxThreads];

        //smallStringTimer.startTimer();
        //mediumStringTimer.startTimer();
        largeStringTimer.startTimer();
        for (int i = 0; i < MaxThreads; i++) {
            //smallRle[i] = new RunnableRLE(
            //        MaxThreads,
            //        i,
            //        smallTestString
            //);
            //smallTh[i] = new Thread((Runnable) smallRle[i]);
            //smallTh[i].start();

            //mediumRle[i] = new RunnableRLE(
            //        MaxThreads,
            //        i,
            //        mediumTestString
            //);
            //mediumTh[i] = new Thread((Runnable) mediumRle[i]);
            //mediumTh[i].start();

            largeRle[i] = new RunnableRLE(
                    MaxThreads,
                    i,
                    largeTestString
            );
            largeTh[i] = new Thread((Runnable) largeRle[i]);
            largeTh[i].start();
        }

        String smallResult = "";
        String mediumResult = "";
        String largeResult = "";
        try {
            for (int i = 0; i < MaxThreads; i++) {
                //smallTh[i].join();
                //mediumTh[i].join();
                largeTh[i].join();

                //smallResult = joinRLE(smallResult, smallRle[i].getResult());
                //mediumResult = joinRLE(mediumResult, mediumRle[i].getResult());
                largeResult = joinRLE(largeResult, largeRle[i].getResult());
            }
            //smallStringTimer.endTimer();
            //mediumStringTimer.endTimer();
            largeStringTimer.endTimer();
        } catch (InterruptedException e) {
            System.out.println("Excecao");
        }
        //System.out.printf(Locale.US, """
        //        =============TEMPOS DE EXECUCAO=============
        //        String de 15000 caracteres: %1$,.6f s
        //        String de 15000000 caracteres: %2$,.6f s
        //        String de 45000000 caracteres: %3$,.6f s
        //        """,
        //        smallStringTimer.calcExecTime(),
        //        mediumStringTimer.calcExecTime(),
        //        largeStringTimer.calcExecTime()
        //);
        System.out.println("=============TEMPOS DE EXECUCAO=============");
        System.out.printf(Locale.US, "String de 15000 caracteres: %1$,.6f s\n", smallStringTimer.calcExecTime());
        System.out.printf(Locale.US, "String de 15000000 caracteres: %1$,.6f s\n", mediumStringTimer.calcExecTime());
        System.out.printf(Locale.US, "String de 45000000 caracteres: %1$,.6f s\n", largeStringTimer.calcExecTime());
    }

    private static String joinRLE(String left, String right) {
        StringBuilder leftRLESize = new StringBuilder();
        StringBuilder rightRLESize = new StringBuilder();
        String result = left + right;
        int leftBound = left.length() - 1;
        int rightBound = 0;
        if (!left.isEmpty()) {
            boolean leftFound = false;
            while (!leftFound) { // como o direito sempre esta no indice 0, nao precisamos buscar
                char leftChar = left.charAt(leftBound);

                if ((leftChar >= 'A' && leftChar <= 'Z') || (leftChar >= 'a' && leftChar <= 'z')) leftFound = true;
                else leftRLESize.insert(0, leftChar);

                if (!leftFound) leftBound--;
            }

            boolean newChar = false;
            while (!newChar) {
                char rightChar = right.charAt(++rightBound);
                if (rightChar < '0' || rightChar > '9') newChar = true;
                else rightRLESize.append(rightChar);
            }
            if (left.charAt(leftBound) == right.charAt(0)) {
                int leftIntegerValue = Integer.parseInt(leftRLESize.toString());
                int rightIntegerValue = Integer.parseInt(rightRLESize.toString());
                int newRLESize = leftIntegerValue + rightIntegerValue;

                result = left.substring(0, leftBound + 1) + newRLESize + right.substring(rightBound);
            }
        }

        return result;
    }
}