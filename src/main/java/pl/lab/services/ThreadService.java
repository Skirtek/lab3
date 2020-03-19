package pl.lab.services;

import pl.lab.helpers.TimeMeasureHelper;
import pl.lab.models.Item;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadService {

    private ExecutorService producents = Executors.newFixedThreadPool(4);
    private ExecutorService consuments = Executors.newFixedThreadPool(3);

    private BlockingQueue<Item> queue = new LinkedBlockingQueue<>(20);

    public void Run() {
        ArrayList<Item> itemsList = new ArrayList<>();

        for (int i = 0; i < 100; ++i) {
            itemsList.add(new Item());
        }

        Instant start = Instant.now();

        itemsList.forEach(x -> {
            producents.execute(() -> {
                x.produceMe();
                try {
                    queue.put(x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            consuments.execute(() -> {
                try {
                    Item getItemResult = queue.take();
                    getItemResult.consumeMe();
                    TimeMeasureHelper.printCurrentExecutionTime(start);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
