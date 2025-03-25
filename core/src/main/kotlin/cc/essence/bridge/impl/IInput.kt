package cc.essence.bridge.impl

import cc.essence.bridge.Bridge

abstract class IInput : Bridge {
    abstract fun getKeyName(key: Int): String
}