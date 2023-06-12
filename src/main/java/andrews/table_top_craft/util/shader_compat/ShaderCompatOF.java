package andrews.table_top_craft.util.shader_compat;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class ShaderCompatOF
{
    private static final MethodHandle handle;

    static void init() {}

    static {
        try {
            MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
            MethodType mt = MethodType.methodType(boolean.class);
            handle = publicLookup.findStatic(Class.forName("net.optifine.Config"), "isShaders", mt);
        } catch (Throwable ignored) {
            throw new RuntimeException();
        }
    }

    static boolean isShaderLoaded() {
        if(handle != null) {
            try {
                return (boolean) handle.invoke();
            } catch (Throwable ignored) {}
        }
        return false;
    }
}