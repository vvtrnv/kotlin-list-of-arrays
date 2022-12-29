package realization.list

import realization.types.comparators.Comporator
import java.util.*
import kotlin.math.sqrt

class MyListOfArrays {
    private var sizeOfArrays = 0 // размерность каждого массива

    private var size = 0 // Размер списка

    private var totalElements = 0 // Количество элементов во всех массивах


    private var head: Node? = null
    private var tail: Node? = null

    constructor(sizeOfArrays: Int) {
        this.sizeOfArrays = sqrt(sizeOfArrays.toDouble()).toInt()
        size = 1
        tail = Node(this.sizeOfArrays)
        head = tail
        totalElements = 0
    }


    /**
     * Добавить элемент в конец всей структуры
     * @param value
     */
    fun add(value: Any?) {
        val current = tail
        current!!.addElementOnArray(value)
        if (current.countOfElementsInArray == sizeOfArrays) {
            val kk1 = sizeOfArrays * 3 / 4 // 75% в старом
            val kk2 = sizeOfArrays - kk1 // 25% в новом

            // Новое количество элементов в старом массиве
            current.countOfElementsInArray = kk1

            // Создаём новый узел и ставим ему количество элементов в массиве
            val newNode = Node(sizeOfArrays)
            current.next = newNode
            newNode.prev = current
            tail = newNode
            newNode.countOfElementsInArray = kk2

            // Копируем 25% данных в новый узел из предыдущего узла
            for (i in 0 until kk2) {
                newNode.array[i] = current.array[kk1 + i]
                current.array[kk1 + i] = null
            }

            // Увеличиваем счётчик узлов списка
            size++
        }
        totalElements++
    }

    operator fun get(logicalIndexOfElement: Int): Any? {
        try {
            val physicalIndex = getIndex(logicalIndexOfElement)
            if (physicalIndex[0] == -1 || physicalIndex[1] == -1) {
                throw IndexOutOfBoundsException("Error. Index out of bounds")
            }
            val current = getNode(physicalIndex[0])
            return current!!.array[physicalIndex[1]]
        } catch (error: IndexOutOfBoundsException) {
            println(error.message)
        }
        return null
    }

    /**
     * Преобразование логического номера в физический
     * @param logicalIndexOfElement
     * @return Возвращает массив из двух элементов:
     * 1 - индекс узла
     * 2 - физический индекс элемента
     */
    private fun getIndex(logicalIndexOfElement: Int): IntArray {
        // Обработка неверного значения
        if (logicalIndexOfElement < 0 || logicalIndexOfElement >= totalElements) {
            return intArrayOf(-1, -1)
        }

        // Преобразование в физический индекс
        var tmp = head
        var indexNode: Int
        var physicalIndexOfElement = logicalIndexOfElement
        indexNode = 0
        while (tmp != null) {
            if (physicalIndexOfElement < tmp.countOfElementsInArray) {
                return intArrayOf(indexNode, physicalIndexOfElement)
            }
            physicalIndexOfElement -= tmp.countOfElementsInArray
            tmp = tmp.next
            indexNode++
        }
        return intArrayOf(-1, -1)
    }

    /**
     * Вспомогательный метод поиска узла
     * @param index
     * @return
     */
    private fun getNode(index: Int): Node? {
        try {
            if (index < 0 || index >= size) {
                throw IndexOutOfBoundsException()
            }
            if (index == 0) {
                return head
            }
            var tmp = head
            for (i in 0 until index) {
                tmp = tmp!!.next
            }
            return tmp
        } catch (ex: IndexOutOfBoundsException) {
            println("Error. Out of bounds list")
        }
        return null
    }

    /**
     * Вставка по логическому индексу в структуру
     * @param value
     * @param logicalIndexOfElement
     * @return
     */
    fun insert(value: Any?, logicalIndexOfElement: Int): Int {
        try {
            val physicalIndex = getIndex(logicalIndexOfElement)
            if (physicalIndex[0] == -1 || physicalIndex[1] == -1) {
                throw IndexOutOfBoundsException("Error. Index out of bounds")
            }
            val current = getNode(physicalIndex[0])

            // Раздвижка в массиве
            for (i in current!!.countOfElementsInArray - 1 downTo physicalIndex[1]) {
                current.array[i + 1] = current.array[i]
            }

            // Вставка нового элемента
            current.array[physicalIndex[1]] = value
            current.countOfElementsInArray = current.countOfElementsInArray + 1
            totalElements++

            // В случае переполнения -> раздвижка списка
            if (current.countOfElementsInArray == sizeOfArrays) {
                val newNode = Node(sizeOfArrays)

                // Перекидываем указатели
                newNode.prev = current
                newNode.next = current.next
                if (current.next == null) {
                    // Если current это последний узел списка
                    tail = newNode
                } else {
                    // Если за current ещё есть узел
                    current.next!!.prev = newNode
                }
                current.next = newNode

                // Перенос половины current в новый узел
                current.countOfElementsInArray = sizeOfArrays / 2
                newNode.countOfElementsInArray = sizeOfArrays - current.countOfElementsInArray
                val countElementsOfNewNode = newNode.countOfElementsInArray
                for (i in 0 until countElementsOfNewNode) {
                    newNode.array[i] = current.array[sizeOfArrays / 2 + i]
                    current.array[sizeOfArrays / 2 + i] = null
                }
                size++
            }
            return 1
        } catch (error: IndexOutOfBoundsException) {
            println(error.message)
        }
        return 0
    }

