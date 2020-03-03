package mobdao.com.openquiz.data.utils.enums

@Suppress("unused")
enum class QuestionsResponseCode(val code: Int) {
    SUCCESS(0),
    NO_RESULTS(1),
    INVALID_PARAMETER(2),
    TOKEN_NOT_FOUND(3),
    TOKEN_EMPTY(4),
    UNKNOWN(5);

    companion object {
        private val map = values().associateBy { it.code }
        fun from(id: Int?) = map[id] ?: UNKNOWN
    }
}