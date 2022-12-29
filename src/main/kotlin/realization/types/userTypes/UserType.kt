package realization.types.userTypes

import realization.types.comparators.Comporator
import java.io.IOException
import java.io.InputStreamReader

interface UserType {
    /**
     * Получить название ТД
     * @return
     */
    fun typeName(): String

    /**
     * Создать объект ТД
     * @return
     */
    fun create(): Any


    /**
     * Создать копию объекта
     * @param object
     * @return
     */
    fun clone(`object`: Any): Any

    /**
     * Чтение экземпляра с потока
     * @param in
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun readValue(`in`: InputStreamReader): Any

    /**
     * Парсить значение со строки
     * @param ss
     * @return
     */
    fun parseValue(ss: String): Any

    /**
     * Сравнение
     * @return
     */
    fun getTypeComparator(): Comporator

    fun toString(`object`: Any): String
}