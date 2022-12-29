package realization.types

class Point2DType {
    private var x = 0f
    private var y = 0f

    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun getX(): Float {
        return x
    }

    fun getY(): Float {
        return this.y
    }

    fun setPoint2DValue(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    override fun toString(): String {
        return "(" + x.toString() + ";" + this.y.toString() + ")"
    }

}