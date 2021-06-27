package io.github.ytam.rickandmorty.ui.character

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.github.ytam.rickandmorty.R
import io.github.ytam.rickandmorty.model.Character
import kotlinx.android.synthetic.main.fragment_character.*
import kotlinx.android.synthetic.main.fragment_character.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
class CharacterFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val args by navArgs<CharacterFragmentArgs>()

    private val characterAdapter = CharacterAdapter()
    private val viewModel: CharacterViewModel by viewModel()
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        mView = inflater.inflate(R.layout.fragment_character, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        initRecyclerView()
        observe()
        viewModel.getCharacters(1, "", args.bottomSheetStatus, args.bottomSheetGender)

        fabFilter.setOnClickListener {
            val action = CharacterFragmentDirections.actionCharacterFragmentToFilterBottomSheetFragment()

            Navigation.findNavController(it).navigate(action)
        }

        swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun initRecyclerView() {

        characterRecyclerView.layoutManager = GridLayoutManager(context, 1)
        characterRecyclerView.adapter = characterAdapter

        characterRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (!recyclerView.canScrollVertically(1)) {
                        viewModel.searchNextPage()
                    }
                }
            }
        )
    }

    private fun observe() {
        showShimmerEffect()
        viewModel.getCharactersLiveData().observe(CharacterFragment@ this) {
            showCharacters(it)
        }
        viewModel.getErrorLiveData().observe(CharacterFragment@ this) { throwable ->
            throwable.message?.let { showErrorMessage(it) }
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(
            activity,
            "Error : $message",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showCharacters(characters: List<Character>) {

        characterAdapter.updateList(characters)

        hideShimmerEffect()
    }

    private fun showShimmerEffect() {

        mView.characterRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {

        mView.characterRecyclerView.hideShimmer()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true

        searchView?.queryHint = "search..."

        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener  {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getCharacters(1, query, "", "")
                searchView.clearFocus()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onRefresh() {

        viewModel.getCharacters(1, "", "", "")

        swipeRefreshLayout.isRefreshing = false

    }
}
