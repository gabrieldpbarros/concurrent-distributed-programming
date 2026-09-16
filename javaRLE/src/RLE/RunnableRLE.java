package RLE;

import interfaces.IRLE;

public class RunnableRLE implements Runnable, IRLE {
    private final String completeString;
    private final int numThreads;
    private final int threadIdx;
    private int stringSize;
    private int i;
    private int offset;
    private int ceiling;
    private String partialResult;

    public RunnableRLE(int numThreads, int threadIdx, String completeString) {
        this.numThreads = numThreads;
        this.threadIdx = threadIdx;
        this.completeString = completeString;
        // verifica se e a ultima particao
        if (threadIdx == numThreads - 1) this.offset = completeString.length() % numThreads;
        else this.offset = 0;
    }

    private int countChar(String input) {
        int count = 1;
        this.i++;
        while (i < this.ceiling && input.charAt(i-1) == input.charAt(i)) {
            count++;
            this.i++;
        }
        return count;
    }

    @Override
    public void run() {
        this.partialResult = calcRLE(this.completeString);
    }

    @Override
    public String calcRLE(String input) {
        StringBuilder output = new StringBuilder();

        this.stringSize = input.length() / this.numThreads;
        this.i = this.threadIdx * this.stringSize;
        this.ceiling = this.stringSize + this.i + this.offset;

        while (i < this.ceiling) {
            output.append(input.charAt(this.i));
            int count = countChar(input);
            output.append(count);
        }

        return output.toString();
    }

    public String getResult() {
        return this.partialResult;
    }
}
