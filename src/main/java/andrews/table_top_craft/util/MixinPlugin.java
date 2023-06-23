package andrews.table_top_craft.util;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MixinPlugin implements IMixinConfigPlugin {
	private static final HashMap<String, ArrayList<String>> incompatibilityMap = new HashMap<>();
	
	private static void addIncompat(
			String mixin, String... things
	) {
		ArrayList<String> incompat = new ArrayList<>();
		Collections.addAll(incompat, things);
		incompatibilityMap.put(mixin, incompat);
	}
	
	static {
		addIncompat(
				"andrews.table_top_craft.mixins.ShaderInstanceMixin",
				"me.modmuss50.optifabric.mod.OptifabricSetup"
		);
	}
	
	@Override
	public void onLoad(String mixinPackage) {
	}
	
	@Override
	public String getRefMapperConfig() {
		return null;
	}
	
	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (incompatibilityMap.containsKey(mixinClassName)) {
			ClassLoader loader = MixinPlugin.class.getClassLoader();
			// tests if the classloader contains a .class file for the target
			for (String name : incompatibilityMap.get(mixinClassName)) {
				InputStream stream = loader.getResourceAsStream(name.replace('.', '/') + ".class");
				if (stream == null) {
					return true;
				} else {
					try {
						stream.close();
						return false;
					} catch (Throwable ignored) {
						return false;
					}
				}
			}
			return false;
		}
		return true;
	}
	
	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	
	}
	
	@Override
	public List<String> getMixins() {
		return null;
	}
	
	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	
	}
	
//	Logger LOGGER = LoggerFactory.getLogger("MIXIN DEBUG");
	
	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
//		if (
//				mixinClassName.equals("andrews.table_top_craft.mixins.ShaderInstanceMixin") ||
//						mixinClassName.equals("andrews.table_top_craft.mixins.BlockEntityWithoutLevelRendererMixin")
//		) {
//			try {
//				File fl = new File(targetClass.name.substring(targetClass.name.lastIndexOf("/") + 1) + "-post.class");
//				FileOutputStream outputStream = new FileOutputStream(fl);
//				LOGGER.error(fl.getAbsoluteFile().toString());
//				ClassWriter writer = new ClassWriter(0);
//				targetClass.accept(writer);
//				outputStream.write(writer.toByteArray());
//				outputStream.flush();
//				outputStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
}
