package andrews.table_top_craft.util.shader_compat;

import java.util.concurrent.atomic.AtomicBoolean;

public class ShaderCompatHandler
{
    private static final AtomicBoolean OF_LOADED = new AtomicBoolean(false);
    private static final AtomicBoolean OCULUS_LOADED = new AtomicBoolean(false);

    public static void initOFCompat()
    {
        ShaderCompatHandler.OF_LOADED.set(true);
        ShaderCompatOF.init();
    }

    public static void initOculusCompat()
    {
        ShaderCompatHandler.OCULUS_LOADED.set(true);
        ShaderCompatOculus.init();
    }

    /**
     * @return Whether there are any active Shaders
     */
    public static boolean isShaderActive()
    {
        if(ShaderCompatHandler.OF_LOADED.get())
            return ShaderCompatOF.isShaderLoaded();
        if(ShaderCompatHandler.OCULUS_LOADED.get())
            return ShaderCompatOculus.isShaderLoaded();
        return false;
    }
}