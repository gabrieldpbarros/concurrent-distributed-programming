package RLE;
import interfaces.IRLE;

public class SequentialRLE implements IRLE {
    private int i;
    private int stringSize;
    private String result;

    public SequentialRLE() {}

    private int countChar(String input) {
        int count = 1;
        this.i++;
        while (i < this.stringSize && input.charAt(i-1) == input.charAt(i)) {
            count++;
            this.i++;
        }
        return count;
    }

    @Override
    public String calcRLE(String input) {
        StringBuilder output = new StringBuilder();

        this.i = 0;
        this.stringSize = input.length();
        while (i < this.stringSize) {
            output.append(input.charAt(this.i));
            int count = countChar(input);
            output.append(count);
        }

        return output.toString();
    }

    @Override
    public String getResult() {
        return this.result;
    }
}
