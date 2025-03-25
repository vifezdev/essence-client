#version 330 core

uniform vec4 c1;
uniform vec4 c2;
uniform float iTime;

out vec4 FragColor;

void main() {
    vec2 fragCoord = gl_FragCoord.xy;
    float m = sin(fragCoord.x / 3.0 - fragCoord.y / 6.0 + mod(iTime, 10.0) * 10.0) * 2.0;
    FragColor = mix(c1, c2, smoothstep(0.0, 1.0, m));
}

#@legacy
#version 120

uniform vec4 c1;
uniform vec4 c2;
uniform float iTime;

void main() {
    vec2 fragCoord = gl_FragCoord.xy;
    float m = sin(fragCoord.x / 3.0 - fragCoord.y / 6.0 + mod(iTime, 10.0) * 10.0) * 2.0;
    gl_FragColor = mix(c1, c2, smoothstep(0.0, 1.0, m));
}