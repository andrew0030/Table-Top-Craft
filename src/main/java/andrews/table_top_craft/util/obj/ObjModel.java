package andrews.table_top_craft.util.obj;

import andrews.table_top_craft.mixin_util.IOptimizedMatrix3f;
import andrews.table_top_craft.mixin_util.IOptimizedMatrix4f;
import andrews.table_top_craft.util.Reference;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.apache.commons.compress.utils.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ObjModel
{
    private final Vector3f[] v;
    private final Vec2[] vt;
    private final Vector3f[] vn;
    private final Face[] faces;

    private ObjModel(Vector3f[] v, Vec2[] vt, Vector3f[] vn, Face[] faces)
    {
        this.v = v;
        this.vt = vt;
        this.vn = vn;
        this.faces = faces;
    }
    
    public void render(PoseStack stack, VertexConsumer buffer)
    {
        try
        {
            for(Face face : faces)
            {
                Vector3f v1 = v[face.v1 - 1];
                Vector3f v2 = v[face.v2 - 1];
                Vector3f v3 = v[face.v3 - 1];

                Vec2 vt1 = vt[face.vt1 - 1];
                Vec2 vt2 = vt[face.vt2 - 1];
                Vec2 vt3 = vt[face.vt3 - 1];

                Vector3f vn1 = vn[face.vn1 - 1];
                Vector3f vn2 = vn[face.vn2 - 1];
                Vector3f vn3 = vn[face.vn3 - 1];

                addVertex(stack, buffer, v1.x(), v1.y(), v1.z(), vt1.x, -vt1.y, vn1.x(), vn1.y(), vn1.z());
                addVertex(stack, buffer, v2.x(), v2.y(), v2.z(), vt2.x, -vt2.y, vn2.x(), vn2.y(), vn2.z());
                addVertex(stack, buffer, v3.x(), v3.y(), v3.z(), vt3.x, -vt3.y, vn3.x(), vn3.y(), vn3.z());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void render(PoseStack stack, VertexConsumer buffer, float red, float green, float blue, int packedLight)
    {
        try
        {
            for(Face face : faces)
            {
                Vector3f v1 = v[face.v1 - 1];
                Vector3f v2 = v[face.v2 - 1];
                Vector3f v3 = v[face.v3 - 1];

                Vec2 vt1 = vt[face.vt1 - 1];
                Vec2 vt2 = vt[face.vt2 - 1];
                Vec2 vt3 = vt[face.vt3 - 1];

                Vector3f vn1 = vn[face.vn1 - 1];
                Vector3f vn2 = vn[face.vn2 - 1];
                Vector3f vn3 = vn[face.vn3 - 1];

                buffer.vertex(stack.last().pose(), v1.x(), v1.y(), v1.z()).color(red, green, blue, 1F).uv(vt1.x, -vt1.y).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(stack.last().normal(), vn1.x(), vn1.y(), vn1.z()).endVertex();
                buffer.vertex(stack.last().pose(), v2.x(), v2.y(), v2.z()).color(red, green, blue, 1F).uv(vt2.x, -vt2.y).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(stack.last().normal(), vn2.x(), vn2.y(), vn2.z()).endVertex();
                buffer.vertex(stack.last().pose(), v3.x(), v3.y(), v3.z()).color(red, green, blue, 1F).uv(vt3.x, -vt3.y).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(stack.last().normal(), vn3.x(), vn3.y(), vn3.z()).endVertex();
                buffer.vertex(stack.last().pose(), v3.x(), v3.y(), v3.z()).color(red, green, blue, 1F).uv(vt3.x, -vt3.y).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(packedLight).normal(stack.last().normal(), vn3.x(), vn3.y(), vn3.z()).endVertex();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addVertex(PoseStack stack, VertexConsumer builder, float x, float y, float z, float u, float v, float nx, float ny, float nz)
    {
        Matrix4f matrix4f = stack.last().pose();
        Matrix3f matrix3f = stack.last().normal();

        ((IOptimizedMatrix4f)(Object)matrix4f).optimizedPos(builder, x, y, z);
    	builder.color(1F, 1F, 1F, 1F);
    	builder.uv(u, v);
//        .overlayCoords(0, 0) // We use the DefaultVertexFormat.BLOCK so there is no need for an overlay
    	builder.uv2(15, 15); // These values are full brightness
        ((IOptimizedMatrix3f)(Object)matrix3f).optimizedNormal(builder, nx, ny, nz);
    	builder.endVertex();
    }
    
    public static ObjModel loadModel(ResourceLocation resourceLocation)
    {
        byte[] modelData = loadResource(resourceLocation);
        String modelString = new String(modelData);
        String[] modelLines = modelString.split("\\r?\\n");
        
        ArrayList<Vector3f> vList = new ArrayList<Vector3f>();
        ArrayList<Vec2> vtList = new ArrayList<Vec2>();
        ArrayList<Vector3f> vnList = new ArrayList<Vector3f>();
        ArrayList<Face> faceList = new ArrayList<Face>();

        for(String line : modelLines)
        {
            String[] lineSpit = line.split(" ");
            if (lineSpit[0].equals("v"))
            {
                vList.add(new Vector3f(Float.parseFloat(lineSpit[1]), Float.parseFloat(lineSpit[2]), Float.parseFloat(lineSpit[3])));
            }
            if (lineSpit[0].equals("vt"))
            {
                vtList.add(new Vec2(Float.parseFloat(lineSpit[1]), Float.parseFloat(lineSpit[2])));
            }
            if (lineSpit[0].equals("vn"))
            {
                vnList.add(new Vector3f(Float.parseFloat(lineSpit[1]), Float.parseFloat(lineSpit[2]), Float.parseFloat(lineSpit[3])));
            }
            if (lineSpit[0].equals("f"))
            {
                faceList.add(new Face(lineSpit[1], lineSpit[2], lineSpit[3]));
            }
        }
        
        Vector3f[] vArray = vList.toArray(new Vector3f[vList.size()]);
        Vec2[] vtArray = vtList.toArray(new Vec2[vtList.size()]);
        Vector3f[] vnArray = vnList.toArray(new Vector3f[vnList.size()]);
        Face[] faces = faceList.toArray(new Face[faceList.size()]);
        
        return new ObjModel(vArray, vtArray, vnArray, faces);
    }
    
    private static byte[] loadResource(ResourceLocation resourceLocation)
    {
        InputStream input = null;
        ByteArrayOutputStream output = null;
        try {
            input = ObjModel.class.getClassLoader().getResourceAsStream("assets/" + Reference.MODID + "/" + resourceLocation.getPath());
            if(input != null)
            {
                output = new ByteArrayOutputStream();
                IOUtils.copy(input, output);
                output.flush();
                return output.toByteArray();
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
        return null;
    }
    
    private static class Face
    {
        // Vertex
        public int v1;
        public int v2;
        public int v3;
        // Texture
        public int vt1;
        public int vt2;
        public int vt3;
        // Normal
        public int vn1;
        public int vn2;
        public int vn3;
        
        // f v1/vt1/vn1 v2/vt2/vn2 v3/vt3/vn3
        
        public Face(String v1, String v2, String v3)
        {
            String[] s1 = v1.split("/");
            String[] s2 = v2.split("/");
            String[] s3 = v3.split("/");
            
            this.v1 = Integer.parseInt(s1[0]);
            this.vt1 = Integer.parseInt(s1[1]);
            this.vn1 = Integer.parseInt(s1[2]);
            
            this.v2 = Integer.parseInt(s2[0]);
            this.vt2 = Integer.parseInt(s2[1]);
            this.vn2 = Integer.parseInt(s2[2]);
            
            this.v3 = Integer.parseInt(s3[0]);
            this.vt3 = Integer.parseInt(s3[1]);
            this.vn3 = Integer.parseInt(s3[2]);
        }
    }
}