package realization.types.comparators

import realization.types.Point2DType
import kotlin.math.pow
import kotlin.math.sqrt

class Point2DComporator : Comporator{
    private fun getLengthOfVector(x: Float, y: Float): Float {
        return sqrt(x.toDouble().pow(2.0) + y.toDouble().pow(2.0)).toFloat()
    }

    override fun compare(o1: Any?, o2: Any?): Float {
        val obj1_x = (o1 as Point2DType).getX()
        val obj2_x = (o2 as Point2DType).getX()
        val obj1_y = o1.getY()
        val obj2_y = o2.getY()
        return getLengthOfVector(obj1_x, obj1_y) - getLengthOfVector(obj2_x, obj2_y)
    }

}