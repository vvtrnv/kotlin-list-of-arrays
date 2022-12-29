package realization.list

import realization.types.userTypes.UserType
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.PrintWriter
import java.util.*

class SerializeList {
    private fun parseString(list: MyListOfArrays, s: String, userType: UserType) {
        var s = s
        s = s.replace("[", "")
        s = s.replace("]", "")
        val arrayFromString = s.split(", ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in arrayFromString.indices) {
            if (arrayFromString[i] != "null") {
                list.add(userType.parseValue(arrayFromString[i]))
            }
        }
    }

    fun saveToFile(list: MyListOfArrays, filename: String?, userType: UserType) {
        try {
            PrintWriter(filename).use { writer ->
                writer.println(userType.typeName())
                writer.println(list.getSizeOfArrays())
                list.forEach(object : Action {
                    override fun toDo(data: Any?) {
                        val el = data
                        writer.println(Arrays.toString(el as Array<*>))
                    }
                })
            }
        } catch (e: FileNotFoundException) {
            throw RuntimeException(e)
        }
    }

    @Throws(Exception::class)
    fun loadFromFile(filename: String?, userType: UserType): MyListOfArrays {
        try {
            BufferedReader(FileReader(filename)).use { bufferedReader ->
                var line: String?
                var sizeOfArrays = 0
                line = bufferedReader.readLine()
                if (userType.typeName() != line) {
                    throw Exception("Wrong file structure")
                }
                line = bufferedReader.readLine()
                sizeOfArrays = line.toInt()
                val list = MyListOfArrays(Math.pow(sizeOfArrays.toDouble(), 2.0).toInt())
                while (bufferedReader.readLine().also { line = it } != null) {
                    parseString(list, line!!, userType)
                }
                return list
            }
        } catch (e: FileNotFoundException) {
            throw FileNotFoundException()
        }
    }
}