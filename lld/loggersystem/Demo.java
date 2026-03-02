package lld.loggersystem;

import java.util.*;
// ------------------- ENUM ----------------------
enum LogLevel {
    DEBUG, INFO, WARN, ERROR
}
// ------------------- MODEL ----------------------
class LogMessage {
    private final long timestamp;
    private final LogLevel level;
    private final String message;
    public LogMessage(LogLevel level, String message) {
        this.timestamp = System.currentTimeMillis();
        this.level = level;
        this.message = message;
    }
    public String toString() {
        return "[" + new Date(timestamp) + "] [" + level + "] " + message;
    }
}
// ------------------- APPENDER INTERFACE ----------------------
interface Appender {
    void append(LogMessage msg);
}
// ------------------- CONSOLE APPENDER ----------------------
class ConsoleAppender implements Appender {
    public void append(LogMessage msg) {
        System.out.println(msg);
    }
}
// ------------------- FILE APPENDER (MOCK) ----------------------
class FileAppender implements Appender {
    private final String fileName;
    public FileAppender(String fileName) {
        this.fileName = fileName;
    }
    public void append(LogMessage msg) {
        System.out.println("Writing to file " + fileName + ": " + msg);
    }
}
// ------------------- DB APPENDER (MOCK) ----------------------
class DbAppender implements Appender {
    public void append(LogMessage msg) {
        System.out.println("Writing to DB: " + msg);
    }
}
// ------------------- LOGGER ----------------------
class Logger {
    private final String name;
    private LogLevel level = LogLevel.INFO;
    private final List<Appender> appenders = new ArrayList<>();
    public Logger(String name) {
        this.name = name;
    }
    public void setLevel(LogLevel level) {
        this.level = level;
    }
    public void addAppender(Appender a) {
        appenders.add(a);
    }
    public void log(LogLevel level, String msg) {
        if (level.ordinal() < this.level.ordinal()) return;
        LogMessage logMessage = new LogMessage(level, msg);
        for (Appender a : appenders) {
            a.append(logMessage);
        }
    }
    public void info(String msg) { log(LogLevel.INFO, msg); }
    public void debug(String msg) { log(LogLevel.DEBUG, msg); }
    public void warn(String msg) { log(LogLevel.WARN, msg); }
    public void error(String msg) { log(LogLevel.ERROR, msg); }
}
// ------------------- LOGGER MANAGER (SINGLETON) ----------------------
class LoggerManager {
    private static final LoggerManager INSTANCE = new LoggerManager();
    private final Map<String, Logger> loggers = new HashMap<>();
    private LoggerManager() {}
    public static LoggerManager getInstance() {
        return INSTANCE;
    }
    public Logger getLogger(String name) {
        return loggers.computeIfAbsent(name, Logger::new);
    }
}
// ------------------- DEMO ----------------------
public class Demo {
    public static void main(String[] args) {
        LoggerManager lm = LoggerManager.getInstance();
        Logger appLogger = lm.getLogger("AppLogger");
// Add appenders
        appLogger.addAppender(new ConsoleAppender());
        appLogger.addAppender(new FileAppender("app.log"));
        appLogger.addAppender(new DbAppender());
// Set log level
        appLogger.setLevel(LogLevel.DEBUG);
        appLogger.info("System started");
        appLogger.debug("Debug details...");
        appLogger.warn("Memory is high");
        appLogger.error("System crashed!");
    }
}
