package com.alican.mvvm_starter.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.data.local.model.SatelliteModel
import com.alican.mvvm_starter.databinding.FragmentDetailBinding
import kotlinx.coroutines.launch

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private val viewModel: DetailViewModel by activityViewModels()
    private var model: SatelliteModel? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_detail
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgument()
        initUi()

    }

    private fun getArgument() {
        arguments?.let {
            model = DetailFragmentArgs.fromBundle(it).data
        }
    }

    private fun initUi() {
        model?.id?.let {
            viewModel.getSatelliteDetail(it).observe(viewLifecycleOwner) { data ->
                hideProgressDialog()
                binding.tvTitle.text = model?.name
                binding.tvCost.text = getString(R.string.cost, data.cost_per_launch)
                binding.tvInfo.text = "Height/Mass: ${data.height}/${data.mass}"
                binding.tvDate.text = data.first_flight

            }
            lifecycleScope.launch {
                viewModel.getPositions(it).collect {
                    binding.tvLocation.text = "X: ${it.posX}\tY: ${it.posY}"
                }
            }
        }

    }


}