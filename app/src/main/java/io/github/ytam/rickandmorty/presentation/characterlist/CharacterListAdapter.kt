package io.github.ytam.rickandmorty.presentation.characterlist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import io.github.ytam.rickandmorty.databinding.ItemCharacterBinding
import io.github.ytam.rickandmorty.domain.model.Character
import io.github.ytam.rickandmorty.enum.CharacterStatusEnums

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
class CharacterListAdapter :
    PagingDataAdapter<Character, CharacterListAdapter.CharacterListViewHolder>(CharacterComparator()) {
    var characterClickListener: CharacterClickListener? = null

    inner class CharacterListViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            characterClickListener
            itemView.setOnClickListener {
                characterClickListener?.onCharacterClicked(
                    getItem(absoluteAdapterPosition)
                )
            }
        }

        fun bindCharacter(character: Character) {
            binding.apply {
                tvCharacterName.text = character.name
                tvCharacterStatus.text = character.status
                ivCharacterProfile.load(character.image)
                tvCharacterGender.text = character.gender

                when (character.status) {
                    CharacterStatusEnums.CHARACTER_ALIVE.value -> imgCharacterStatus.setColorFilter(
                        Color.parseColor("#14D91B")
                    )
                    CharacterStatusEnums.CHARACTER_DEAD.value -> imgCharacterStatus.setColorFilter(
                        Color.parseColor("#FF0800")
                    )
                    CharacterStatusEnums.CHARACTER_UNKNOWN.value -> imgCharacterStatus.setColorFilter(
                        Color.parseColor("#E3E3E3")
                    )
                    else -> {
                        imgCharacterStatus.setColorFilter(Color.parseColor("#F8F816"))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        getItem(position)?.let { holder.bindCharacter(it) }
    }

    class CharacterComparator : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Character, newItem: Character) =
            oldItem == newItem
    }

    interface CharacterClickListener {
        fun onCharacterClicked(character: Character?)
    }
}
