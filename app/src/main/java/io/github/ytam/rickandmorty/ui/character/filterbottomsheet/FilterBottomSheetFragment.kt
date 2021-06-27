package io.github.ytam.rickandmorty.ui.character.filterbottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import io.github.ytam.rickandmorty.R
import kotlinx.android.synthetic.main.filter_bottom_sheet.view.*
import java.util.Locale

class FilterBottomSheetFragment : BottomSheetDialogFragment() {

    private var statusChip = "alive"
    private var genderChip = "female"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mView = inflater.inflate(R.layout.filter_bottom_sheet, container, false)

        mView.chipGroupStatus.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            statusChip = chip.text.toString().lowercase(Locale.ROOT)
        }

        mView.chipGroupGender.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            genderChip = chip.text.toString().lowercase(Locale.ROOT)
        }

        mView.apply_btn.setOnClickListener {

            val action =
                FilterBottomSheetFragmentDirections.actionFilterBottomSheetFragmentToCharacterFragment(genderChip, statusChip)
            findNavController().navigate(action)
        }

        return mView
    }
}
