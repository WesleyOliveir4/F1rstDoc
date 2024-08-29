package com.example.f1rstdoc.presentation.utils

import android.text.format.DateFormat
import com.example.f1rstdoc.domain.docs.model.Docs
import java.util.*

fun factoryDocs(title: String, subTitle: String, doc: String,idUser:String ,id: Int?) : Docs {

        val d = Date()
        val docsDate: CharSequence = DateFormat.format("d MMMM yyyy", d.time)

        return Docs(
            id = id,
            idUser = idUser,
            title = title,
            subTitle = subTitle,
            doc = doc,
            date = docsDate as String,
        )

}