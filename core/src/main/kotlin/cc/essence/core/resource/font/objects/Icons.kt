@file:Suppress("FunctionName")

package cc.essence.core.resource.font.objects

import cc.essence.core.resource.ResourceManager
import cc.essence.core.resource.font.util.IconsLineAwesome
import cc.essence.icons.IconsMaterialRound

object Icons {
    val lineAwesome = ResourceManager.getFont("line-awesome")
    val materialRound = ResourceManager.getFont("material-round")
}

fun LA(icon: IconsLineAwesome.() -> String): String = icon(IconsLineAwesome)
fun MR(icon: IconsMaterialRound.() -> String): String = icon(IconsMaterialRound)