import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class FileIO {
    private String dataDirectory;
    private ReentrantLock lock = new ReentrantLock();
    public FileIO(){
    }
    public ArrayList<String> fileContentsToList(String filePath){
        ArrayList<String> contents = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String l;
            while((l = br.readLine())!=null){
                if(! contents.contains(l)){
                    contents.add(l);
                }
            }
        } catch (Exception e) {
            System.out.println("File not found:" + filePath);
        }
        return contents;
    }
    public String getDataDirectory(){
        return dataDirectory;
    }
    public void writeToFile(String pathName, String contents) throws IOException {
        lock.lock();
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(pathName, true)));
        pw.println(contents);
        pw.close();
        lock.unlock();
    }
    public void clearFile(String name){
        try {
            File file = new File(getDataDirectory() + name);
            file.delete();
            try{
                file.createNewFile();
            } catch(Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}