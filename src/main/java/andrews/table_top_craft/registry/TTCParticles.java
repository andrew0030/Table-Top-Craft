package andrews.table_top_craft.registry;

import andrews.table_top_craft.particles.ChessShatterParticle;
import andrews.table_top_craft.particles.TinyPoofParticle;
import andrews.table_top_craft.particles.options.ChessShatterParticleOptions;
import andrews.table_top_craft.util.Reference;
import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TTCParticles
{
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Reference.MODID);

    public static final RegistryObject<ParticleType<ChessShatterParticleOptions>> CHESS_SHATTER    = PARTICLES.register("chess_shatter", () -> new ParticleType<>(false, ChessShatterParticleOptions.DESERIALIZER) {
        @Override
        public Codec<ChessShatterParticleOptions> codec()
        {
            return ChessShatterParticleOptions.CODEC;
        }
    });
    public static final RegistryObject<SimpleParticleType> TINY_POOF                                = PARTICLES.register("tiny_poof", () -> new SimpleParticleType(false));

    public static void registerParticles()
    {
        Minecraft.getInstance().particleEngine.register(CHESS_SHATTER.get(), ChessShatterParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(TINY_POOF.get(), TinyPoofParticle.Provider::new);
    }
}