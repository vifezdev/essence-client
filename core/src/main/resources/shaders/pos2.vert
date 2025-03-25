#version 330 core

layout (location = 0) in vec2 aPos;

void main() {
    gl_Position = vec4(aPos, 0.0, 1.0);
}

#@legacy
#version 120

void main() {
    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}