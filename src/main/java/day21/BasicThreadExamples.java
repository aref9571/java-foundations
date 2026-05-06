package day21;

public class BasicThreadExamples {
    public static Thread startPrintTasks(String name , int times){
        Runnable task = () -> {
            for (int i = 0 ; i < times ; i++){
                System.out.println("[" + name + "] iteration" + i);
            }
        };
        Thread thread = new Thread(task,name);
        thread.start();
        return thread;
    }
}
