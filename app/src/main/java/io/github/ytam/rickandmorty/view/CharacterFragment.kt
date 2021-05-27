package io.github.ytam.rickandmorty.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.ytam.rickandmorty.R
import io.github.ytam.rickandmorty.adapter.CharacterAdapter
import io.github.ytam.rickandmorty.enum.SwitchLayoutEnums
import io.github.ytam.rickandmorty.viewmodel.CharacterViewModel
import kotlinx.android.synthetic.main.fragment_character.*

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
class CharacterFragment : Fragment() {

    private lateinit var viewModel: CharacterViewModel
    private var layoutManager: GridLayoutManager? = null
    private var name: String? = ""
    private var isAvailableToSearch: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CharacterViewModel::class.java)
        viewModel.getDataFromAPI(name)

        layoutManager = GridLayoutManager(context, SwitchLayoutEnums.SPAN_COUNT_TWO.value)
        characterRecyclerView.layoutManager = layoutManager
        val characterAdapter = CharacterAdapter(arrayListOf(), layoutManager!!)
        characterRecyclerView.adapter = characterAdapter

        characterRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (!recyclerView.canScrollVertically(1)) {

                        if (isAvailableToSearch) {

                            viewModel.searchNextPage(name)
                        }
                    }
                }
            }
        )

        characterSearchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {

                    name = query
                    viewModel.getDataFromAPI(query)
                    characterSearchView.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            }
        )

        viewModel.characters.observe(
            viewLifecycleOwner,
            Observer { characters ->

                characters?.let {

                    characterRecyclerView.visibility = View.VISIBLE
                    characterError.visibility = View.GONE

                    characterAdapter.updateList(it)
                }
            }
        )

        viewModel.characterError.observe(
            viewLifecycleOwner,
            Observer { error ->

                error?.let {

                    if (it) {

                        Toast.makeText(
                            context,
                            "" + getString(R.string.error_has_occurred),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        )

        viewModel.characterLoading.observe(
            viewLifecycleOwner,
            Observer { loading ->

                loading?.let {

                    if (it) {
                        characterLoadingProgressBar.visibility = View.VISIBLE
                    } else {
                        characterLoadingProgressBar.visibility = View.GONE
                    }
                }
            }
        )

        viewModel.isNextPageAvailable.observe(
            viewLifecycleOwner,
            Observer {

                it?.let {

                    isAvailableToSearch = it
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.change_layout -> {

                if (layoutManager?.spanCount == SwitchLayoutEnums.SPAN_COUNT_ONE.value) {
                    layoutManager?.spanCount = SwitchLayoutEnums.SPAN_COUNT_TWO.value
                    item.icon = resources.getDrawable(R.drawable.ic_baseline_view_list_24)
                } else {
                    layoutManager?.spanCount = SwitchLayoutEnums.SPAN_COUNT_ONE.value
                    item.icon = resources.getDrawable(R.drawable.ic_baseline_view_grid_24)
                }
                characterRecyclerView.adapter?.notifyItemRangeChanged(
                    0,
                    characterRecyclerView.adapter?.itemCount ?: 0
                )
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
