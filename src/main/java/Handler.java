import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

public class Handler {
    private ThreadPoolExecutor executor;
    private ArrayList<String> accountAliases;
    private static final ReentrantLock lock = new ReentrantLock();
    private int threads;
    private int accountIndex = 0;
    private static final FileIO fileIO = new FileIO();
    public Handler(){
        accountAliases = fileIO.fileContentsToList("accounts.txt");
    }
    public void instantiateAndBeginTasks(int threads){
        this.threads = threads;
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
        int numSublists = accountAliases.size() / threads;
        for(int i = 0; i < threads; i++){
            Task task = new Task(this);
            task.start();
        }
    }
    public String getAccount(){
        lock.lock();
        String content =accountAliases.get(accountIndex);
        accountIndex++;
        lock.unlock();
        return content;
    }
}