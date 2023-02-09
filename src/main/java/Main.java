import java.io.File;

public class Main {
    static int port = 8989;

    public static void main(String[] args) throws Exception {
        BooleanSearchEngine booleanSearchEngine = new BooleanSearchEngine(new File("pdfs"));
        Server server = new Server(port, booleanSearchEngine);
        server.start();
    }
}