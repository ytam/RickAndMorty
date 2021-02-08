package io.github.ytam.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.ytam.rickandmorty.R
import io.github.ytam.rickandmorty.enum.SwitchLayoutEnums
import io.github.ytam.rickandmorty.extensions.loadFromUrl
import io.github.ytam.rickandmorty.model.Character
import io.github.ytam.rickandmorty.view.CharacterFragmentDirections
import kotlinx.android.synthetic.main.item_character.view.*

/**
 *Created by Yıldırım TAM on 04/02/2021.
 */
class CharacterAdapter(
    private val characterList: ArrayList<Character>,
    private val layoutManager: GridLayoutManager
) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        val view: View
        val inflater = LayoutInflater.from(parent.context)
        view = if (viewType == SwitchLayoutEnums.VIEW_TYPE_BIG.value) {
            inflater.inflate(R.layout.item_character, parent, false)
        } else {
            inflater.inflate(R.layout.item_character_grid, parent, false)
        }

        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        holder.view.ivCharacterProfile.loadFromUrl(characterList[position].image.toString())
        holder.view.tvCharacterName.text = characterList[position].name
        holder.view.tvCharacterSpecies.text = characterList[position].species
        holder.view.tvCharacterStatus.text = characterList[position].status

        holder.view.setOnClickListener {
            val characterId = characterList[position].id.toString().toInt()
            val action =
                CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(
                    characterId
                )

            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (layoutManager.spanCount == SwitchLayoutEnums.SPAN_COUNT_ONE.value) {

            SwitchLayoutEnums.VIEW_TYPE_BIG.value
        } else {
            SwitchLayoutEnums.VIEW_TYPE_SMALL.value
        }
    }

    override fun getItemCount(): Int {

        return characterList.size
    }

    fun updateList(newCountryList: List<Character>) {

        if (characterList.isNotEmpty()) {
            this.characterList.clear()
        }
        characterList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}