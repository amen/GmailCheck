import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {
    public static void main(String[] args){
        WebDriverManager.phantomjs().setup();
        Handler handler = new Handler();
        Console console = new Console();
        int threads = console.getUserInt("Threads: ");
        handler.instantiateAndBeginTasks(threads);
    }

}
