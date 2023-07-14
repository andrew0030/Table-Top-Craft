package andrews.table_top_craft.util;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.InputStream;
import java.util.*;

public class MixinPlugin implements IMixinConfigPlugin
{
    private static final HashMap<String, ArrayList<String>> incompatibilityMap = new HashMap<>();

    private static void addIncompat(String mixin, String... things) {
        ArrayList<String> incompat = new ArrayList<>();
        Collections.addAll(incompat, things);
        incompatibilityMap.put(mixin, incompat);
    }

    static {
        addIncompat("andrews.table_top_craft.mixins.ShaderInstanceMixin", "me.modmuss50.optifabric.mod.OptifabricSetup");
    }

    @Override
    public void onLoad(String mixinPackage) {}

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
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}