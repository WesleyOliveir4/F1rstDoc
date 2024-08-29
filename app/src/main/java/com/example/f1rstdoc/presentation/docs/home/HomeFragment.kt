package com.example.f1rstdoc.presentation.docs.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.f1rstdoc.presentation.docs.adapter.DocsAdapter
import com.example.f1rstdoc.R
import com.example.f1rstdoc.databinding.FragmentHomeBinding
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.presentation.docs.state.SaveDocsState
import com.example.f1rstdoc.presentation.docs.viewmodel.DocsViewModel
import com.example.f1rstdoc.presentation.utils.MessageBuilderUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val EXPORTAR="Exportar"
    private val CLOUDFIREBASE="CloudFirebase"

    private lateinit var binding: FragmentHomeBinding
    private val docsViewModel: DocsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        createDocs()
        return binding.root
    }

    private fun createDocs() {
        binding.btnAddDocs.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createDocsFragment)
        }
    }

    override fun onResume() {
        super.onResume()

        docsViewModel.getDocs().observe(viewLifecycleOwner) { docsList ->
            pushRecyclerView(docsList)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.title){
            EXPORTAR -> {
                val bottomSheetItem =
                    MessageBuilderUtils(requireContext()).bottomSheetItem(R.layout.dialog_export)

                docsViewModel.getDocs().observe(viewLifecycleOwner) { docsList ->

                    bottomSheetItem.yesBtn?.setOnClickListener {

                        try {
                            docsViewModel.writeToFile(docsList)
                            MessageBuilderUtils(requireContext()).MessageShowTimer(
                                getString(R.string.save_docs_storage_success),
                                1500
                            )
                        } catch (e: Exception) {
                            MessageBuilderUtils(requireContext()).MessageShow(getString(R.string.save_docs_storage_failure))
                        }
                        bottomSheetItem.bottomSheet.dismiss()
                    }
                }

                bottomSheetItem.noBtn?.setOnClickListener {
                    bottomSheetItem.bottomSheet.dismiss()
                }

                bottomSheetItem.bottomSheet.show()
            }
            CLOUDFIREBASE -> {
                val bottomSheetItem =
                    MessageBuilderUtils(requireContext()).bottomSheetItem(R.layout.dialog_cloud)

                docsViewModel.getDocs().observe(viewLifecycleOwner) { docsList ->

                    bottomSheetItem.yesBtn?.setOnClickListener {

                        docsViewModel.saveRealDatabase(docsList)
                        docsViewModel.stateSaveDocs.observe(viewLifecycleOwner) { stateSaveDocs ->
                            when (stateSaveDocs) {
                                is SaveDocsState.Loading -> {

                                }
                                is SaveDocsState.Success -> {
                                    MessageBuilderUtils(requireActivity()).MessageShowTimer(
                                        getString(R.string.save_docs_firebase_success),
                                        1500
                                    )
                                }
                                is SaveDocsState.Failure -> {
                                    MessageBuilderUtils(requireActivity()).MessageShow(getString(R.string.save_docs_firebase_failure))
                                }

                            }
                        }

                        bottomSheetItem.bottomSheet.dismiss()

                    }

                    bottomSheetItem.noBtn?.setOnClickListener {
                        bottomSheetItem.bottomSheet.dismiss()
                    }

                }
                bottomSheetItem.bottomSheet.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun pushRecyclerView(listDocs: List<Docs>) {
        binding.rcvAllDocs.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvAllDocs.adapter = DocsAdapter(listDocs)
    }
}