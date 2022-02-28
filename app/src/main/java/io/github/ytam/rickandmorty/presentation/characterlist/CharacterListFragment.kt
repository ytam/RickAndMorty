package io.github.ytam.rickandmorty.presentation.characterlist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.ytam.rickandmorty.R
import io.github.ytam.rickandmorty.databinding.FragmentCharacterBinding
import io.github.ytam.rickandmorty.domain.model.Character
import io.github.ytam.rickandmorty.extensions.safeNavigate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment :
    Fragment(R.layout.fragment_character),
    CharacterListAdapter.CharacterClickListener {
    private lateinit var characterListBinding: FragmentCharacterBinding
    private val characterListViewModel: CharacterListViewModel by viewModels()
    private val characterListAdapter = CharacterListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterListBinding = FragmentCharacterBinding.bind(view)

        setupCharacterRecyclerView()
        collectFromViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true

        searchView?.queryHint = "search..."
        searchView?.setQuery(characterListViewModel.searchQuery.value, true)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                performSearchEvent(query)
                return false
            }

        })
    }


    private fun performSearchEvent(characterName: String) {
        characterListViewModel.onEvent(CharacterListEvent.GetAllCharactersByName(characterName))
    }

    private fun setupCharacterRecyclerView() {
        characterListAdapter.characterClickListener = this@CharacterListFragment
        characterListBinding.apply {
            characterRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = characterListAdapter
            }
        }
    }

    private fun collectFromViewModel() {
        lifecycleScope.launch {
            characterListViewModel.charactersFlow
                .collectLatest {
                    characterListAdapter.submitData(it)
                }
        }
    }

    override fun onCharacterClicked(character: Character?) {
        character?.let {
            findNavController().safeNavigate(
                CharacterListFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(
                    it
                )
            )
        }
    }
}
