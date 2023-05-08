package andrews.table_top_craft.util.shader_compat;

import java.util.concurrent.atomic.AtomicBoolean;

public class ShaderCompatHandler
{
    private static final AtomicBoolean OF_LOADED = new AtomicBoolean(false);

    /**
     * Called when OF is used, causes the OF compat class
     * to load and marks OF as "existing".
     */
    public static void initOFCompat()
    {
        ShaderCompatHandler.OF_LOADED.set(true);
        ShaderCompatOF.init();
    }

    /**
     * @return Whether there are any active Shaders
     */
    public static boolean isShaderActive()
    {
        if(ShaderCompatHandler.OF_LOADED.get())
            return ShaderCompatOF.isShaderLoaded();
        return false;
    }
}