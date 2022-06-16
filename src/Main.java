public class Main {
    public static void main(String[] args) {
        FileManager fileToCompile = new FileManager(args[0]);
        System.out.println(fileToCompile.readString());
    }
}
