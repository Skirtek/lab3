package pl.lab.services;

import pl.lab.helpers.TimeMeasureHelper;
import pl.lab.models.Item;

import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolService {

    private ArrayList<Item> itemsList = new ArrayList<>();
    private BlockingQueue<Item> queue;

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(7);
    private ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public void RunFixedThreadPool() {
        checkInitialization();
        Instant start = Instant.now();
        itemsList.forEach(x -> {
            fixedThreadPool.execute(() -> {
                x.produceMe();
                try {
                    queue.put(x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            fixedThreadPool.execute(() -> {
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

    public void RunSingleThreadPool() {
        checkInitialization();
        Instant start = Instant.now();
        itemsList.forEach(x -> {
            singleThreadPool.execute(() -> {
                x.produceMe();
                try {
                    queue.put(x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            singleThreadPool.execute(() -> {
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

    public void RunCachedThreadPool() {
        checkInitialization();
        Instant start = Instant.now();

        itemsList.forEach(x -> {
            cachedThreadPool.execute(() -> {
                x.produceMe();
                try {
                    queue.put(x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            cachedThreadPool.execute(() -> {
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

    private void Init() {
        queue = new LinkedBlockingQueue<>(20);
        for (int i = 0; i < 100; ++i) {
            itemsList.add(new Item());
        }
    }

    private void checkInitialization() {
        if (itemsList.isEmpty()) {
            Init();
        }
    }
}
