package io.github.ytam.rickandmorty.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.github.ytam.rickandmorty.R
import io.github.ytam.rickandmorty.databinding.FragmentCharacterDetailBinding
import io.github.ytam.rickandmorty.viewmodel.CharacterDetailViewModel


/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
class CharacterDetailFragment : Fragment() {

    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var fragmentCharacterDetailBinding: FragmentCharacterDetailBinding
    private var characterId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
        }

        viewModel = ViewModelProviders.of(this).get(CharacterDetailViewModel::class.java)
        viewModel.getDataFromAPI(characterId)

        viewModel.characterDetail.observe(viewLifecycleOwner, Observer { characterDetail ->

            characterDetail?.let {

                fragmentCharacterDetailBinding.characterDetail = characterDetail
            }
        })

        viewModel.characterError.observe(viewLifecycleOwner, Observer { error ->

            error?.let {

                if (it) {

                    Toast.makeText(
                        context,
                        "" + getString(R.string.error_has_occurred),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }
}