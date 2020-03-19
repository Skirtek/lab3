package pl.lab.services;

import pl.lab.helpers.TimeMeasureHelper;
import pl.lab.models.Item;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class StreamService {

    private BlockingQueue<Item> queue = new LinkedBlockingQueue<>(20);

    public void Run() {
        ArrayList<Item> itemsList = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            itemsList.add(new Item());
        }

        Instant start = Instant.now();
        itemsList.parallelStream().forEach(x -> {
            try {
                x.produceMe();
                queue.put(x);
                Item item = queue.take();
                item.consumeMe();
                TimeMeasureHelper.printCurrentExecutionTime(start);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
