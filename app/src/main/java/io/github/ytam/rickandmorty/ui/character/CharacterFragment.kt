package io.github.ytam.rickandmorty.ui.character

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.ytam.rickandmorty.R
import io.github.ytam.rickandmorty.model.Character
import kotlinx.android.synthetic.main.fragment_character.*
import kotlinx.android.synthetic.main.fragment_character.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
class CharacterFragment : Fragment() {

    private val characterAdapter = CharacterAdapter()
    private val viewModel: CharacterViewModel by viewModel()
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        mView =  inflater.inflate(R.layout.fragment_character, container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initSearchView()
        observe()
        viewModel.getCharacters(1, "")
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

    private fun initSearchView() {

        characterSearchView.setBackgroundColor(Color.WHITE)
        characterSearchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.getCharacters(1, query)
                    characterSearchView.clearFocus()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
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

    private fun showShimmerEffect(){

        mView.characterRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect(){

        mView.characterRecyclerView.hideShimmer()
    }
}
