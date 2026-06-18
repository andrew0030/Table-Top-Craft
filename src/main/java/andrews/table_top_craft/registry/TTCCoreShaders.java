package andrews.table_top_craft.registry;

import andrews.table_top_craft.TableTopCraft;
import com.github.andrew0030.pandora_core.PandoraCore;
import com.github.andrew0030.pandora_core.client.registry.PaCoCoreShaders;
import com.github.andrew0030.pandora_core.utils.resource.PacoResourceManager;
import com.github.andrew0030.pandora_core.utils.resource.ResourceDispatcher;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class TTCCoreShaders implements PacoResourceManager {
	public static ShaderInstance chessPiece;
	
	@Override
	public void run(ResourceManager manager, ResourceDispatcher dispatcher) {
		dispatcher.apply("ttc_core_shader_loading", () -> {
			if (TTCCoreShaders.chessPiece != null)
				TTCCoreShaders.chessPiece.close();
			try {
				TTCCoreShaders.chessPiece = new ShaderInstance(manager, "table_top_craft:chess", DefaultVertexFormat.NEW_ENTITY);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
