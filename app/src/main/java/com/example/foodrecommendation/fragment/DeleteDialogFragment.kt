package com.example.foodrecommendation.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.bluehomestudio.luckywheel.WheelItem
import java.lang.ClassCastException

class DeleteDialogFragment(cur_wheelItems: MutableList<WheelItem>) : DialogFragment() {
    private var wheelItems: MutableList<WheelItem> = ArrayList()
    private var foodListNameOnly: MutableList<CharSequence> = ArrayList()

    init {
        for (item in cur_wheelItems) {
            wheelItems.add(item)
            foodListNameOnly.add(item.text)
        }
    }

    private lateinit var mListener: OnMultiChoiceListener

    interface OnMultiChoiceListener {
        fun onPositiveButtonClicked(selectedItems: ArrayList<Int>)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = this.parentFragment as OnMultiChoiceListener
        } catch (e: ClassCastException) {
            Log.e("Some Tag", e.message.toString(), e)
            throw ClassCastException(context.toString() + "May bi ngu roi OnMultiChoiceListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val foodCharSequence = foodListNameOnly.toTypedArray<CharSequence>()
            val selectedItems = ArrayList<Int>()

            val builder = AlertDialog.Builder(it)

            builder.setTitle("Food List")

            builder.setMultiChoiceItems(foodCharSequence, null,
                DialogInterface.OnMultiChoiceClickListener { dialog, which, isChecked ->
                    if (isChecked) {
                        selectedItems.add(which)
                    } else if (selectedItems.contains(which)) {
                        selectedItems.remove(which)
                    }
                })

            builder.setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, id ->
                    mListener.onPositiveButtonClicked(selectedItems)
                    // this.dismiss()
                })

            builder.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id ->
                    this.dismiss()
                })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

