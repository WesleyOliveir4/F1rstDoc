package com.example.f1rstdoc.presentation.utils

import android.content.Context
import android.widget.TextView
import com.example.f1rstdoc.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*


class MessageBuilderUtils(
    private val context: Context,
) {

    fun MessageShow(message: String) {

        val bottomSheet: BottomSheetDialog =
            BottomSheetDialog(context, R.style.BottomSheetStyle)
        bottomSheet.setContentView(R.layout.dialog_message_app)
        val edtInput = bottomSheet.findViewById<TextView>(R.id.edt_input)
        edtInput?.text = message
        bottomSheet.show()
    }


    fun MessageShowTimer(message: String, delay: Long?) {

        if (delay!! < 0) {delay == 0L  }
        val timer: Timer = Timer()

        val bottomSheet: BottomSheetDialog =
            BottomSheetDialog(context, R.style.BottomSheetStyle)
        bottomSheet.setContentView(R.layout.dialog_message_app)
        val edtInput = bottomSheet.findViewById<TextView>(R.id.edt_input)
        edtInput?.text = message
        bottomSheet.show()

        timer.schedule(object : TimerTask() {
            override fun run() {
                bottomSheet.dismiss()
            }
        }, delay)
    }


}