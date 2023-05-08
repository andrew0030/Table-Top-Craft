package andrews.table_top_craft.particles.options;

import andrews.table_top_craft.registry.TTCParticles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

import java.util.Locale;

public class ChessShatterParticleOptions implements ParticleOptions
{
    // Colors
    private final int red;
    private final int green;
    private final int blue;
    // Deserializer
    public static final ParticleOptions.Deserializer<ChessShatterParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<>()
    {
        @Override
        public ChessShatterParticleOptions fromCommand(ParticleType<ChessShatterParticleOptions> particleType, StringReader reader) throws CommandSyntaxException
        {
            reader.expect(' ');
            int red = reader.readInt();
            int green = reader.readInt();
            int blue = reader.readInt();
            return new ChessShatterParticleOptions(red, green, blue);
        }

        @Override
        public ChessShatterParticleOptions fromNetwork(ParticleType<ChessShatterParticleOptions> particleType, FriendlyByteBuf buffer)
        {
            return new ChessShatterParticleOptions(buffer.readInt(), buffer.readInt(), buffer.readInt());
        }
    };
    // Codec
    public static final Codec<ChessShatterParticleOptions> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
        Codec.INT.fieldOf("red").forGetter(ChessShatterParticleOptions::getRed),
        Codec.INT.fieldOf("green").forGetter(ChessShatterParticleOptions::getGreen),
        Codec.INT.fieldOf("blue").forGetter(ChessShatterParticleOptions::getBlue)
    ).apply(builder, ChessShatterParticleOptions::new));

    public ChessShatterParticleOptions(int red, int green, int blue)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public ParticleType<?> getType()
    {
        return TTCParticles.CHESS_SHATTER.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buffer)
    {
        buffer.writeInt(this.red);
        buffer.writeInt(this.green);
        buffer.writeInt(this.blue);
    }

    @Override
    public String writeToString()
    {
        return String.format(Locale.ROOT, "%s %d %d %d", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.red, this.green, this.blue);
    }

    public int getRed()
    {
        return red;
    }

    public int getGreen()
    {
        return green;
    }

    public int getBlue()
    {
        return blue;
    }
}