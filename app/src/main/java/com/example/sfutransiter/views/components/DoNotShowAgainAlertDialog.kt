package com.example.sfutransiter.views.components

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.sfutransiter.R
import com.example.sfutransiter.util.Constants


class DoNotShowAgainAlertDialog(private val builder: Builder) : DialogFragment() {
    private var isShowDialog = true

    class Builder {
        var title: CharSequence? = null
        var message: CharSequence? = null
        var positiveButtonText: CharSequence? = null
        var negativeButtonText: CharSequence? = null
        var positiveOnclick: ((dialog: DialogInterface, which: Int) -> Unit)? = null
        var negativeOnClick: ((dialog: DialogInterface, which: Int) -> Unit)? = null

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setPositiveButton(
            text: CharSequence,
            listener: ((dialog: DialogInterface, which: Int) -> Unit)?
        ): Builder {
            this.positiveButtonText = text
            this.positiveOnclick = listener
            return this
        }

        fun setNegativeButton(
            text: CharSequence,
            listener: ((dialog: DialogInterface, which: Int) -> Unit)?
        ): Builder {
            this.negativeButtonText = text
            this.negativeOnClick = listener
            return this
        }

        fun create() = DoNotShowAgainAlertDialog(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isShowDialog =
            requireContext().getSharedPreferences(Constants.Pref.PREF_NAME, Context.MODE_PRIVATE)
                .getBoolean(Constants.Pref.SHOW_DIALOG, true)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = inflateDialogView()

        val dialog = activity?.let {
            AlertDialog.Builder(it).apply {
                setView(view)
                setPositiveButton(builder.positiveButtonText) { dialog, which ->
                    builder.positiveOnclick?.let { it1 -> it1(dialog, which) }
                    requireContext().getSharedPreferences(
                        Constants.Pref.PREF_NAME,
                        Context.MODE_PRIVATE
                    )
                        .edit().putBoolean(Constants.Pref.SHOW_DIALOG, isShowDialog).apply()
                }
                setNegativeButton(builder.negativeButtonText, builder.negativeOnClick)
            }
        }
        return dialog?.create() ?: throw java.lang.IllegalStateException("Activity cannot be null")
    }

    fun showIfAllowed(context: Context, manager: FragmentManager) {
        if (context.getSharedPreferences(Constants.Pref.PREF_NAME, Context.MODE_PRIVATE)
                .getBoolean(Constants.Pref.SHOW_DIALOG, true)
        )
            show(manager, TAG)
    }

    @SuppressLint("InflateParams")
    private fun inflateDialogView(): View? {
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_do_not_show_again, null)

        view.findViewById<TextView>(R.id.text_title).text = builder.title
        view.findViewById<TextView>(R.id.text_message).text = builder.message
        view.findViewById<CheckBox>(R.id.checkbox_do_not_show_again)
            .setOnCheckedChangeListener { _, isChecked ->
                isShowDialog = !isChecked
            }
        return view
    }

    companion object {
        val TAG: String = DoNotShowAgainAlertDialog::class.java.simpleName
    }
}