package com.example.f1rstdoc.data.internalStorage.repository


import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.internalStorage.usecase.InternalStorageUseCase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.action.PdfAction
import com.itextpdf.kernel.pdf.navigation.PdfExplicitDestination
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.AreaBreak
import com.itextpdf.layout.element.Link
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import java.io.*

class InternalStorageImpl(val context: Context): InternalStorageUseCase {

    companion object{
        private const val FOLDER = "F1rstDocData"
        private const val FOLDER_PATH = "Download/${FOLDER}"

        private const val FILE_NAME_PDF = "f1rst_doc.pdf"
        private const val FILE_NAME_JSON = "f1rst_doc.json"

        private const val APPLICATION_PDF = "application/pdf"
        private const val APPLICATION_JSON = "application/json"


        private const val SUMARY = "Sumário"

    }


    override fun exportData(listDocs: List<Docs>){
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val myFolder = File(downloadsDir, FOLDER)

        if (!myFolder.exists()) {
            myFolder.mkdirs()
        }

        exportToPDF(listDocs,myFolder)
        exportToJsonFile(listDocs,myFolder)
    }




    private fun exportToPDF(listDocs: List<Docs>,myFolder: File ) {


        try {
            val outputStream: OutputStream?

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.Downloads.DISPLAY_NAME, FILE_NAME_PDF)
                    put(MediaStore.Downloads.MIME_TYPE, APPLICATION_PDF)
                    put(MediaStore.Downloads.RELATIVE_PATH, FOLDER_PATH)
                }

                val resolver = context.contentResolver
                val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

                if (uri != null) {
                    outputStream = resolver.openOutputStream(uri)
                } else {
                    throw IOException("Falha ao criar o arquivo PDF")
                }

            } else {

                val file = File(myFolder, FILE_NAME_PDF)

                if (file.exists()) {
                    file.delete()
                }

                outputStream = FileOutputStream(file)
            }

            val writer = PdfWriter(outputStream)
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc)

            document.add(
                Paragraph(SUMARY)
                    .setFontSize(22f)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
            )

            listDocs.forEach { docs ->
                val link = Link(docs.title, PdfAction.createGoTo(docs.title))
                document.add(Paragraph().add(link).setFontSize(16f).setUnderline())
            }
            document.add(AreaBreak())

            listDocs.forEach { docs ->
                document.add(
                    Paragraph(docs.title)
                        .setFontSize(20f)
                        .setBold()
                )

                document.add(Paragraph(docs.date))

                document.add(
                    Paragraph(docs.subTitle)
                        .setFontSize(16f)
                )

                document.add(
                    Paragraph(docs.doc)
                        .setFontSize(14f)
                )

                val page = pdfDoc.getPage(pdfDoc.numberOfPages)
                val dest = PdfExplicitDestination.createFit(page).pdfObject
                pdfDoc.addNamedDestination(docs.title, dest)
                document.add(AreaBreak())
            }

            document.close()
            outputStream?.close()

            Log.i("PDF Export","PDF salvo com sucesso!")

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("PDF Export","Erro ao criar o PDF: ${e.message}")
        }

    }

    private fun exportToJsonFile(listDocs: List<Docs>,myFolder : File) {

        val gson = com.google.gson.GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(listDocs)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, FILE_NAME_JSON)
                put(MediaStore.Downloads.MIME_TYPE, APPLICATION_JSON)
                put(MediaStore.Downloads.RELATIVE_PATH, FOLDER_PATH)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            uri?.let {
                try {
                    resolver.openOutputStream(it)?.use { outputStream ->
                        OutputStreamWriter(outputStream).use { writer ->
                            writer.write(jsonString)
                        }
                    }
                    Log.i("Json Export","Arquivo salvo com sucesso!")
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else {

            val file = File(myFolder, FILE_NAME_JSON)

            try {
                FileWriter(file).use { writer ->
                    writer.write(jsonString)
                }
                Log.i("Json Export","Arquivo salvo com sucesso!")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    override fun selectDataToImport(uri: Uri): List<Docs> {

        val listDocsJson = parseJson(readJsonFile(uri))
        listDocsJson.forEach {
            println("Docs Results = ${it.title}")
        }
        return listDocsJson
    }

    private fun readJsonFile(uri: Uri): String? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.bufferedReader().use { it.readText() }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun parseJson(jsonString: String?): List<Docs> {
        return try {
            val gson = Gson()
            val type = object : TypeToken<List<Docs>>() {}.type
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }

    }


}

