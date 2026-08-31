package threads;

public class Printthreadv2 implements Runnable {
    private long count;

    public Printthreadv2(long acount) {  // construtor
        count = acount; }

    public void run() {
        long i;
        for(i=1; i<=count; i++)
            System.out.println("Line from " +
                    Thread.currentThread().getName() + " value=" + i); }
}
