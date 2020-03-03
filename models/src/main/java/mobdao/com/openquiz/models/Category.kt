package mobdao.com.openquiz.models

import com.google.gson.annotations.SerializedName

@Suppress("unused")
enum class Category(val id: Int) {
    @SerializedName("General Knowledge")
    GENERAL_KNOWLEDGE(9),

    @SerializedName("Entertainment: Books")
    ENTERTAINMENT_BOOKS(10),

    @SerializedName("Entertainment: Film")
    ENTERTAINMENT_FILM(11),

    @SerializedName("Entertainment: Music")
    ENTERTAINMENT_MUSIC(12),

    @SerializedName("Entertainment: Musicals & Theatres")
    ENTERTAINMENT_MUSICALS_AND_THEATRES(13),

    @SerializedName("Entertainment: Television")
    ENTERTAINMENT_TELEVISION(14),

    @SerializedName("Entertainment: Video Games")
    ENTERTAINMENT_VIDEO_GAMES(15),

    @SerializedName("Entertainment: Board Games")
    ENTERTAINMENT_BOARD_GAMES(16),

    @SerializedName("Science & Nature")
    SCIENCE_AND_NATURE(17),

    @SerializedName("Science: Computers")
    SCIENCE_COMPUTERS(18),

    @SerializedName("Science: Mathematics")
    SCIENCE_MATHEMATICS(19),

    @SerializedName("Mythology")
    MYTHOLOGY(20),

    @SerializedName("Sports")
    SPORTS(21),

    @SerializedName("Geography")
    GEOGRAPHY(9),

    @SerializedName("History")
    HISTORY(23),

    @SerializedName("Politics")
    POLITICS(24),

    @SerializedName("Art")
    ART(25),

    @SerializedName("Celebrities")
    CELEBRITIES(26),

    @SerializedName("Animals")
    ANIMALS(27),

    @SerializedName("Vehicles")
    VEHICLES(28),

    @SerializedName("Entertainment: Comics")
    ENTERTAINMENT_COMICS(29),

    @SerializedName("Science: Gadgets")
    SCIENCE_GADGETS(30),

    @SerializedName("Entertainment: Japanese Anime & Manga")
    ENTERTAINMENT_JAPANESE_ANIME_AND_MANGA(31),

    @SerializedName("Entertainment: Cartoon & Animations")
    ENTERTAINMENT_CARTOON_AND_ANIMATIONS(32)
}