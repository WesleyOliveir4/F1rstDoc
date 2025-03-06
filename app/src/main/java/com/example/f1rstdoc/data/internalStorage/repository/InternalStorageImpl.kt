package com.example.f1rstdoc.data.internalStorage.repository


import android.os.Environment
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.internalStorage.usecase.InternalStorageUseCase
import com.google.gson.GsonBuilder
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.action.PdfAction
import com.itextpdf.kernel.pdf.navigation.PdfExplicitDestination
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.AreaBreak
import com.itextpdf.layout.element.Link
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import java.io.File
import java.io.FileWriter
import java.io.IOException

class InternalStorageImpl(): InternalStorageUseCase {

    override fun exportToPDF(listDocs: List<Docs>) {

        try {

            val filePath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/f1rst_doc.pdf"
            val file = File(filePath)

            if (file.exists()) {
                file.delete()
            }

            val writer = PdfWriter(file)
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc)

            // Criar página inicial (Sumário)
            document.add(Paragraph("Sumário")
                .setFontSize(22f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
            )

            // Criar links no sumário
            listDocs.forEach { docs ->
                val link = Link(docs.title, PdfAction.createGoTo(docs.title))
                document.add(Paragraph().add(link).setFontSize(16f).setUnderline())
            }
            document.add(AreaBreak())

            // Adicionar título e conteúdo dos docs
            listDocs.forEach {docs ->
                document.add(Paragraph(docs.title)
                    .setFontSize(20f)
                    .setBold()
                )

                document.add(Paragraph(docs.date))

                document.add(Paragraph(
                    """
                        ${docs.subTitle}
                        """
                ).setFontSize(16f))

                document.add(Paragraph(
                    """
                        ${docs.doc}
                        """
                ).setFontSize(14f))


                val page = pdfDoc.getPage(pdfDoc.numberOfPages)
                val dest = PdfExplicitDestination.createFit(page).getPdfObject()
                pdfDoc.addNamedDestination(docs.title, dest)
                document.add(AreaBreak())
            }

            document.close()

        } catch (e: Exception) {
            e.printStackTrace()
            println("Erro ao criar o PDF: ${e.message}")
        }
    }

    override fun exportJsonToFile(listDocs: List<Docs>) {

        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(listDocs)

        // Define o diretório de salvamento (Downloads)
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(downloadsDir, "f1rst_doc.json")

        try {
            FileWriter(file).use { writer ->
                writer.write(jsonString)
            }
            println("Arquivo salvo com sucesso em: ${file.absolutePath}")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}

