package programUI

import realization.list.MyListOfArrays
import realization.list.SerializeList
import realization.types.factory.FactoryUserType
import realization.types.userTypes.UserType
import java.awt.Dimension
import java.awt.event.ActionEvent
import java.io.FileNotFoundException
import java.util.*
import javax.swing.*

class GUI : JPanel() {
    private var addButton: JButton
    private var insertByIdButton: JButton
    private var removeByIdButton: JButton
    private var mainField: JTextArea
    private var typeComboBox: JComboBox<*>
    private var insertByIdField: JTextField
    private var removeByIdField: JTextField
    private var sortListBySizeButton: JButton
    private var getByIdButton: JButton
    private var getByIdField: JTextField
    private var saveButton: JButton
    private var loadButton: JButton
    private var totalElementsLabel: JLabel

    var currentType: String? = null
    var totalElements = "TOTAL ELEMENTS = 0"
    var userType: UserType? = null
    var list: MyListOfArrays? = null
    var factoryUserType: FactoryUserType? = null
    private val serializeList: SerializeList = SerializeList()

    init {
        currentType = "Integer"
        factoryUserType = FactoryUserType()
        userType = factoryUserType!!.getBuilderByTypeName(currentType!!)
        list = MyListOfArrays(64)

        // list of types
        val typeNameList = factoryUserType!!.getTypeNameList()
        val factoryListItems = arrayOfNulls<String>(typeNameList!!.size)
        for (i in typeNameList.indices) {
            factoryListItems[i] = typeNameList[i]
        }

        //construct components
        addButton = JButton("Вставить")
        insertByIdButton = JButton("Вставить по id")
        removeByIdButton = JButton("Удалить по id")
        mainField = JTextArea(5, 5)
        typeComboBox = JComboBox<Any?>(factoryListItems)
        insertByIdField = JTextField(5)
        removeByIdField = JTextField(5)
        sortListBySizeButton = JButton("Сортировать")
        getByIdButton = JButton("Получить по id")
        getByIdField = JTextField(5)
        totalElementsLabel = JLabel(totalElements)
        saveButton = JButton("Сохранить")
        loadButton = JButton("Загрузить")

        //adjust size and set layout
        preferredSize = Dimension(1000, 700)
        layout = null

        //add components
        add(addButton)
        add(insertByIdButton)
        add(removeByIdButton)
        add(mainField)
        add(typeComboBox)
        add(insertByIdField)
        add(removeByIdField)
        add(sortListBySizeButton)
        add(getByIdButton)
        add(getByIdField)
        add(totalElementsLabel)
        add(saveButton)
        add(loadButton)

        //set component bounds
        typeComboBox.setBounds(650, 15, 180, 30)

        // Work with nodes
        addButton.setBounds(800, 70, 140, 30)
        insertByIdButton.setBounds(800, 110, 140, 30)
        insertByIdField.setBounds(650, 110, 140, 30)
        removeByIdButton.setBounds(800, 155, 140, 30)
        removeByIdField.setBounds(650, 155, 140, 30)
        getByIdButton.setBounds(800, 200, 140, 30)
        getByIdField.setBounds(650, 200, 140, 30)
        sortListBySizeButton.setBounds(650, 250, 290, 30)
        totalElementsLabel.setBounds(650, 350, 290, 30)

        // Serialization
        saveButton.setBounds(650, 610, 145, 70)
        loadButton.setBounds(850, 610, 140, 70)

        // Main field
        mainField.setBounds(25, 10, 600, 680)
        mainField.isEditable = false
        mainField.lineWrap = true
        mainField.wrapStyleWord = true

        // set action
        setActions()
    }

    fun setActions() {
        addButton.addActionListener { e: ActionEvent? -> pushNode() }
        insertByIdButton.addActionListener { e: ActionEvent? -> pushNodeByID() }
        typeComboBox.addActionListener { e: ActionEvent -> selectTypeList(e) }
        removeByIdButton.addActionListener { e: ActionEvent? -> removeNodeByID() }
        getByIdButton.addActionListener { e: ActionEvent? -> getNodeByID() }
        sortListBySizeButton.addActionListener { e: ActionEvent? -> sortListBySizeArray() }
        saveButton.addActionListener { e: ActionEvent? -> saveList() }
        loadButton.addActionListener { e: ActionEvent? -> loadList() }
    }

    fun showWindow() {
        val frame = JFrame("Lab3 Kotlin")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.contentPane.add(GUI())
        frame.pack()
        frame.isVisible = true
        frame.isResizable = false
    }

    private fun selectTypeList(actionEvent: ActionEvent?) {
        val comboBox = actionEvent?.source as JComboBox<*>
        val item = comboBox.selectedItem as String
        if (currentType != item) {
            currentType = item
            userType = factoryUserType!!.getBuilderByTypeName(currentType!!)
            list = MyListOfArrays(64)
            setTextOnMainField()
        }
    }

    private fun pushNode() {
        list!!.add(userType!!.create())
        updateTotalElementsLabel()
        setTextOnMainField()
    }

    private fun pushNodeByID() {
        val nodeID = insertByIdField.text
        if (nodeID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ошибка. Поле \"id\" пусто!")
            return
        }
        list!!.insert(userType!!.create(), nodeID.toInt())
        updateTotalElementsLabel()
        setTextOnMainField()
    }

    private fun removeNodeByID() {
        val nodeID = removeByIdField.text
        if (nodeID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ошибка. Поле \"id\" пусто!")
            return
        }
        list!!.remove(nodeID.toInt())
        updateTotalElementsLabel()
        setTextOnMainField()
    }

    private fun sortListBySizeArray() {
        list = list!!.sort(userType!!.getTypeComparator())
        updateTotalElementsLabel()
        setTextOnMainField()
    }

    private fun getNodeByID() {
        val nodeID = getByIdField.text
        if (nodeID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ошибка. Поле \"id\" пусто!")
            return
        }
        JOptionPane.showMessageDialog(
            null, """
     Найденый узел:
     ${list!![nodeID.toInt()]}
     """.trimIndent()
        )
    }


    private fun saveList() {
        val filename = userType!!.typeName() + ".dat"
        serializeList.saveToFile(list!!, filename, userType!!)
        JOptionPane.showMessageDialog(null, "Список успешно сохранён в \"$filename\"")
    }

    private fun loadList() {
        try {
            val filename = userType!!.typeName() + ".dat"
            list = serializeList.loadFromFile(filename, userType!!)
            JOptionPane.showMessageDialog(null, "Список успешно загружен!")
            updateTotalElementsLabel()
            setTextOnMainField()
        } catch (ex: FileNotFoundException) {
            JOptionPane.showMessageDialog(null, "Ошибка. Файл не найден!")
        } catch (ex: Exception) {
            JOptionPane.showMessageDialog(null, "Ошибка. Структура файла неверна или повреждена!")
        }
    }

    fun updateTotalElementsLabel() {
        totalElements = "TOTAL ELEMENTS = " + list!!.getTotalElements()
        totalElementsLabel.text = totalElements
    }

    private fun setTextOnMainField() {
        mainField.text = list.toString()
    }
}