    fun remove(logicalIndexOfElement: Int): Int {
        try {
            val physicalIndex = getIndex(logicalIndexOfElement)
            if (physicalIndex[0] == -1 || physicalIndex[1] == -1) {
                throw IndexOutOfBoundsException("Error. Index out of bounds")
            }
            val current = getNode(physicalIndex[0])

            // Сдвиг в массиве
            current!!.array[physicalIndex[1]] = null
            for (i in physicalIndex[1] until current.countOfElementsInArray) {
                current.array[i] = current.array[i + 1]
            }
            totalElements--
            current.countOfElementsInArray = current.countOfElementsInArray - 1

            // Если массив оказался пустым, то удалим узел перекинув указатели
            if (current.countOfElementsInArray == 0) {
                if (current.prev != null) {
                    if (current.next != null) {
                        current.prev!!.next = current.next
                    } else {
                        current.prev!!.next = null
                        tail = current.prev
                    }
                }
                if (current.next != null) {
                    if (current.prev != null) {
                        current.next!!.prev = current.prev
                    } else {
                        current.next!!.prev = null
                        head = current.next
                    }
                }
                if (size != 1) {
                    size--
                }
            }
            return 1
        } catch (error: IndexOutOfBoundsException) {
            println(error.message)
        }
        return 0
    }


    fun forEach(a: Action) {
        var tmp = head
        while (tmp != null) {
            a.toDo(tmp.array)
            tmp = tmp.next
        }
    }

    /**
     * Разбиение массива на части
     * @param arr
     * @param comporator
     */
    private fun mergeSortArray(arr: Array<Any?>, comporator: Comporator, countElem: Int) {
        if (countElem == 1) {
            return
        }
        val middle = countElem / 2
        val left = arrayOfNulls<Any>(middle)
        val right = arrayOfNulls<Any>(countElem - middle)
        for (i in 0 until middle) {
            left[i] = arr[i]
        }
        for (i in 0 until countElem - middle) {
            right[i] = arr[middle + i]
        }
        mergeSortArray(left, comporator, left.size)
        mergeSortArray(right, comporator, right.size)
        mergeArrays(arr, left, right, comporator)
    }

    /**
     * Слияние подмассивов
     * @param arr результирующий массив
     * @param leftArr первый подмассив
     * @param rightArr второй подмассив
     * @param comporator объект для сравнения значений пользовательских типов данных
     */
    private fun mergeArrays(arr: Array<Any?>, leftArr: Array<Any?>, rightArr: Array<Any?>, comporator: Comporator) {
        val leftSize = leftArr.size
        val rightSize = rightArr.size
        var i = 0
        var j = 0
        var idx = 0
        while (i < leftSize && j < rightSize) {
            if (comporator.compare(leftArr[i], rightArr[j]) < 0) {
                arr[idx] = leftArr[i]
                i++
            } else {
                arr[idx] = rightArr[j]
                j++
            }
            idx++
        }

        // Если размеры массивов были разными,
        // то добавляем в результирующий массив остатки
        for (ll in i until leftSize) {
            arr[idx++] = leftArr[ll]
        }
        for (rr in j until rightSize) {
            arr[idx++] = rightArr[rr]
        }
    }

    fun sort(comparator: Comporator): MyListOfArrays {
        val arrayOfAllElements = arraysInOneArray()
        mergeSortArray(arrayOfAllElements, comparator, arrayOfAllElements.size)

        // Создаём новый список
        val newList = MyListOfArrays(Math.pow(sizeOfArrays.toDouble(), 2.0).toInt())
        for (i in arrayOfAllElements.indices) {
            newList.add(arrayOfAllElements[i])
        }
        return newList
    }

    private fun arraysInOneArray(): Array<Any?> {
        val arrayOfAllElements = arrayOfNulls<Any>(totalElements)
        for (i in 0 until totalElements) {
            arrayOfAllElements[i] = this[i]
        }
        return arrayOfAllElements
    }

    fun clear() {
        this.size = 0
        this.totalElements = 0
        this.tail = null
        this.head = this.tail
    }

    fun show() {
        var tmp = this.head
        var numOfCurrentNode = 0
        while (tmp != null) {
            println(numOfCurrentNode.toString() + ": " + Arrays.toString(tmp.array))
            numOfCurrentNode++
            tmp = tmp.next
        }
    }

    fun getSize(): Int {
        return size
    }

    fun getTotalElements(): Int {
        return totalElements
    }

    fun getSizeOfArrays(): Int {
        return sizeOfArrays
    }

    override fun toString(): String {
        var str = ""
        var elements = ""
        var indexNode = 0
        var tmp = head
        while (tmp != null) {
            for (j in 0 until tmp.countOfElementsInArray) {
                if (tmp.array[j] != null) {
                    elements += tmp.array[j].toString() + " "
                }
            }
            str += "$indexNode: [$elements]\n"
            tmp = tmp.next
            indexNode++
            elements = ""
        }
        return str
    }

    private class Node(size: Int) {
        var array: Array<Any?>
        var countOfElementsInArray = 0
        var next: Node? = null
        var prev: Node? = null

        init {
            array = arrayOfNulls(size)
            next = prev
        }

        fun addElementOnArray(value: Any?) {
            array[this.countOfElementsInArray] = value
            countOfElementsInArray++
        }

        override fun toString(): String {
            return Arrays.toString(array)
        }
    }
}