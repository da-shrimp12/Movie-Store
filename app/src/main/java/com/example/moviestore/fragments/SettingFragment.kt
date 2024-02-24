package com.example.moviestore.fragments

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.moviestore.R
import android.content.DialogInterface
import android.text.InputType
import android.widget.EditText
import com.example.movies.base.Constants
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.example.moviestore.base.SharePreferencesUtils


class SettingFragment : Fragment() {

    private lateinit var mLabelCategory: TextView
    private lateinit var mLabelRate: TextView
    private lateinit var mLabelReleaseYear: TextView
    private lateinit var mLabelSort: TextView
    private lateinit var mTextCategory: TextView
    private lateinit var mTextRate: TextView
    private lateinit var mTextReleaseYear: TextView
    private lateinit var mTextSort: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        mLabelCategory = view.findViewById(R.id.label_category)
        mLabelRate = view.findViewById(R.id.label_rate)
        mLabelReleaseYear = view.findViewById(R.id.label_release_year)
        mLabelSort = view.findViewById(R.id.label_sort)
        mTextCategory = view.findViewById(R.id.text_category)
        mTextRate = view.findViewById(R.id.text_rate)
        mTextReleaseYear = view.findViewById(R.id.text_release_year)
        mTextSort = view.findViewById(R.id.text_sort)
        initData()
        setListener()
        return view
    }

    private fun initData() {
        val sharePref = SharePreferencesUtils(context)
        var defaultCategory = sharePref.getStringByKey(Constants.FILTER_CATEGORY_KEY)
        if (defaultCategory == null) {
            sharePref.setStringByKey(Constants.FILTER_CATEGORY_KEY, "0")
            defaultCategory = "0";
        }
        var defaultRate = sharePref.getStringByKey(Constants.FILTER_FROM_RATE_KEY)
        if (defaultRate == null) {
            sharePref.setStringByKey(Constants.FILTER_FROM_RATE_KEY, "0")
            defaultRate = "0";
        }
        var defaultYear = sharePref.getStringByKey(Constants.FILTER_FROM_YEAR_KEY)
        if (defaultYear == null) {
            sharePref.setStringByKey(Constants.FILTER_FROM_YEAR_KEY, "0")
            defaultYear = "0";
        }
        var defaultSort = sharePref.getStringByKey(Constants.SORT_BY_KEY)
        if (defaultSort == null) {
            sharePref.setStringByKey(Constants.SORT_BY_KEY, "0")
            defaultSort = "0";
        }

        mTextCategory.text = Constants.CATEGORY_OPTIONS[defaultCategory.toInt()]
        mTextRate.text = defaultRate
        mTextReleaseYear.text = defaultYear
        mTextSort.text = Constants.SORT_BY_OPTIONS[defaultSort.toInt()]

    }

    private fun setListener() {
        mLabelCategory.setOnClickListener {

            val builder = Builder(context)
            builder.setItems(Constants.CATEGORY_OPTIONS) { dialogInterface: DialogInterface, i: Int ->
                mTextCategory.text = Constants.CATEGORY_OPTIONS[i]
                SharePreferencesUtils(context).setStringByKey(Constants.FILTER_CATEGORY_KEY, i.toString())
            }
            builder.show()
        }
        mLabelSort.setOnClickListener {

            val builder = Builder(context)
            builder.setItems(Constants.SORT_BY_OPTIONS) { dialogInterface: DialogInterface, i: Int ->
                mTextSort.text = Constants.SORT_BY_OPTIONS[i]
                SharePreferencesUtils(context).setStringByKey(Constants.SORT_BY_KEY, i.toString())
            }
            builder.show()
        }
        mLabelRate.setOnClickListener { showDialogSeekBar() }
        mLabelReleaseYear.setOnClickListener { showDialogInput() }
    }

    private fun showDialogSeekBar() {
        val popDialog = Builder(context)
        val seek = SeekBar(context)
        popDialog.setTitle("Movie with rate from")
        seek.max = 10
        popDialog.setView(seek)
        seek.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                //Do something here with new value
                mTextRate.setText("$progress")
                SharePreferencesUtils(context).setStringByKey(Constants.FILTER_FROM_RATE_KEY, progress.toString())
            }

            override fun onStartTrackingTouch(arg0: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }
        })


        // Button OK
        popDialog.setPositiveButton(
            "OK"
        ) { dialog, which -> dialog.dismiss() }
        popDialog.create()
        popDialog.show()
    }

    private fun showDialogInput() {
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("From Release year")

        val input = EditText(context)
        input.hint = "Input year"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            mTextReleaseYear.text = input.text.toString()
            SharePreferencesUtils(context).setStringByKey(Constants.FILTER_FROM_YEAR_KEY, input.text.toString())
        })
        builder.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

}