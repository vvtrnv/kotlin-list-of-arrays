package test

import realization.list.MyListOfArrays
import realization.list.SerializeList
import realization.types.factory.FactoryUserType
import realization.types.userTypes.IntegerUserType
import realization.types.userTypes.Point2DUserType
import realization.types.userTypes.UserType
import java.io.FileNotFoundException

class TestListOfArrays {
    private var factoryUserType: FactoryUserType? = null
    private var userType: UserType? = null
    private var list: MyListOfArrays? = null

    private val INTEGER_FILENAME = "testInteger.dat"
    private val POINT2D_FILENAME = "testPoint2D.dat"

    private val serializeList: SerializeList = SerializeList()

    private fun startTest() {
        println("### FILL LIST (ADD METHOD)")
        for (i in 0..120) {
            list!!.add(userType!!.create())
        }
        println("### PRINT LIST")
        list!!.show()
        println("TOTAL ELEMENTS = " + list!!.getTotalElements())

        println("\n### INSERT ELEMENT")
        println("ELEMENT ON 77 INDEX BEFORE INSERT: " + list!![77])
        list!!.insert(userType!!.create(), 77)

        println("ELEMENT ON 77 INDEX AFTER INSERT: " + list!![77])
        println("\n### INSERT 20 ELEMENTS ON INDEX 100")
        for (i in 0..19) {
            list!!.insert(userType!!.create(), 100)
        }
        println("### PRINT LIST")
        list!!.show()
        println("TOTAL ELEMENTS = " + list!!.getTotalElements())
        println("\n### REMOVE ELEMENT ON INDEX")
        println("ELEMENT ON 77 INDEX BEFORE REMOVE: " + list!![77])
        list!!.remove(77)
        println("ELEMENT ON 77 INDEX AFTER REMOVE: " + list!![77])
        println("\n### REMOVE 20 ELEMENTS ON INDEX 50")
        for (i in 0..19) {
            list!!.remove(50)
        }
        println("### PRINT LIST")
        list!!.show()
        println("TOTAL ELEMENTS = " + list!!.getTotalElements())
        if (userType is IntegerUserType) {
            println("### SAVE TO FILE $INTEGER_FILENAME")
            serializeList.saveToFile(list!!, INTEGER_FILENAME, userType!!)
        } else if (userType is Point2DUserType) {
            println("### SAVE TO FILE $POINT2D_FILENAME")
            serializeList.saveToFile(list!!, POINT2D_FILENAME, userType!!)
        }
        println("\n### SORT LIST")
        list = list!!.sort(userType!!.getTypeComparator())
        println("### PRINT LIST")
        list!!.show()
        println("TOTAL ELEMENTS = " + list!!.getTotalElements())
        if (userType is IntegerUserType) {
            try {
                println("\n~~ LOAD LIST FROM FILE $INTEGER_FILENAME ~~")
                list!!.clear()
                list = serializeList.loadFromFile(INTEGER_FILENAME, userType!!)
                println("### PRINT LIST")
                list!!.show()
                println("TOTAL ELEMENTS = " + list!!.getTotalElements())
            } catch (ex: FileNotFoundException) {
                println("Ошибка. Файл не найден!")
            } catch (ex: Exception) {
                println("Ошибка. Структура файла неверна или повреждена!")
            }
        } else if (userType is Point2DUserType) {
            try {
                println("\n~~ LOAD LIST FROM FILE $POINT2D_FILENAME ~~")
                list!!.clear()
                list = serializeList.loadFromFile(POINT2D_FILENAME, userType!!)
                println("### PRINT LIST")
                list!!.show()
                println("TOTAL ELEMENTS = " + list!!.getTotalElements())
            } catch (ex: FileNotFoundException) {
                println("Ошибка. Файл не найден!")
            } catch (ex: Exception) {
                println("Ошибка. Структура файла неверна или повреждена!")
            }
        }
    }

    fun testingInteger(): Int {
        println("#### TEST USER TYPE INTEGER\n")
        factoryUserType = FactoryUserType()
        userType = factoryUserType!!.getBuilderByTypeName("Integer")
        list = MyListOfArrays(100)
        startTest()
        return 1
    }

    fun testingPoint2D(): Int {
        println("#### TEST USER TYPE POINT2D\n")
        factoryUserType = FactoryUserType()
        userType = factoryUserType!!.getBuilderByTypeName("Point2D")
        list = MyListOfArrays(100)
        startTest()
        return 1
    }
}