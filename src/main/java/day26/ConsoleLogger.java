package day26;

public class ConsoleLogger implements SimpleLogger {

    private final String loggerName;
    public ConsoleLogger(Class<?> sourceClass){
        this.loggerName = sourceClass.getSimpleName();
    }

    @Override
    public void info(String message, Object... args) {
        System.out.println("[INFO] [" + loggerName + "] " + format(message, args));
    }

    @Override
    public void error(String message, Throwable throwable) {
        System.err.println("[ERROR] [" + loggerName + "] " + message);
        throwable.printStackTrace(System.err);
    }

    private String format(String template , Object... args){
        String printTemplate = template.replace("{}" , "%s");
        return String.format(printTemplate, args);
    }


}
