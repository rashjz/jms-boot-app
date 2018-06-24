package jms.boot.app.tools;


@FunctionalInterface
public interface OrderFunctions<T1, T2, R> {
    R overrideMethod(T1 t1, T2 t2);
}
