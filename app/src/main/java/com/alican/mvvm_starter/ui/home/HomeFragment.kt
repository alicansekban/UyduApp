package com.alican.mvvm_starter.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.data.local.model.SatelliteModel
import com.alican.mvvm_starter.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearch()
        initViews()
    }

    private fun initViews() {
        binding.rvList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )

        binding.rvList.adapter = SatelliteAdapter {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    it
                )
            )
        }
        getDataFromDataBase()
    }

    var observed: LiveData<List<SatelliteModel>>? = null

    private fun getDataFromDataBase(query: String = "") {
        observed?.removeObservers(viewLifecycleOwner)
        observed = viewModel.searchWithQuery(query)

        observed?.observe(viewLifecycleOwner) {
            initAdapter(it)
        }
    }

    private fun initSearch() {
        binding.edtSearch.addTextChangedListener {
            if (it?.length!! > 2) {
                getDataFromDataBase(it.toString())
            } else {
                getDataFromDataBase()
            }
        }
    }

    private fun showEmptyUI() {
        binding.rvList.visibility = View.GONE
    }

    private fun initAdapter(satelliteModels: List<SatelliteModel>) {
        hideProgressDialog()
        if (satelliteModels.isEmpty()) {
            showEmptyUI()
        } else {
            showUI()
        }
        (binding.rvList.adapter as? SatelliteAdapter)?.submitList(satelliteModels.map { it.copy() })
    }

    private fun showUI() {
        binding.rvList.visibility = View.VISIBLE
    }

}
