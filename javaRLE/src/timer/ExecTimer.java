package timer;

public class ExecTimer {
    private long startTime = 0;
    private long endTime = 0;

    public ExecTimer() {}

    public void startTimer() {
        this.startTime = System.nanoTime();
    }

    public void endTimer() {
        this.endTime = System.nanoTime();
    }

    public double calcExecTime() {
        return (endTime - startTime) * 1.0e-9;
    }
}
