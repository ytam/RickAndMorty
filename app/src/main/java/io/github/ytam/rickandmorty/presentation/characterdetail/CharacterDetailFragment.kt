package io.github.ytam.rickandmorty.presentation.characterdetail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import io.github.ytam.rickandmorty.R
import io.github.ytam.rickandmorty.databinding.FragmentCharacterDetailBinding
import io.github.ytam.rickandmorty.domain.model.Character

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {
    private lateinit var binding: FragmentCharacterDetailBinding
    private val args: CharacterDetailFragmentArgs by navArgs()
    private lateinit var character: Character

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCharacterDetailBinding.bind(view)
        getArgs()
    }

    private fun getArgs() {
        character = args.character
        bindCharacterDetails()
    }

    private fun bindCharacterDetails() {
        binding.apply {
            character.let {
                tvCharacterName.text = it.name
                tvCharacterSpecies.text = it.species
                tvCharacterGender.text = it.gender
                tvCharacterStatus.text = it.status
                ivCharacterProfile.load(it.image)
                tvCharacterOriginLocation.text = it.origin
                tvCharacterLastLocation.text = it.location
            }
        }
    }
}
