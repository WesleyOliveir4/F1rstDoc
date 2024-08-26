package com.example.f1rstdoc.presentation.docs.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.devk.data.Message.MessageBuilder
import com.example.devk.presentation.state.CreateNotesState
import com.example.f1rstdoc.R
import com.example.f1rstdoc.databinding.FragmentCreateDocsBinding
import com.example.f1rstdoc.presentation.docs.viewmodel.DocsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateDocsFragment : Fragment() {

    lateinit var binding: FragmentCreateDocsBinding
    private val docsViewModel: DocsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateDocsBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        binding.btnEditSaveNotes.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val subTitle = binding.edtSubTitle.text.toString()
            val notes = binding.edtNotes.text.toString()

            docsViewModel.createNotes(it,title,subTitle,notes)
            docsViewModel.stateCreateNotes.observe(viewLifecycleOwner){ stateCreateNotes ->
                when (stateCreateNotes) {
                    is CreateNotesState.Loading -> {

                    }
                    is CreateNotesState.Success -> {
                        Navigation.findNavController((it!!)).navigate(R.id.action_createDocsFragment_to_homeFragment)
                        MessageBuilder(requireContext()).MessageShowTimer(
                            getString(R.string.create_docs_success),
                            1500
                        )
                    }
                    is CreateNotesState.Failure -> {
                        MessageBuilder(requireActivity()).MessageShow(stateCreateNotes.error)
                    }

                }

            }


        }

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        requireActivity().onBackPressed()
        return super.onOptionsItemSelected(item)
    }


}