package andrews.table_top_craft.registry;

import andrews.table_top_craft.particles.ChessShatterParticle;
import andrews.table_top_craft.particles.TinyPoofParticle;
import andrews.table_top_craft.particles.options.ChessShatterParticleOptions;
import andrews.table_top_craft.util.Reference;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class TTCParticles
{
    public static final ParticleType<ChessShatterParticleOptions> CHESS_SHATTER     = Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(Reference.MODID, "chess_shatter"), new ParticleType<>(false, ChessShatterParticleOptions.DESERIALIZER) {
        @Override
        public Codec<ChessShatterParticleOptions> codec()
        {
            return ChessShatterParticleOptions.CODEC;
        }
    });
    public static final SimpleParticleType TINY_POOF            = Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(Reference.MODID, "tiny_poof"), FabricParticleTypes.simple());

    public static void init() {}

    public static void registerParticles()
    {
        ParticleFactoryRegistry.getInstance().register(CHESS_SHATTER, ChessShatterParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(TINY_POOF, TinyPoofParticle.Provider::new);
    }
}