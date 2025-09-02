#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
//in ivec2 UV1;
#define UV1 ivec2(0, 10)
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat3 IViewRotMat;
uniform int FogShape;

uniform vec3 Light0_Direction;
uniform vec3 Light1_Direction;

out float vertexDistance;
out vec4 color;
//out vec4 normal;

uniform vec4 ColorModulator;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexDistance = fog_distance(ModelViewMat, IViewRotMat * Position, FogShape);
    vec4 lightMapColor = texelFetch(Sampler2, UV2 / 16, 0);
    vec4 overlayColor = texelFetch(Sampler1, UV1, 0);

    vec4 vertexColor = minecraft_mix_light(Light0_Direction, Light1_Direction, Normal, Color);
    vertexColor *= ColorModulator;

    vertexColor.rgb = mix(overlayColor.rgb, vertexColor.rgb, overlayColor.a);
    vertexColor *= lightMapColor;

    color = vertexColor;

//    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
}
