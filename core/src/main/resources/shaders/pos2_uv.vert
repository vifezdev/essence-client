#version 330 core

layout (location = 0) in vec2 aPos;
layout (location = 1) in vec2 aTexCoord;

out vec2 uv;

void main() {
    uv = aTexCoord;
    gl_Position = vec4(aPos, 0.0, 1.0);
}

#@legacy
#version 120

varying vec2 uv;

void main() {
    uv = gl_MultiTexCoord0.xy;
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}