package cc.essence.core.gl

import cc.essence.core.gl.vertex.VertexData
import cc.essence.core.logger
import org.lwjgl.opengl.GL20.*

class Shader(private val vertex: String, private val fragment: String) {
    private var program = 0

    fun create() {
        val vertexShader = createShader(GL_VERTEX_SHADER, vertex)
        val fragmentShader = createShader(GL_FRAGMENT_SHADER, fragment)

        program = glCreateProgram()
        glAttachShader(program, vertexShader)
        glAttachShader(program, fragmentShader)

        glLinkProgram(program)

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            throw RuntimeException("Failed to link program: ${glGetProgramInfoLog(program)}")
        }

        glValidateProgram(program)

        if (glGetProgrami(program, GL_VALIDATE_STATUS) == GL_FALSE) {
            throw RuntimeException("Failed to validate program: ${glGetProgramInfoLog(program)}")
        }

        glDeleteShader(vertexShader)
        glDeleteShader(fragmentShader)
    }

    private fun createShader(type: Int, source: String): Int {
        var src = source

        val regex = Regex(pattern = "#@legacy", options = setOf(RegexOption.IGNORE_CASE))
        if (source.contains(regex)) {
            val splitSrc = source.split(regex)
            src = if (GLZ.isLegacy()) splitSrc[1] else splitSrc[0]
        } else {
            logger.warn { "Shader doesnt contain LEGACY counterpart, its most likely not going to work on legacy minecraft versions!" }
        }

        val shader = glCreateShader(type)
        glShaderSource(shader, src)
        glCompileShader(shader)

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw RuntimeException("Failed to compile shader: ${glGetShaderInfoLog(shader)}")
        }

        return shader
    }

    fun loc(name: String): Int = glGetUniformLocation(program, name)

    fun use(vertexData: VertexData, block: Shader.() -> Unit = {}) {
        glUseProgram(program)
        block(this)
        vertexData.render()
        glUseProgram(0)
    }

    fun setUniform(name: String, x: Float, y: Float, z: Float, w: Float) {
        glUniform4f(loc(name), x, y, z, w)
    }

    fun setUniform(name: String, x: Float, y: Float, z: Float) {
        glUniform3f(loc(name), x, y, z)
    }

    fun setUniform(name: String, x: Float, y: Float) {
        glUniform2f(loc(name), x, y)
    }

    fun setUniform(name: String, x: Float) {
        glUniform1f(loc(name), x)
    }
}