package com.example.f1rstdoc.presentation.docs.view.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.f1rstdoc.R
import com.example.f1rstdoc.databinding.FragmentHomeBinding
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.firebase.model.RealtimeDatabaseResult
import com.example.f1rstdoc.presentation.docs.adapter.DocsAdapter
import com.example.f1rstdoc.presentation.docs.view.state.GetDocsState
import com.example.f1rstdoc.presentation.docs.view.state.ImportDocsState
import com.example.f1rstdoc.presentation.docs.viewmodel.DocsViewModel
import com.example.f1rstdoc.presentation.login.view.LoginActivity
import com.example.f1rstdoc.presentation.utils.MessageBuilderUtils
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.navigation.findNavController
import com.example.f1rstdoc.presentation.utils.UiConstants.CLOUDFIREBASE
import com.example.f1rstdoc.presentation.utils.UiConstants.EXPORT
import com.example.f1rstdoc.presentation.utils.UiConstants.IMPORT
import com.example.f1rstdoc.presentation.utils.UiConstants.LOGOUT
import com.example.f1rstdoc.presentation.utils.UiConstants.SEARCH


class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private val docsViewModel: DocsViewModel by viewModel()

    private lateinit var listDocs: List<Docs>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        createDocs()
        getDocs()
        return binding.root
    }

    private fun getDocs() {
        docsViewModel.getDocs()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                docsViewModel.stateGetDocs.collect { docsList ->
                    when (docsList) {
                        is GetDocsState.ListDocs -> {
                            listDocs = docsList.docs
                            pushRecyclerView(listDocs)
                        }
                    }
                }
            }

        }
    }

    private fun createDocs() {
        binding.btnAddDocs.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_createDocsFragment)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.title) {
            EXPORT -> {
                exportMenuOption()
            }

            CLOUDFIREBASE -> {
                exportCloudMenuOption()
            }

            LOGOUT -> {
                logoutMenuOption()

            }

            IMPORT -> {
                importMenuOption()
            }

            SEARCH -> {
                val searchView = item.actionView as SearchView
                searchMenuOption(searchView)
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun searchMenuOption(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(textChanged: String?): Boolean {
                textChanged?.let { text ->
                    val textLowerCase = text.lowercase()
                    val listDocsFilter = listDocs.filter { doc ->
                        doc.title.lowercase().contains(textLowerCase) ||
                                doc.subTitle.lowercase().contains(textLowerCase) ||
                                doc.doc.lowercase().contains(textLowerCase)
                    }
                    pushRecyclerView(listDocsFilter)
                }
                return true
            }
        })
    }

    private fun importMenuOption() {
        openFilePicker()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                docsViewModel.stateImportDocs.collect { state ->
                    when (state) {
                        ImportDocsState.Success -> {
                            MessageBuilderUtils(requireContext()).MessageShowTimer(
                                getString(R.string.import_docs_storage_success), 1500
                            )
                        }

                        ImportDocsState.Failure -> {
                            MessageBuilderUtils(requireContext()).MessageShowTimer(
                                getString(R.string.import_docs_storage_failure), 1500
                            )
                        }

                        ImportDocsState.Loading -> {
                            // implementar loading
                        }
                    }
                }
            }
        }
    }

    private fun logoutMenuOption() {
        val bottomSheetItem = MessageBuilderUtils(requireContext()).bottomSheetItem(
            R.layout.dialog_bottom_sheet, messageText = getString(R.string.message_logout_builder)
        )

        bottomSheetItem.yesBtn?.setOnClickListener {
            docsViewModel.logoutUser()
            bottomSheetItem.bottomSheet.dismiss()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)

            requireActivity().finish()
        }

        bottomSheetItem.noBtn?.setOnClickListener {
            bottomSheetItem.bottomSheet.dismiss()
        }

        bottomSheetItem.bottomSheet.show()
    }

    private fun exportCloudMenuOption() {
        val bottomSheetItem = MessageBuilderUtils(requireContext()).bottomSheetItem(
            R.layout.dialog_bottom_sheet, messageText = getString(R.string.message_cloud_builder)
        )

        bottomSheetItem.yesBtn?.setOnClickListener {

            docsViewModel.saveRealDatabase(listDocs)
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    docsViewModel.stateRealtimeResult.collect { stateSaveDocs ->
                        when (stateSaveDocs) {

                            is RealtimeDatabaseResult.Success -> {
                                MessageBuilderUtils(requireActivity()).MessageShowTimer(
                                    getString(R.string.save_docs_firebase_success), 1500
                                )
                            }

                            is RealtimeDatabaseResult.Failure -> {
                                MessageBuilderUtils(requireActivity()).MessageShow(getString(R.string.save_docs_firebase_failure))
                            }

                            RealtimeDatabaseResult.Loading -> {
                                //Implementar loading
                            }
                        }
                    }

                    bottomSheetItem.bottomSheet.dismiss()

                }
            }
        }

        bottomSheetItem.noBtn?.setOnClickListener {
            bottomSheetItem.bottomSheet.dismiss()
        }


        bottomSheetItem.bottomSheet.show()
    }

    private fun exportMenuOption() {
        val bottomSheetItem = MessageBuilderUtils(requireContext()).bottomSheetItem(
            R.layout.dialog_bottom_sheet, messageText = getString(R.string.message_export_builder)
        )


        bottomSheetItem.yesBtn?.setOnClickListener {

            try {
                docsViewModel.writeToFile(listDocs)
                MessageBuilderUtils(requireContext()).MessageShowTimer(
                    getString(R.string.save_docs_storage_success), 1500
                )
            } catch (e: Exception) {
                MessageBuilderUtils(requireContext()).MessageShow(getString(R.string.save_docs_storage_failure))
            }
            bottomSheetItem.bottomSheet.dismiss()
        }



        bottomSheetItem.noBtn?.setOnClickListener {
            bottomSheetItem.bottomSheet.dismiss()
        }

        bottomSheetItem.bottomSheet.show()
    }


    private val filePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                uri?.let { selectedUri ->
                    docsViewModel.importDataDocs(selectedUri)
                }
            }
        }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/json"
        }
        filePickerLauncher.launch(intent)
    }


    private fun pushRecyclerView(listDocs: List<Docs>) {
        binding.rcvAllDocs.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvAllDocs.adapter = DocsAdapter(listDocs)
    }


}