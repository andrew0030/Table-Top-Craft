package andrews.table_top_craft.util.shader_compat;

import net.coderbot.iris.Iris;

import java.util.concurrent.atomic.AtomicBoolean;

public class ShaderCompatHandler
{
    private static final AtomicBoolean OF_LOADED = new AtomicBoolean(false);
    private static final AtomicBoolean IRIS_LOADED = new AtomicBoolean(false);

    /**
     * Called when OF is used, causes the OF compat class
     * to load and marks OF as "existing".
     */
    public static void initOFCompat()
    {
        ShaderCompatHandler.OF_LOADED.set(true);
        ShaderCompatOF.init();
    }

    public static void initIrisCompat()
    {
        ShaderCompatHandler.IRIS_LOADED.set(true);
    }

    /**
     * @return Whether there are any active Shaders
     */
    public static boolean isShaderActive()
    {
        if(ShaderCompatHandler.OF_LOADED.get())
            return ShaderCompatOF.isShaderLoaded();
        if(ShaderCompatHandler.IRIS_LOADED.get())
            return Iris.getCurrentPack().isPresent();
        return false;
    }
}