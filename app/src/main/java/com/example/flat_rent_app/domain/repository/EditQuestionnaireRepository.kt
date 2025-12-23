package com.example.flat_rent_app.domain.repository

import com.example.flat_rent_app.domain.model.EditQuestionnaire

interface EditQuestionnaireRepository {
    suspend fun getQuestionnaire(userId : String): EditQuestionnaire?

    suspend fun saveQuestionnaire(questionnaire: EditQuestionnaire): Result<Unit>

    suspend fun deleteQuestionnaire(userId: String): Result<Unit>

    suspend fun hasQuestionnaire(userId: String): Boolean
}