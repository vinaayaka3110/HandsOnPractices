// package SingletonPatternExample;

class Logger {

    // Private static instance
    private static Logger instance;

    // Private constructor
    private Logger() {
        System.out.println("Logger Instance Created!");
    }

    // Public method to get the single instance
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    // Logging method
    public void log(String message) {
        System.out.println("LOG: " + message);
    }
}

public class SingletonTest {

    public static void main(String[] args) {

        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.log("Application Started..");
        logger2.log("Running Application...");

        if (logger1 == logger2) {
            System.out.println("Only one Logger instance exists.");
        } else {
            System.out.println("More than one Logger instance exists.");
        }
    }
}