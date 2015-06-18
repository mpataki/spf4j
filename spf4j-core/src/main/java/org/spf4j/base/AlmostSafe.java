
package org.spf4j.base;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
//CHECKSTYLE:OFF
import sun.misc.Unsafe;
//CHECKSTYLE:ON

/**
 * Expose
 * @author zoly
 */
public final class AlmostSafe {

    private AlmostSafe() { }

    public static final Unsafe USF;

    static {

        USF = AccessController.doPrivileged(new PrivilegedAction<Unsafe>() {
            @Override
            public Unsafe run() {
                try {
                    Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafe.setAccessible(true);
                    return (Unsafe) theUnsafe.get(null);
                } catch (IllegalArgumentException | IllegalAccessException
                        | NoSuchFieldException | SecurityException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }



}
