package normalize;

public class Interval {
    private final String start;
    private final String end;

    public Interval(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
