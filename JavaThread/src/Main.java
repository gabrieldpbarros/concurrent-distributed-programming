import threads.Printthreadv2;

public class Main {
    public static void main(String[] args) {
        long MAX_THREADS =  5;
        long MAX_COUNT   = 10;
        long i;
        String s;
        for(i=0; i<MAX_THREADS; i++) {
            s = "Thread " + ((char) (65+i));
            new Thread(new Printthreadv2(MAX_COUNT), s).start(); }
    }
}