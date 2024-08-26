package com.example.f1rstdoc.presentation.docs.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.devk.data.Message.MessageBuilder
import com.example.devk.presentation.state.SaveNotesState
import com.example.devk.presentation.ui.Adapter.NotesAdapter
import com.example.f1rstdoc.R
import com.example.f1rstdoc.databinding.FragmentHomeBinding
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.presentation.docs.viewmodel.DocsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val docsViewModel: DocsViewModel by viewModel()

    private lateinit var auth: FirebaseAuth
    private var database: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()

        createNotes()

        return binding.root
    }

    private fun createNotes() {
        binding.btnAddNotes.setOnClickListener {
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

        if (item.title == "Exportar") {
            val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_export)

            val textviewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textviewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            docsViewModel.getDocs().observe(viewLifecycleOwner) { docsList ->

                textviewYes?.setOnClickListener {
                    try {
                        docsViewModel.writeToFile(docsList)
                        MessageBuilder(requireContext()).MessageShowTimer(
                            getString(R.string.save_docs_storage_success),
                            1500
                        )
                    } catch (e: Exception) {
                        MessageBuilder(requireContext()).MessageShow(getString(R.string.save_docs_storage_failure))
                        Log.e("Exception", "Falha na exportação: $e ")
                    }
                    bottomSheet.dismiss()
                }
            }

            textviewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }

            bottomSheet.show()
        } else if (item.title == "CloudFirebase") {

            val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_cloud)

            val textviewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textviewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            docsViewModel.getDocs().observe(viewLifecycleOwner) { docsList ->

                textviewYes?.setOnClickListener {

                    docsViewModel.saveRealDatabase(docsList)
                    docsViewModel.stateSaveNote.observe(viewLifecycleOwner) { stateSaveNote ->
                        when (stateSaveNote) {
                            is SaveNotesState.Loading -> {

                            }
                            is SaveNotesState.Success -> {
                                MessageBuilder(requireActivity()).MessageShowTimer(
                                    getString(R.string.save_docs_firebase_success),
                                    1500
                                )
                            }
                            is SaveNotesState.Failure -> {
                                MessageBuilder(requireActivity()).MessageShow(getString(R.string.save_docs_firebase_failure))
                            }

                        }
                    }

                    bottomSheet.dismiss()

                }

                textviewNo?.setOnClickListener {
                    bottomSheet.dismiss()
                }

            }
            bottomSheet.show()

        } else {
            requireActivity().onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun pushRecyclerView(listNotes: List<Docs>) {
        binding.rcvAllNotes.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvAllNotes.adapter = NotesAdapter(listNotes)
    }
}