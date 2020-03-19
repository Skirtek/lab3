import pl.lab.services.StreamService;
import pl.lab.services.ThreadPoolService;
import pl.lab.services.ThreadService;

public class Main {

    private static ThreadService threadService = new ThreadService();
    private static ThreadPoolService threadPoolService = new ThreadPoolService();
    private static StreamService streamService = new StreamService();

    public static void main(String[] args) {
        //threadService.Run();
        //threadPoolService.RunFixedThreadPool();
        //threadPoolService.RunSingleThreadPool();
        //threadPoolService.RunCachedThreadPool();
        //streamService.Run();
    }
}