import threads.Printthreadv2;
import threads.RunThE;

public class Main {
    public static void main(String[] args) {
        //runPrintExample();
        calcThE();
    }

    private static void runPrintExample() {
        long MAX_THREADS =  5;
        long MAX_COUNT   = 10;
        long i;
        String s;
        for(i=0; i<MAX_THREADS; i++) {
            s = "Thread " + ((char) (65+i));
            new Thread(new Printthreadv2(MAX_COUNT), s).start(); }
    }

    private static void calcThE() {
        int MaxThreads = 8, i;
        long N = 1000000000;
        Thread[] th;
        RunThE[] rh;

        double resfinal;
        rh = new RunThE[MaxThreads];
        th = new Thread[MaxThreads];
        for(i=0; i<MaxThreads; i++) {
            rh[i] = new RunThE(N,
                    MaxThreads);
            th[i] = new Thread(rh[i]);
            th[i].start();	}

        resfinal = 1;
        try {
            for(i=0; i<MaxThreads; i++) {
                th[i].join();
                resfinal *= rh[i].valor; }
        } catch (InterruptedException e) { System.out.println("Excecao"); }
        System.out.println("Resultado=" + resfinal);

    }
}