package timer;

public class ExecTimer {
    private long startTime;
    private long endTime;

    public ExecTimer() {}

    public void startTimer() {
        this.startTime = System.nanoTime();
    }

    public void endTimer() {
        this.endTime = System.nanoTime();
    }

    public double calcExecTime() {
        return (endTime - startTime) * 10e-9;
    }
}
