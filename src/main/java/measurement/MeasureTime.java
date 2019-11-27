package measurement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MeasureTime<T> {
    //private T obj;

    public long measureMethodTime(T obj, String methodName) {
        long res = -1;
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            long start = System.currentTimeMillis();
            method.invoke(obj);
            res = System.currentTimeMillis() - start;

        } catch (NoSuchMethodException | IllegalAccessException  | InvocationTargetException e)
        {
            System.out.println("No such method");
            e.printStackTrace();
        }
        return res;
    }
}
