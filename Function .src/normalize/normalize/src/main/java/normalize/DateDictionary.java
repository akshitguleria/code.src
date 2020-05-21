package normalize;

import java.util.Map;
import java.util.TreeMap;

public class DateDictionary {
    private Object[] dateStrings;
    private final Map<String, Integer> dateMap;
    private int nextStringIndex;

    public DateDictionary(Map<String, Integer> stringIntegerMap) {
        this.dateMap = new TreeMap<>(stringIntegerMap);
        this.dateStrings = this.dateMap.keySet().toArray();
        this.nextStringIndex = 0;
    }

    public DateDictionary(DateDictionary dateDictionary) {
        this(dateDictionary.getMap());
    }

    private Map<String, Integer> getMap() {
        return dateMap;
    }

    public boolean hasKey(String dateString) {
        return dateMap.containsKey(dateString);
    }

    public String getNextDateString() {
        if(nextStringIndex >= dateStrings.length)
            return null;
        return (String) dateStrings[nextStringIndex++];
    }

    public int getValue(String key) {
        return dateMap.get(key);
    }

    public void putAll(Map<String, Integer> tempMap) {
        dateMap.putAll(tempMap);
        this.dateStrings = this.dateMap.keySet().toArray();
    }

    @Override
    public String toString() {
        return "DateDictionary{" +
                "dateMap=" + dateMap +
                '}';
    }
}
