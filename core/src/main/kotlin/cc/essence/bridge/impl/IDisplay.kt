package cc.essence.bridge.impl

import cc.essence.bridge.Bridge

abstract class IDisplay : Bridge {
    abstract var width: Int
        protected set
    abstract var height: Int
        protected set
    abstract var title: String
}