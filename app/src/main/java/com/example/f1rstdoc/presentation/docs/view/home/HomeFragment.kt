package com.example.f1rstdoc.presentation.docs.view.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.f1rstdoc.R
import com.example.f1rstdoc.databinding.FragmentHomeBinding
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.firebase.model.RealtimeDatabaseResult
import com.example.f1rstdoc.presentation.docs.adapter.DocsAdapter
import com.example.f1rstdoc.presentation.docs.viewmodel.DocsViewModel
import com.example.f1rstdoc.presentation.login.view.LoginActivity
import com.example.f1rstdoc.presentation.utils.MessageBuilderUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    companion object{
        private const val EXPORTAR="Exportar"
        private const val CLOUDFIREBASE="CloudFirebase"
        private const val LOGOUT="Logout"
        private const val IMPORTAR="Importar Docs"
    }


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
                    MessageBuilderUtils(requireContext()).bottomSheetItem(
                        R.layout.dialog_bottom_sheet,
                        messageText = getString(R.string.message_export_builder)
                    )

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
                    MessageBuilderUtils(requireContext()).bottomSheetItem(
                        R.layout.dialog_bottom_sheet,
                        messageText = getString(R.string.message_cloud_builder)
                    )

                docsViewModel.getDocs().observe(viewLifecycleOwner) { docsList ->

                    bottomSheetItem.yesBtn?.setOnClickListener {

                        docsViewModel.saveRealDatabase(docsList)
                        docsViewModel.stateRealtimeResult.observe(viewLifecycleOwner) { stateSaveDocs ->
                            when (stateSaveDocs) {

                                is RealtimeDatabaseResult.Success -> {
                                    MessageBuilderUtils(requireActivity()).MessageShowTimer(
                                        getString(R.string.save_docs_firebase_success),
                                        1500
                                    )
                                }
                                is RealtimeDatabaseResult.Failure -> {
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
            LOGOUT -> {
                val bottomSheetItem =
                    MessageBuilderUtils(requireContext()).bottomSheetItem(
                        R.layout.dialog_bottom_sheet,
                        messageText = getString(R.string.message_logout_builder)
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
            IMPORTAR ->{
                openFilePicker()
            }


        }

        return super.onOptionsItemSelected(item)
    }


    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri: Uri? = result.data?.data
            uri?.let { selectedUri ->
                // Faça algo com o arquivo selecionado, por exemplo, exibir ou ler o conteúdo
                docsViewModel.importDataDocs(selectedUri)
//                handleSelectedFile(selectedUri)
            }
        }
    }
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*" // Pode ser "image/*", "video/*", "application/pdf" etc.
        }
        filePickerLauncher.launch(intent)
    }

//    fun handleSelectedFile(uri: Uri) {
//        // Exemplo: Pegando o nome do arquivo
//        val fileName = getFileNameFromUri(uri)
//        Log.d("FilePicker", "Arquivo selecionado: $fileName")
//    }
//
//    private fun getFileNameFromUri(uri: Uri): String? {
//        val contentResolver = requireActivity().contentResolver
//        val cursor = contentResolver.query(uri, null, null, null, null)
//        cursor?.use { it ->
//            if (it.moveToFirst()) {
//                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//                return it.getString(nameIndex)
//            }
//        }
//        return null
//    }

    private fun pushRecyclerView(listDocs: List<Docs>) {
        binding.rcvAllDocs.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvAllDocs.adapter = DocsAdapter(listDocs)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}