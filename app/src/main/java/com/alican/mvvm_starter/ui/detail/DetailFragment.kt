package com.alican.mvvm_starter.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.data.local.model.SatelliteDetailEntity
import com.alican.mvvm_starter.data.local.model.SatelliteModel
import com.alican.mvvm_starter.data.model.SatelliteDetailModel
import com.alican.mvvm_starter.data.model.SatelliteListItem
import com.alican.mvvm_starter.databinding.FragmentDetailBinding
import com.alican.mvvm_starter.ui.home.HomeViewModel
import com.alican.mvvm_starter.util.utils.loadJSONFromAssets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class DetailFragment:BaseFragment<FragmentDetailBinding>() {
    private val viewModel : HomeViewModel by activityViewModels()
    private var items = arrayListOf<SatelliteDetailEntity>()
    private var model : SatelliteModel? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_detail
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgument()
        fetchData()
    }

    private fun getArgument() {
        arguments?.let {
            model = DetailFragmentArgs.fromBundle(it).data
        }
    }

    private fun fetchData() {
        showProgressDialog()
        val satelliteDetailJsonArray =
            JSONArray(requireContext().loadJSONFromAssets("satellite_detail.json")) // Extension Function call here
        for (i in 0 until satelliteDetailJsonArray.length()) {
            val satelliteModel = SatelliteDetailEntity()
            val satelliteJSONObject = satelliteDetailJsonArray.getJSONObject(i)
            satelliteModel.id = satelliteJSONObject.getInt("id")
            satelliteModel.first_flight = satelliteJSONObject.getString("first_flight")
            satelliteModel.cost_per_launch = satelliteJSONObject.getInt("cost_per_launch")
            satelliteModel.height = satelliteJSONObject.getInt("height")
            satelliteModel.mass = satelliteJSONObject.getInt("mass")
            items.add(satelliteModel)
      //      if (satelliteModel.id == model?.id){
        //        initUi(satelliteModel) }

        }
        lifecycleScope.launch(Dispatchers.IO){
            viewModel.insertSatelliteDetailToDatabase(items)
        }
        initUi()

    }

    private fun initUi() {
        model?.id?.let {
            viewModel.getSatelliteDetail(it).observe(viewLifecycleOwner){ data ->
                hideProgressDialog()
                binding.tvTitle.text = model?.name
                binding.tvCost.text = "Cost: ${data.cost_per_launch}"
                binding.tvInfo.text = "Height/Mass: ${data.height}/${data.mass}"
                binding.tvDate.text = data.first_flight

            }
        }

    }


}