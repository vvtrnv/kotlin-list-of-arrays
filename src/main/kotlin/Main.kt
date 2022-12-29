import programUI.GUI
import test.TestListOfArrays

fun main(args: Array<String>) {
    val testStructure =  TestListOfArrays()
    val interfaceProgram = GUI()

    testStructure.testingInteger()
    testStructure.testingPoint2D()

    interfaceProgram.showWindow()
}