package io.github.ytam.rickandmorty.ui.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.github.ytam.rickandmorty.R
import io.github.ytam.rickandmorty.databinding.FragmentCharacterDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
class CharacterDetailFragment : Fragment() {

    private val viewModel: CharacterDetailViewModel by viewModel()
    private lateinit var fragmentCharacterDetailBinding: FragmentCharacterDetailBinding
    private var characterId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentCharacterDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_character_detail, container, false)
        return fragmentCharacterDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            characterId = CharacterDetailFragmentArgs.fromBundle(it).characterId
            viewModel.getCharacterById(characterId)
        }

        observeCharacter()
    }

    private fun observeCharacter() {
        viewModel.getCharacterLiveData().observe(CharacterDetailFragment@ this) {
            it?.let {
                fragmentCharacterDetailBinding.characterDetail = it
            }
        }

        viewModel.getErrorLiveData().observe(CharacterDetailFragment@ this) { throwable ->
            throwable.message?.let { showErrorMessage(it) }
        }
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(
            context,
            "Error : $message",
            Toast.LENGTH_SHORT
        ).show()
    }
}
