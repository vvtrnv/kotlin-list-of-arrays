package realization.types.userTypes

import realization.types.IntegerType
import realization.types.comparators.Comporator
import realization.types.comparators.IntegerComporator
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class IntegerUserType : UserType{
    override fun typeName(): String {
        return "Integer"
    }

    override fun create(): Any {
        val rand = Random()
        return IntegerType(rand.nextInt(1000))
    }

    override fun clone(`object`: Any): Any {
        return IntegerType((`object` as IntegerType).getIntValue())
    }

    @Throws(IOException::class)
    override fun readValue(`in`: InputStreamReader): Any {
        return `in`.read()
    }

    override fun parseValue(ss: String): Any {
        return IntegerType(ss.toInt())
    }

    override fun getTypeComparator(): Comporator {
        return IntegerComporator()
    }

    override fun toString(`object`: Any): String {
        return `object`.toString()
    }
}