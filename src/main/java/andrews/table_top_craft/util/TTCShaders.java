package andrews.table_top_craft.util;

import com.github.andrew0030.pandora_core.modules.templater.TemplateManager;
import com.github.andrew0030.pandora_core.modules.templater.wrapper.ShaderWrapper;
import com.github.andrew0030.pandora_core.modules.instancer.state.PaCoShaderStateShard;
import net.minecraft.resources.ResourceLocation;

public class TTCShaders {
    public static final ShaderWrapper CHESS_INSTANCED = TemplateManager.getWrapper(new ResourceLocation("table_top_craft:shaders/paco/templated/chess_instanced"));
    public static final PaCoShaderStateShard CHESS_INSTANCED_SHARD = new PaCoShaderStateShard(CHESS_INSTANCED);
}
