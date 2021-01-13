import java.awt.Color;
import java.util.Scanner;
public class Console {
    //    private static final String MAGENTA = "\033[1;35m";
//    private static final String WHITE = "\033[1;37m";
    private static final String MAGENTA = "";
    private static final String WHITE = "";
    public Console(){
    }
    public void setHeaders(){
        System.out.println("[Gmail Checker]");
    }
    public boolean getUserBool(String prompt, String truePrint, String falsePrint){
        System.out.println(prompt);
        while(true){
            Scanner in = new Scanner(System.in);
            if(in.hasNext()){
                String ans = in.nextLine();
                if(ans.toLowerCase().equals("y")){
                    if(truePrint !=null) {
                        System.out.println(truePrint);
                    }
                    return true;
                }
                if(ans.toLowerCase().equals("n")){
                    if(falsePrint !=null){
                        System.out.println(falsePrint);
                    }
                    return false;
                }
            }
            System.out.println("bruh, enter 'Y' or 'N'");
        }
    }
    public int getUserInt(String prompt){
        int numThreads;
        System.out.print(prompt);
        while(true){
            Scanner in = new Scanner(System.in);
            if(in.hasNextInt()){
                numThreads = in.nextInt();
                return numThreads;
            }
            System.out.println("bruh, enter a number...");
        }
    }

}
