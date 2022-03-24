package andrews.table_top_craft.util.obj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.compress.utils.IOUtils;

import com.mojang.blaze3d.matrix.MatrixStack;

import andrews.table_top_craft.util.Reference;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ObjModel
{
    private Vector3f[] v;
    private Vector2f[] vt;
    private Vector3f[] vn;
    private Face[] faces;
    
    //on static init TODO remove if not needed
//    static
//    {
    	//this needs to be called every time resource manager reloads (I think)
//        texture.loadTexture(Minecraft.getInstance().getResourceManager());
        //before rendering vbo
//        image.setPixelRGBA(0, 0, new Color(255, 255, 255).getRGB());
//    }
    
    private ObjModel(Vector3f[] v, Vector2f[] vt, Vector3f[] vn, Face[] faces)
    {
        this.v = v;
        this.vt = vt;
        this.vn = vn;
        this.faces = faces;
    }
    
    public void render(MatrixStack stack, BufferBuilder buffer)
    {    	
        try
        {
            for(int i = 0; i < faces.length; i++)
            {
                Face face = faces[i];
                
                Vector3f v1 = v[face.v1 - 1];
                Vector3f v2 = v[face.v2 - 1];
                Vector3f v3 = v[face.v3 - 1];
                
                Vector2f vt1 = vt[face.vt1 - 1];
                Vector2f vt2 = vt[face.vt2 - 1];
                Vector2f vt3 = vt[face.vt3 - 1];
                
                Vector3f vn1 = vn[face.vn1 - 1];
                Vector3f vn2 = vn[face.vn2 - 1];
                Vector3f vn3 = vn[face.vn3 - 1];
                
                addVertex(stack, buffer, v1.getX(), v1.getY(), v1.getZ(), vt1.x, -vt1.y, vn1.getX(), vn1.getY(), vn1.getZ());
                addVertex(stack, buffer, v2.getX(), v2.getY(), v2.getZ(), vt2.x, -vt2.y, vn2.getX(), vn2.getY(), vn2.getZ());
                addVertex(stack, buffer, v3.getX(), v3.getY(), v3.getZ(), vt3.x, -vt3.y, vn3.getX(), vn3.getY(), vn3.getZ());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addVertex(MatrixStack stack, BufferBuilder builder, float x, float y, float z, float u, float v, float nx, float ny, float nz)
    {
    	pos(builder, stack.getLast().getMatrix(), x, y, z)
    	.color(1F, 1F, 1F, 1F)
    	.tex(u, v)
    	.lightmap(0, 240); // These values are full brightness
    	normal(builder, stack.getLast().getNormal(), nx, ny, nz)
    	.endVertex();   
    }
    
	private static BufferBuilder pos(BufferBuilder bufferBuilder, Matrix4f matrix4f, float x, float y, float z)
	{
		// Calling 'bufferBuilder.pos(matrix4f, x, y, z)' allocates a Vector4f
		// To avoid allocating so many short lived vectors we do the transform ourselves instead
		float w = 1.0F;
		float tx = matrix4f.m00 * x + matrix4f.m01 * y + matrix4f.m02 * z + matrix4f.m03 * w;
		float ty = matrix4f.m10 * x + matrix4f.m11 * y + matrix4f.m12 * z + matrix4f.m13 * w;
		float tz = matrix4f.m20 * x + matrix4f.m21 * y + matrix4f.m22 * z + matrix4f.m23 * w;
		
		return (BufferBuilder) bufferBuilder.pos(tx, ty, tz);
	}
	
	private static BufferBuilder normal(BufferBuilder bufferBuilder, Matrix3f matrix3f, float x, float y, float z)
	{
		// Calling 'bufferBuilder.normal(matrix3f, x, y, z)' allocates a Vector3f
		// To avoid allocating so many short lived vectors we do the transform ourselves instead
	    float nx = matrix3f.m00 * x + matrix3f.m01 * y + matrix3f.m02 * z;
	    float ny = matrix3f.m10 * x + matrix3f.m11 * y + matrix3f.m12 * z;
	    float nz = matrix3f.m20 * x + matrix3f.m21 * y + matrix3f.m22 * z;
	      
	    return (BufferBuilder) bufferBuilder.normal(nx, ny, nz);
	}
    
    public static ObjModel loadModel(ResourceLocation resourceLocation)
    {
        byte[] modelData = loadResource(resourceLocation);
        String modelString = new String(modelData);
        String[] modelLines = modelString.split("\\r?\\n");
        
        ArrayList<Vector3f> vList = new ArrayList<Vector3f>();
        ArrayList<Vector2f> vtList = new ArrayList<Vector2f>();
        ArrayList<Vector3f> vnList = new ArrayList<Vector3f>();
        ArrayList<Face> faceList = new ArrayList<Face>();
        
        for(int i = 0; i < modelLines.length; i++)
        {
            String line = modelLines[i];
            String[] lineSpit = line.split(" ");
            if(lineSpit[0].equals("v"))
            {
                vList.add(new Vector3f(Float.parseFloat(lineSpit[1]), Float.parseFloat(lineSpit[2]), Float.parseFloat(lineSpit[3])));
            }
            if(lineSpit[0].equals("vt"))
            {
                vtList.add(new Vector2f(Float.parseFloat(lineSpit[1]), Float.parseFloat(lineSpit[2])));
            }
            if(lineSpit[0].equals("vn"))
            {
                vnList.add(new Vector3f(Float.parseFloat(lineSpit[1]), Float.parseFloat(lineSpit[2]), Float.parseFloat(lineSpit[3])));
            }
            if(lineSpit[0].equals("f"))
            {
                faceList.add(new Face(lineSpit[1], lineSpit[2], lineSpit[3]));
            }
        }
        
        Vector3f[] vArray = vList.toArray(new Vector3f[vList.size()]);
        Vector2f[] vtArray = vtList.toArray(new Vector2f[vtList.size()]);
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
                byte[] data = output.toByteArray();
                return data;
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