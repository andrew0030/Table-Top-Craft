package andrews.table_top_craft.util.obj;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.compress.utils.IOUtils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import andrews.table_top_craft.util.Reference;
import andrews.table_top_craft.util.TTCRenderTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;
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
//    private int modelList = -1;
    
    static final NativeImage image = new NativeImage(NativeImage.PixelFormat.RGBA, 1, 1, true);
    static final DynamicTexture texture = new DynamicTexture(image);
    static ResourceLocation resourceLocation = null;
    
    //on static init
    static
    {
    	//this needs to be called every time resource manager reloads (I think)
//        texture.loadTexture(Minecraft.getInstance().getResourceManager());
        //before rendering vbo
        image.setPixelRGBA(0, 0, new Color(255, 255, 255).getRGB());
    }
    
    private ObjModel(Vector3f[] v, Vector2f[] vt, Vector3f[] vn, Face[] faces)
    {
        this.v = v;
        this.vt = vt;
        this.vn = vn;
        this.faces = faces;
    }
    
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int combinedLightIn, float red, float green, float blue)
    {
//        if(modelList == -1)
//        {
//            modelList = GL11.glGenLists(1);
//            
//            GL11.glNewList(modelList, GL11.GL_COMPILE);
            renderModel(stack, buffer, combinedLightIn, red, green, blue);
//            GL11.glEndList();
//        }
//        GL11.glCallList(modelList);
    }
    
    private void renderModel(MatrixStack stack, IRenderTypeBuffer buffer, int combinedLightIn, float red, float green, float blue)
    {
    	image.setPixelRGBA(0, 0, new Color(blue, green, red).getRGB());
    	texture.updateDynamicTexture();
    	if(resourceLocation == null)
    		resourceLocation = Minecraft.getInstance().getTextureManager().getDynamicTextureLocation("table_top_craft_dummy", texture);
    	
    	
    	
    	IVertexBuilder ivertexbuilder = buffer.getBuffer(TTCRenderTypes.getChessPieceSolid(resourceLocation));
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
                
                addVertex(stack, ivertexbuilder, v1.getX(), v1.getY(), v1.getZ(), red, green, blue, 1F, vt1.x, -vt1.y, combinedLightIn, vn1.getX(), vn1.getY(), vn1.getZ());
                addVertex(stack, ivertexbuilder, v2.getX(), v2.getY(), v2.getZ(), red, green, blue, 1F, vt2.x, -vt2.y, combinedLightIn, vn2.getX(), vn2.getY(), vn2.getZ());
                addVertex(stack, ivertexbuilder, v3.getX(), v3.getY(), v3.getZ(), red, green, blue, 1F, vt3.x, -vt3.y, combinedLightIn, vn3.getX(), vn3.getY(), vn3.getZ());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void addVertex(MatrixStack stack, IVertexBuilder builder, float x, float y, float z, float r, float g, float b, float alpha, float u, float v, int lightmapUV, float nx, float ny, float nz)
    {
    	builder.pos(stack.getLast().getMatrix(), x, y, z)
    		   .color(1F, 1F, 1F, 1F)
    		   .tex(u, v)
    		   .lightmap(lightmapUV)
    		   .normal(stack.getLast().getNormal(), nx, ny, nz)
    		   .endVertex();   
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