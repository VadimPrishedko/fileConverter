import service.FilesDir;

public class Run {
    public static void main(String[] args) {
//        /Users/user/Desktop/fileConverter/Converter
        FilesDir fd = new FilesDir();
        try {
            String dir = args[0];
            fd.listFiles(dir);
        } catch (ArrayIndexOutOfBoundsException e) {
            String dir = System.getProperty("user.dir");
            fd.listFiles(dir);
        }


    }
}