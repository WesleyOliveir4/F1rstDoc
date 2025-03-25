package com.example.f1rstdoc.presentation.docs.view.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.example.f1rstdoc.R
import com.example.f1rstdoc.databinding.FragmentCreateDocsBinding
import com.example.f1rstdoc.presentation.docs.view.state.CreateDocsState
import com.example.f1rstdoc.presentation.docs.viewmodel.DocsViewModel
import com.example.f1rstdoc.presentation.utils.MessageBuilderUtils
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.findNavController

class CreateDocsFragment : Fragment() {

    lateinit var binding: FragmentCreateDocsBinding
    private val docsViewModel: DocsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateDocsBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        createDocs()

        return binding.root
    }

    private fun createDocs() {
        binding.btnSaveDocs.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val subTitle = binding.edtSubTitle.text.toString()
            val docs = binding.edtDoc.text.toString()

            docsViewModel.createDocs(title, subTitle, docs)


            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    docsViewModel.stateCreateDocs.collect { stateCreateDocs ->
                        when (stateCreateDocs) {
                            is CreateDocsState.Success -> {
                                requireView().findNavController()
                                    .navigate(R.id.action_createDocsFragment_to_homeFragment)
                                MessageBuilderUtils(requireContext()).MessageShowTimer(
                                    getString(R.string.create_docs_success),
                                    1500
                                )
                            }
                            is CreateDocsState.Failure -> {
                                MessageBuilderUtils(requireActivity()).MessageShow(getString(R.string.error_save_doc_is_empty))
                            }
                            is CreateDocsState.Loading ->{
                                // Implementação do loading
                            }

                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        requireActivity().onBackPressed()
        return super.onOptionsItemSelected(item)
    }


}