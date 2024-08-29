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

    fun bottomSheetItem(layoutBottomSheet: Int): BottomSheet {

        val bottomSheet: BottomSheetDialog =
            BottomSheetDialog(context, R.style.BottomSheetStyle)
        bottomSheet.setContentView(layoutBottomSheet)
        val textviewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
        val textviewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)
        bottomSheet.show()
        return BottomSheet(
            yesBtn = textviewYes,
            noBtn = textviewNo,
            bottomSheet = bottomSheet
        )
    }

    class BottomSheet(
        val yesBtn : TextView?,
        val noBtn : TextView?,
        val bottomSheet : BottomSheetDialog,
    )

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