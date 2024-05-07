package andrews.table_top_craft.util.shader_compat;

import java.util.concurrent.atomic.AtomicBoolean;

public class ShaderCompatHandler
{
    private static final AtomicBoolean OF_LOADED = new AtomicBoolean(false);
    private static final AtomicBoolean IRIS_LOADED = new AtomicBoolean(false);

    public static void initOFCompat()
    {
        ShaderCompatHandler.OF_LOADED.set(true);
        ShaderCompatOF.init();
    }

    public static void initIrisCompat()
    {
        ShaderCompatHandler.IRIS_LOADED.set(true);
        ShaderCompatIris.init();
    }

    /**
     * @return Whether there are any active Shaders
     */
    public static boolean isShaderActive()
    {
        if(ShaderCompatHandler.isOFLoaded())
            return ShaderCompatOF.isShaderLoaded();
        if(ShaderCompatHandler.isIrisLoaded())
            return ShaderCompatIris.isShaderLoaded();
        return false;
    }

    public static boolean isOFLoaded()
    {
        return ShaderCompatHandler.OF_LOADED.get();
    }

    public static boolean isIrisLoaded()
    {
        return ShaderCompatHandler.IRIS_LOADED.get();
    }
}