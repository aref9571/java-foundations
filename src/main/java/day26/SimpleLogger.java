package day26;

public interface SimpleLogger {
    void info(String message , Object... args);
    void error(String message , Throwable throwable);

}
