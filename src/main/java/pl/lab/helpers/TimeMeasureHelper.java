package pl.lab.helpers;

import java.time.Duration;
import java.time.Instant;

public class TimeMeasureHelper {

    public static void printCurrentExecutionTime(Instant start){
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }
}
