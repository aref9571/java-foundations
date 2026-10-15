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
        StringBuilder result = new StringBuilder();
        int argumentIndex = 0;
        int position = 0;
        int placeholder = template.indexOf("{}");
        while (placeholder != -1 && argumentIndex < args.length) {
            result.append(template, position, placeholder);
            result.append(args[argumentIndex]);
            argumentIndex++;
            position = placeholder + 2;
            placeholder = template.indexOf("{}", position);
        }
        result.append(template.substring(position));
        return result.toString();
    }


}
