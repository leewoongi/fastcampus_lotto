package com.wg.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var clearButton: Button
    private lateinit var addButton: Button
    private lateinit var runButton: Button
    private lateinit var numberPicker: NumberPicker
    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()

    private val numberTextViewList: List<TextView> by lazy{
        listOf<TextView>(
                findViewById<TextView>(R.id.tv_first_number),
                findViewById<TextView>(R.id.tv_second_number),
                findViewById<TextView>(R.id.tv_third_number),
                findViewById<TextView>(R.id.tv_fourth_number),
                findViewById<TextView>(R.id.tv_fifth_number),
                findViewById<TextView>(R.id.tv_sixth_number),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialized()
        initNumberPickerRange()
        initRunButton()
        initAddButton()
        initClear()
    }

    private fun initialized() {
        clearButton = findViewById(R.id.btn_clear)
        addButton = findViewById(R.id.btn_add_lotto_number)
        runButton = findViewById(R.id.btn_run_lotto)
        numberPicker = findViewById(R.id.np_lotto)
    }

    private fun initNumberPickerRange() {
        numberPicker.minValue = 1
        numberPicker.maxValue = 45
    }

    private fun initRunButton(){
        runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true

            list.forEachIndexed{ index, number ->
                val textView = numberTextViewList[index]
                textView.text = number.toString()
                textView.isVisible = true
            }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>()
                .apply {
            for(i in 1..45){
                if(pickNumberSet.contains(i)){
                    continue
                }
                this.add(i)
            }
        }
        numberList.shuffle()
        return pickNumberSet.toList() + numberList.subList(0,6 - pickNumberSet.size).sorted()
    }

    private fun initAddButton() {
        addButton.setOnClickListener {
            if(didRun){
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(pickNumberSet.size >= 6){
                Toast.makeText(this, "번호는 6개까지만 선택 할 수 있습니다.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this, "이미 선택한 번호 입니다.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()
            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun initClear(){
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            numberTextViewList.forEach{
                it.isVisible = false
            }
        }
        didRun = false
    }
}