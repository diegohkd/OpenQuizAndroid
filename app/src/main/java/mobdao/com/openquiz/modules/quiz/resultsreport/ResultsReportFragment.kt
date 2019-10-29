package mobdao.com.openquiz.modules.quiz.resultsreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_results_report.*
import mobdao.com.openquiz.R
import mobdao.com.openquiz.modules.base.BaseFragment

class ResultsReportFragment : BaseFragment() {

    private val args: ResultsReportFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_results_report, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    //region private

    private fun setupView() {
        with(args.resultsReport) {
            correctValueTextView.text = correctAnswers.toString()
            wrongValueTextView.text = wrongAnswers.toString()
        }

        okButton.setOnClickListener {
            val action = ResultsReportFragmentDirections.actionResultsReportFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    //endregion
}
