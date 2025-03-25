package cc.essence.core.gl

import org.lwjgl.opengl.GL30.*

class FBO(private var width: Int, private var height: Int) {
    var id = glGenFramebuffers()
    var textureId: Int = -1
    private var depthStencilBuffer: Int = -1


    fun create() {
        // Generate texture
        textureId = glGenTextures()
        glBindTexture(GL_TEXTURE_2D, textureId)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, 0)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glBindTexture(GL_TEXTURE_2D, 0)

        // Generate depth and stencil buffer
        depthStencilBuffer = glGenRenderbuffers()
        glBindRenderbuffer(GL_RENDERBUFFER, depthStencilBuffer)
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height)
        glBindRenderbuffer(GL_RENDERBUFFER, 0)

        // Bind FBO and attach texture and depth+stencil buffer
        glBindFramebuffer(GL_FRAMEBUFFER, id)
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, textureId, 0)
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, depthStencilBuffer)

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            throw RuntimeException("Framebuffer is not complete")
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0)
    }

    fun resize(newWidth: Int, newHeight: Int) {
        if (width == newWidth && height == newHeight) return

        width = newWidth
        height = newHeight

        // Cleanup old resources
        glDeleteFramebuffers(id)
        glDeleteTextures(textureId)
        glDeleteRenderbuffers(depthStencilBuffer)

        // Recreate FBO with new dimensions
        id = glGenFramebuffers()
        create()
    }

    fun bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, id)
        glViewport(0, 0, width, height)
    }

    fun unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
    }

    fun cleanup() {
        glDeleteFramebuffers(id)
        glDeleteTextures(textureId)
        glDeleteRenderbuffers(depthStencilBuffer)
    }
}