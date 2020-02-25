package mobdao.com.openquiz.models

import com.google.gson.annotations.SerializedName

enum class Difficulty {
    @SerializedName("easy")
    EASY,

    @SerializedName("medium")
    NORMAL,

    @SerializedName("hard")
    HARD
}