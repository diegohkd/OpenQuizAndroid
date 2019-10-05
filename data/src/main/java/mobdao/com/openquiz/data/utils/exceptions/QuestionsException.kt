package mobdao.com.openquiz.data.utils.exceptions

import mobdao.com.openquiz.data.utils.enums.QuestionsResponseCode

class QuestionsException(
    message: String,
    private val errorCode: QuestionsResponseCode?
) : Exception(message)