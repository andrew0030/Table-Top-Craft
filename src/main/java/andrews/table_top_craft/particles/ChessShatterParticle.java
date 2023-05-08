package andrews.table_top_craft.particles;

import andrews.table_top_craft.particles.options.ChessShatterParticleOptions;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Quaternionf;
import org.joml.Random;
import org.joml.Vector3f;

public class ChessShatterParticle extends TextureSheetParticle
{
    private final int rotation;
    private final boolean randomizeScale;

    protected ChessShatterParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, ChessShatterParticleOptions options, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.lifetime = 30;
        this.rCol = options.getRed() / 255F;
        this.gCol = options.getGreen() / 255F;
        this.bCol = options.getBlue() / 255F;
        Random random = new Random();
        this.scale(random.nextInt(3) == 0 ? 0.1F : 0.05F);
        this.pickSprite(sprites);
        this.yd = Math.abs(yd) + 0.02D;
        this.rotation = random.nextInt(9) * 20;
        this.randomizeScale = random.nextInt(2) == 0;
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks)
    {
        // Corners
        Vector3f[] cornerVectors = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        // Position
        float posX = (float)(Mth.lerp(partialTicks, this.xo, this.x) - renderInfo.getPosition().x());
        float posY = (float)(Mth.lerp(partialTicks, this.yo, this.y) - renderInfo.getPosition().y());
        float posZ = (float)(Mth.lerp(partialTicks, this.zo, this.z) - renderInfo.getPosition().z());
        // Rotation
        Quaternionf quaternion;
        if (this.roll == 0.0F)
        {
            quaternion = renderInfo.rotation();
        }
        else
        {
            quaternion = new Quaternionf(renderInfo.rotation());
            quaternion.rotateZ(Mth.lerp(partialTicks, this.oRoll, this.roll));
        }
        // Rotation Randomizer
        if (this.rotation != 0)
        {
            quaternion.mul(Axis.ZP.rotationDegrees(this.rotation));
        }
        // Transform the Quad's corner Vectors based on Camera Position and Rotation
        for(int i = 0; i < 4; ++i)
        {
            Vector3f vector3f = cornerVectors[i];
            if(this.randomizeScale)
                vector3f.mul(new Vector3f(2.0F, 1.0F, 1.0F));
            vector3f.rotate(quaternion);
            vector3f.mul(this.getQuadSize(partialTicks));
            vector3f.add(posX, posY, posZ);
            vector3f.add(0.0F, 0.01F, 0.0F);
        }

        // Render the Quad
        int light = this.getLightColor(partialTicks);
        buffer.vertex(cornerVectors[0].x(), cornerVectors[0].y(), cornerVectors[0].z()).uv(this.getU1(), this.getV1()).color(this.rCol, this.gCol, this.bCol, 1.0F).uv2(light).endVertex();
        buffer.vertex(cornerVectors[1].x(), cornerVectors[1].y(), cornerVectors[1].z()).uv(this.getU1(), this.getV0()).color(this.rCol, this.gCol, this.bCol, 1.0F).uv2(light).endVertex();
        buffer.vertex(cornerVectors[2].x(), cornerVectors[2].y(), cornerVectors[2].z()).uv(this.getU0(), this.getV0()).color(this.rCol, this.gCol, this.bCol, 1.0F).uv2(light).endVertex();
        buffer.vertex(cornerVectors[3].x(), cornerVectors[3].y(), cornerVectors[3].z()).uv(this.getU0(), this.getV1()).color(this.rCol, this.gCol, this.bCol, 1.0F).uv2(light).endVertex();
    }

    @Override
    public void tick()
    {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime)
        {
            double speedMod = 0.01;
            this.xd *= 0.8F;
            this.zd *= 0.8F;
            this.yd -= (this.age < 5) ? 0.0F : 0.08F;
            this.move(this.xd / 1.2F, this.yd / 3.5F, this.zd / 1.2F);
            if(age > 25)
                this.scale(1 - ((age - 25) / 5F));
        }
        else
        {
            this.remove();
        }
    }

    @Override
    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<ChessShatterParticleOptions>
    {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites)
        {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(ChessShatterParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            return new ChessShatterParticle(level, x, y, z, xSpeed, ySpeed + 0.1F, zSpeed, options, this.sprites);
        }
    }
}