package mobdao.com.openquiz.utils.pokos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultsReport(
    val correctAnswers: Int,
    val wrongAnswers: Int
) : Parcelable