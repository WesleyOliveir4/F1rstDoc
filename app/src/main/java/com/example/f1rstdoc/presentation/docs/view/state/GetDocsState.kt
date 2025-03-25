package com.example.f1rstdoc.presentation.docs.view.state

import com.example.f1rstdoc.domain.docs.model.Docs

sealed interface GetDocsState{
        class ListDocs(val docs: List<Docs>): GetDocsState
}