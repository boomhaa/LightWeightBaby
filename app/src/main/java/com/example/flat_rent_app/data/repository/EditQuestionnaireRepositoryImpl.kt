package com.example.flat_rent_app.data.repository

import com.example.flat_rent_app.domain.model.EditQuestionnaire
import com.example.flat_rent_app.domain.model.ProfilePhoto
import com.example.flat_rent_app.domain.repository.EditQuestionnaireRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditQuestionnaireRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : EditQuestionnaireRepository {

    private companion object {
        const val COLLECTION_NAME = "questionnaires"
    }

    override suspend fun getQuestionnaire(userId: String): EditQuestionnaire? {
        return try {
            val document = db.collection(COLLECTION_NAME)
                .document(userId)
                .get()
                .await()

            if (document.exists()) {
                document.toEditQuestionnaire()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveQuestionnaire(questionnaire: EditQuestionnaire): Result<Unit> {
        return try {
            val now = System.currentTimeMillis()

            val data = hashMapOf<String, Any>(
                "name" to questionnaire.name,
                "city" to questionnaire.city,
                "eduPlace" to questionnaire.eduPlace,
                "description" to questionnaire.description,
                "mainPhotoIndex" to questionnaire.mainPhotoIndex,
                "updatedAtMillis" to now
            )

            val createdAt = questionnaire.createdAtMillis ?: now
            data["createdAtMillis"] = createdAt

            val photoSlotsData = questionnaire.photoSlots.map { slot ->
                slot?.let { photo ->
                    hashMapOf<String, Any?>(
                        "fullUrl" to photo.fullUrl,
                        "thumbUrl" to photo.thumbUrl,
                        "updatedAtMillis" to photo.updatedAt
                    )
                }
            }
            data["photoSlots"] = photoSlotsData

            db.collection(COLLECTION_NAME)
                .document(questionnaire.uid)
                .set(data, SetOptions.merge())
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка сохранения анкеты: ${e.message}"))
        }
    }

    override suspend fun deleteQuestionnaire(userId: String): Result<Unit> {
        return try {
            db.collection(COLLECTION_NAME)
                .document(userId)
                .delete()
                .await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Ошибка удаления анкеты: ${e.message}"))
        }
    }

    override suspend fun hasQuestionnaire(userId: String): Boolean {
        return try {
            val document = db.collection(COLLECTION_NAME)
                .document(userId)
                .get()
                .await()

            document.exists()
        } catch (e: Exception) {
            false
        }
    }

    private fun DocumentSnapshot.toEditQuestionnaire(): EditQuestionnaire {
        val photoSlots = (get("photoSlots") as? List<*>)?.mapIndexed { index, item ->
            val map = item as? Map<*, *> ?: return@mapIndexed null

            ProfilePhoto(
                fullUrl = map["fullUrl"] as? String,
                thumbUrl = map["thumbUrl"] as? String,
                updatedAt = map["updatedAtMillis"] as? Long
            )
        } ?: listOf(null, null, null)

        val ensuredPhotoSlots = if (photoSlots.size < 3) {
            photoSlots + List(3 - photoSlots.size) { null }
        } else {
            photoSlots.take(3)
        }

        return EditQuestionnaire(
            uid = id,
            name = getString("name") ?: "",
            city = getString("city") ?: "",
            eduPlace = getString("eduPlace") ?: "",
            description = getString("description") ?: "",
            mainPhotoIndex = (getLong("mainPhotoIndex") ?: 0L).toInt(),
            photoSlots = ensuredPhotoSlots,
            createdAtMillis = getLong("createdAtMillis"),
            updatedAtMillis = getLong("updatedAtMillis")
        )
    }
}