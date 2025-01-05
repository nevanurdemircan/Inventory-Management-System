package manager;

import java.util.HashMap;
import java.util.Map;

public class SingletonManager {

    private static final Map<Class<?>, Object> singletonMap = new HashMap<>();

    private SingletonManager() {
    }

    public static <T> T getBean(Class<T> clazz) {
        if (!singletonMap.containsKey(clazz)) {
            synchronized (SingletonManager.class) {
                if (!singletonMap.containsKey(clazz)) {
                    try {
                        T instance = clazz.getDeclaredConstructor().newInstance();
                        singletonMap.put(clazz, instance);
                    } catch (Exception e) {
                        throw new RuntimeException("Cannot create Singleton instance for: " + clazz.getName(), e);
                    }
                }
            }
        }
        return clazz.cast(singletonMap.get(clazz));
    }
}
