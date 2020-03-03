package mobdao.com.openquiz.models

import com.google.gson.annotations.SerializedName

@Suppress("unused")
enum class QuestionType {
    @SerializedName("multiple")
    MULTIPLE_CHOICE,

    @SerializedName("boolean")
    TRUE_FALSE
}