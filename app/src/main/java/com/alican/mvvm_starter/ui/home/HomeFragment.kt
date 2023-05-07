package com.alican.mvvm_starter.ui.home

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alican.mvvm_starter.R
import com.alican.mvvm_starter.base.BaseFragment
import com.alican.mvvm_starter.data.local.model.SatelliteModel
import com.alican.mvvm_starter.databinding.FragmentHomeBinding
import com.alican.mvvm_starter.databinding.ItemSatelliteBinding
import com.alican.mvvm_starter.util.utils.loadJSONFromAssets
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import com.github.nitrico.lastadapter.Type
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: HomeViewModel by activityViewModels()

    private var items = arrayListOf<SatelliteModel>()
    private var keyword: String? = null
    private lateinit var homeAdapter: LastAdapter
    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchJsonData()
        initSearch()
        initObserver()

    }

    private fun initObserver() {
        viewModel.finish.observe(viewLifecycleOwner) {
            if (it) {
                hideProgressDialog()
                getDataFromDataBase()
            }
        }

    }

    private fun getDataFromDataBase() {
        viewModel.getAllSatellite().observe(viewLifecycleOwner) {
            if (it != null) {
                items.clear()
                items.addAll(it)
                initAdapter()
            }
        }
    }

    private fun fetchJsonData() {
        showProgressDialog()
        val satelliteJsonArray =
            JSONArray(requireContext().loadJSONFromAssets("satellite_list.json")) // Extension Function call here
        for (i in 0 until satelliteJsonArray.length()) {

            val satelliteJSONObject = satelliteJsonArray.getJSONObject(i)
            val satelliteModel = SatelliteModel(
                id = satelliteJSONObject.getInt("id"),
                active = satelliteJSONObject.getBoolean("active"),
                name = satelliteJSONObject.getString("name")
            )

            items.add(satelliteModel)
        }
        viewModel.insertDataToDataBase(items)

    }

    private fun initSearch() {
        binding.edtSearch.addTextChangedListener {
            keyword = it.toString()
            if (it?.length!! > 2) {
                viewModel.searchWithQuery(it.toString()).observe(viewLifecycleOwner) { model ->
                    if (model != null) {
                        items.clear()
                        items.add(model)
                        initAdapter()
                    } else {
                        showEmptyUI()
                    }
                }
            } else {
                getDataFromDataBase()
            }
        }
    }

    private fun showEmptyUI() {
        binding.rvList.visibility = View.GONE
    }

    private fun initAdapter() {
        binding.rvList.visibility = View.VISIBLE
        binding.rvList.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        homeAdapter = LastAdapter(items, BR.data)
            .map<SatelliteModel>(
                Type<ItemSatelliteBinding>(R.layout.item_satellite).onBind { row ->
                    val data = row.binding.data
                    row.binding.tvTitle.text = data?.name
                    row.binding.tvActive.text = if (data?.active == true) "Active" else "Passive"
                }
                    .onClick { rowClick ->
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                                rowClick.binding.data
                            )
                        )
                        items.clear()
                    }
            ).into(binding.rvList)


    }

}
