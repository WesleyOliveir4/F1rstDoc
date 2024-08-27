package com.example.f1rstdoc.presentation.docs.edit

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.devk.data.Message.MessageBuilder
import com.example.f1rstdoc.R
import com.example.f1rstdoc.databinding.FragmentEditDocsBinding
import com.example.f1rstdoc.presentation.docs.viewmodel.DocsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditDocsFragment: Fragment() {

    private val oldNotes by navArgs<EditDocsFragmentArgs>()
    lateinit var  binding: FragmentEditDocsBinding
    private val docsViewModel: DocsViewModel by viewModel()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditDocsBinding.inflate(layoutInflater , container, false)
        setHasOptionsMenu(true)

        binding.edtTitle.setText(oldNotes.data.title)
        binding.edtSubTitle.setText(oldNotes.data.subTitle)
        binding.edtNotes.setText(oldNotes.data.doc)


        binding.btnEditSaveNotes.setOnClickListener{
            try {
                docsViewModel.updateDocs (
                    binding.edtTitle.text.toString(),
                    binding.edtSubTitle.text.toString(),
                    binding.edtNotes.text.toString(),
                    oldNotes.data.id!!)

                MessageBuilder(requireContext()).MessageShowTimer( getString(R.string.update_docs_success),1500)
                Navigation.findNavController((it!!)).navigate(R.id.action_editDocsFragment_to_homeFragment)
            }catch (e: Exception){
                Log.e("exception in editSaveNotesListener ","$e")
                MessageBuilder(requireContext()).MessageShow(getString(R.string.update_docs_failure))
            }

        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.title == "Delete"){
            val bottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)

            val textviewYes=bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textviewNo=bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textviewYes?.setOnClickListener{
               try{
                   docsViewModel.deleteNotes(oldNotes.data.id!!)
                   MessageBuilder(requireContext()).MessageShowTimer(getString(R.string.delete_docs_success),1500)
                   bottomSheet.dismiss()
                   requireActivity().onBackPressed()
               }catch(e: Exception){
                   MessageBuilder(requireContext()).MessageShow(getString(R.string.delete_docs_failure))
               }

            }
            textviewNo?.setOnClickListener{
                bottomSheet.dismiss()
            }

            bottomSheet.show()
        }else{
            requireActivity().onBackPressed()
        }


        return super.onOptionsItemSelected(item)
    }


}