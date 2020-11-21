package mobdao.com.openquiz.modules.quiz.resultsreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import mobdao.com.openquiz.databinding.FragmentResultsReportBinding
import mobdao.com.openquiz.modules.base.BaseFragment

class ResultsReportFragment : BaseFragment() {

    private lateinit var binding: FragmentResultsReportBinding
    private val args: ResultsReportFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentResultsReportBinding.inflate(layoutInflater).apply {
        binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    fun onOkClicked() {
        val action = ResultsReportFragmentDirections.toHomeFragment()
        findNavController().navigate(action)
    }

    //region private

    private fun setupView() {
        binding.fragment = this
        binding.resultsReport = args.resultsReport
    }

    //endregion
}
