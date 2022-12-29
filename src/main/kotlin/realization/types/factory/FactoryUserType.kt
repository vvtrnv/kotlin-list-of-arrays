package realization.types.factory

import realization.types.userTypes.IntegerUserType
import realization.types.userTypes.Point2DUserType
import realization.types.userTypes.UserType

class FactoryUserType {

    private var listOfAvailableTypes : List<UserType> = listOf(IntegerUserType(), Point2DUserType())

    /**
     * Получить объект желаемого ТД
     * @param typename название ТД
     * @return
     */
    fun getBuilderByTypeName(typename: String): UserType? {
        for (userType in listOfAvailableTypes) {
            if (typename == userType.typeName()) {
                return userType
            }
        }
        throw RuntimeException("Ошибка. Тип данных не найден!")
    }

    /**
     * Получить список типов данных
     * @return
     */
    fun getTypeNameList(): java.util.ArrayList<String>? {
        val typeNameListString = java.util.ArrayList<String>()
        for (userType in listOfAvailableTypes) {
            typeNameListString.add(userType.typeName())
        }
        return typeNameListString
    }
}