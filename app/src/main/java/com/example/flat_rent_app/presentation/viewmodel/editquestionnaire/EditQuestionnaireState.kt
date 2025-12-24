package com.example.flat_rent_app.presentation.viewmodel.editquestionnaire

data class EditQuestionnaireState(
    val name: String = "",
    val city: String = "",
    val eduPlace: String = "",
    val description: String = "",

    val selectedHabits: Map<String, Boolean> = mapOf(
        "Курение разрешено" to false,
        "Пью алкоголь" to false,
        "Сова" to false,
        "Жаворонок" to false,
        "Есть животные" to false,
        "Приглашаю гостей" to false,
        "Чистота важна" to false,
        "Тишина важна" to false,
        "Люблю музыку" to false,
        "Занимаюсь спортом" to false,
    ),

    val createdAtMillis: Long? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)