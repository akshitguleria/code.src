package normalize;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class Normalize {

    public DateDictionary solution(DateDictionary dateDictionary) {
        List<Interval> missingDateIntervals = getMissingDateIntervals(dateDictionary);
        DateDictionary newDateDictionary = new DateDictionary(dateDictionary);
        Map<String, Integer> tempMap = new HashMap<>();

        missingDateIntervals.forEach(interval -> {
            String firstDateString = interval.getStart();
            String nextDateString = interval.getEnd();
            List<String> missingDates = getMissingDatesBetween(firstDateString, nextDateString);
            int startValue = dateDictionary.getValue(firstDateString);

            int normalizingFactor = calculateNormalizingFactor(
                    dateDictionary.getValue(firstDateString),
                    dateDictionary.getValue(nextDateString),
                    missingDates.size());

            updateToNormalizedValue(tempMap, missingDates, normalizingFactor, startValue);
        });

        newDateDictionary.putAll(tempMap);
        return newDateDictionary;
    }

    private void updateToNormalizedValue(Map<String, Integer> tempMap, List<String> missingDates, int normalizingFactor, int startValue) {
        int iValue = startValue;
        for(String missingDate : missingDates){
            iValue += normalizingFactor;
            tempMap.put(missingDate, iValue);
        }
    }

    private List<Interval> getMissingDateIntervals(DateDictionary dateDictionary) {
        List<Interval> intervals = new ArrayList<>();
        String iDateString = dateDictionary.getNextDateString();

        while (iDateString != null) {
            String nextDateString = dateDictionary.getNextDateString();
            if (nextDateString == null)
                break;
            LocalDate startDate = parseDateString(iDateString);
            LocalDate endDate = parseDateString(nextDateString);
            if (DAYS.between(startDate, endDate) != 1)
                intervals.add(new Interval(iDateString, nextDateString));
            iDateString = nextDateString;
        }
        return intervals;
    }

    private List<String> getMissingDatesBetween(String startingDateString, String nextDateString) {
        LocalDate startingDate = parseDateString(startingDateString);
        LocalDate nextDate = parseDateString(nextDateString);
        List<String> dateStringList = startingDate
                .datesUntil(nextDate)
                .map(LocalDate::toString)
                .collect(Collectors.toList());
        dateStringList.remove(0); // Starting date was included, hence had to remove that in order to get missing dates.
        return dateStringList;
    }

    private LocalDate parseDateString(String dateString) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, dateFormat);
    }

    private int calculateNormalizingFactor(int startValue, int endValue, int numberOfMissingTerms) {
        return (endValue - startValue) / (numberOfMissingTerms + 1);
    }
}
