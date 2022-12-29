package realization.types.userTypes

import realization.types.Point2DType
import realization.types.comparators.Comporator
import realization.types.comparators.Point2DComporator
import java.io.IOException
import java.io.InputStreamReader
import java.util.*
import java.util.regex.Pattern

class Point2DUserType : UserType {
    private val REG_EXPR = "\\(([-]?[0-9]+(?:[.,][0-9]+){0,1});([-]?[0-9]+(?:[.,][0-9]+){0,1})\\)"

    override fun typeName(): String {
        return "Point2D"
    }

    override fun create(): Any {
        val rand = Random()
        return Point2DType(
            rand.nextFloat(1000.0f),
            rand.nextFloat(1000.0f)
        )
    }

    override fun clone(`object`: Any): Any {
        return Point2DType(
            (`object` as Point2DType).getX(),
            `object`.getY()
        )
    }

    @Throws(IOException::class)
    override fun readValue(`in`: InputStreamReader): Any {
        return `in`.read()
    }

    override fun parseValue(ss: String): Any {
        val ptrnString = Pattern.compile(REG_EXPR)
        val matcher = ptrnString.matcher(ss)
        return if (matcher.find()) {
            Point2DType(
                java.lang.Float.valueOf(matcher.group(1)),
                java.lang.Float.valueOf(matcher.group(2))
            )
        } else {
            throw NullPointerException()
        }
    }

    override fun getTypeComparator(): Comporator {
        return Point2DComporator()
    }

    override fun toString(`object`: Any): String {
        return `object`.toString()
    }
}