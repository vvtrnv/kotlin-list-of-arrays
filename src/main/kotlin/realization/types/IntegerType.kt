package realization.types

class IntegerType {
    private var value = 0

    constructor(value : Int) {
        this.value = value
    }

    fun getIntValue(): Int {
        return value
    }

    fun setIntValue(value: Int) {
        this.value = value
    }

    override fun toString(): String {
        return value.toString()
    }
}