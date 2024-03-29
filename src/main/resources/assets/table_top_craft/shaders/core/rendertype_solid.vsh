#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;
//in vec2 UV0;
//in ivec2 UV1;
//in ivec2 UV2;
in vec3 Normal;

//uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

uniform vec2 LightUV;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat3 IViewRotMat;
//uniform vec3 ChunkOffset;

uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;

out float vertexDistance;
out vec4 vertexColor;
out vec4 lightMapColor;
out vec4 overlayColor;
//out vec2 texCoord0;
out vec4 normal;

void main() {
    vec3 pos = Position/* + ChunkOffset*/;
    gl_Position = ProjMat * ModelViewMat * vec4(pos, 1.0);

    // for whatever reason, the normals are inversed
    vec3 tempNormal = Normal * vec3(-1);

    vec4 transformed = vec4(pos, 1.0) * ModelViewMat;
//    vertexDistance = transformed.length();
    vertexDistance = fog_distance(ModelViewMat, IViewRotMat * Position, 0);//replaced cylindrical_distance with fog_distance and added 0
    vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, tempNormal, vec4(1));
    lightMapColor = texelFetch(Sampler2, ivec2(LightUV), 0);
//    overlayColor = texelFetch(Sampler1, UV1, 0);
//    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * vec4(tempNormal, 0.0);
}