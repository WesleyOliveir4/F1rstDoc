package com.example.f1rstdoc.presentation.docs.edit

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.f1rstdoc.R
import com.example.f1rstdoc.databinding.FragmentEditDocsBinding
import com.example.f1rstdoc.presentation.docs.viewmodel.DocsViewModel
import com.example.f1rstdoc.presentation.utils.MessageBuilderUtils
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditDocsFragment: Fragment() {

    private val DELETE ="Delete"

    private val oldDocs by navArgs<EditDocsFragmentArgs>()
    lateinit var  binding: FragmentEditDocsBinding
    private val docsViewModel: DocsViewModel by viewModel()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditDocsBinding.inflate(layoutInflater , container, false)
        setHasOptionsMenu(true)

        binding.edtTitle.setText(oldDocs.data.title)
        binding.edtSubTitle.setText(oldDocs.data.subTitle)
        binding.edtDocs.setText(oldDocs.data.doc)

        saveEditDocs()

        return binding.root
    }

    private fun saveEditDocs() {
        binding.btnEditSaveDocs.setOnClickListener {
            try {
                docsViewModel.updateDocs(
                    binding.edtTitle.text.toString(),
                    binding.edtSubTitle.text.toString(),
                    binding.edtDocs.text.toString(),
                    oldDocs.data.id!!
                )

                MessageBuilderUtils(requireContext()).MessageShowTimer(
                    getString(R.string.update_docs_success),
                    1500
                )
                Navigation.findNavController((it!!))
                    .navigate(R.id.action_editDocsFragment_to_homeFragment)
            } catch (e: Exception) {
                MessageBuilderUtils(requireContext()).MessageShow(getString(R.string.update_docs_failure))
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.title){
            DELETE -> {
                val bottomSheetItem =
                    MessageBuilderUtils(requireContext()).bottomSheetItem(R.layout.dialog_delete)

                bottomSheetItem.yesBtn?.setOnClickListener{
                    try{
                        docsViewModel.deleteDocs(oldDocs.data.id!!)
                        MessageBuilderUtils(requireContext()).MessageShowTimer(getString(R.string.delete_docs_success),1500)
                        bottomSheetItem.bottomSheet.dismiss()
                        requireActivity().onBackPressed()
                    }catch(e: Exception){
                        MessageBuilderUtils(requireContext()).MessageShow(getString(R.string.delete_docs_failure))
                    }

                }
                bottomSheetItem.noBtn?.setOnClickListener{
                    bottomSheetItem.bottomSheet.dismiss()
                }

                bottomSheetItem.bottomSheet.show()
            }
            else -> {
                    requireActivity().onBackPressed()
            }
        }


        return super.onOptionsItemSelected(item)
    }


}