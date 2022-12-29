package realization.types.comparators

import realization.types.IntegerType
import java.io.Serializable

class IntegerComporator : Comporator, Serializable{
    override fun compare(o1: Any?, o2: Any?): Float {
        return ((o1 as IntegerType).getIntValue() - (o2 as IntegerType).getIntValue()).toFloat()
    }
}