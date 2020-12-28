package homework20.singleton;

import java.util.concurrent.atomic.AtomicInteger;

public class SingletonSynch {
    private static SingletonSynch instance;
    private static AtomicInteger atomicInteger;

    public SingletonSynch() {
        atomicInteger = new AtomicInteger(0);
    }

    public static SingletonSynch getInstance() {
        SingletonSynch localInstance = instance;
        if (localInstance == null) {
            synchronized (SingletonSynch.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SingletonSynch();
                }
            }
        }
        System.out.println("From getInstance() Atomic counter " + atomicInteger.incrementAndGet());
        return localInstance;
    }
}
