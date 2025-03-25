package cc.essence.core.data

class SliderData<T: Number>(var value: T, val min: T, val max: T, val step: T) {
    operator fun getValue(thisRef: Any?, property: Any?): T {
        return value
    }

    operator fun setValue(thisRef: Any?, property: Any?, value: T) {
        this.value = value
    }
}