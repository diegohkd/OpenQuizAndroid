package mobdao.com.openquiz.modules.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mobdao.com.openquiz.R
import mobdao.com.openquiz.modules.base.BaseFragment

class HomeFragment : BaseFragment() {

    //region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    //endregion
}