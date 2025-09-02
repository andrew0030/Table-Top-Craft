#version 150

#moj_import <fog.glsl>

uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 color;
//in vec4 normal;

out vec4 fragColor;

void main() {
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
