package andrews.table_top_craft.util.shader_compat;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Optional;

public class ShaderCompatOculus
{
    private static final MethodHandle handle;

    static void init() {}

    static {
        try {
            MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
            MethodType mt = MethodType.methodType(Optional.class);
            boolean usesNewPath = true;
            try {
                Class<?> clazz = Class.forName("net.coderbot.iris.Iris");
                if (clazz != null)
                    usesNewPath = false;
            } catch (Throwable ignored) {}
            String classPath = usesNewPath ? "net.irisshaders.iris.Iris" : "net.coderbot.iris.Iris";
            handle = publicLookup.findStatic(Class.forName(classPath), "getCurrentPack", mt);
        } catch (Throwable ignored) {
            throw new RuntimeException();
        }
    }

    static boolean isShaderLoaded() {
        if(handle != null) {
            try {
                return ((Optional<?>) handle.invoke()).isPresent();
            } catch (Throwable ignored) {}
        }
        return false;
    }
}