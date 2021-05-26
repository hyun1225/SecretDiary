package com.chjun.secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3)
            .apply {
                minValue = 0
                maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.openButton)
    }

    private val chagePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.changePasswordButton)
    }

    private var chagePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener {

            if(chagePasswordMode){
                Toast.makeText(this,"비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)

            val passwordFromUser =
                "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                //패스워드 성공
                startActivity(Intent(this, DiaryActivity::class.java))


            } else {
                //패스워드 실패
                showErrorAlertDialog()
            }
        }

        chagePasswordButton.setOnClickListener {
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser =
                "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if(chagePasswordMode){
                if(passwordPreferences.getString("password","000").equals(passwordFromUser)){
                    AlertDialog.Builder(this)
                        .setTitle("실패")
                        .setMessage("이전 패스워스드와 동일한 번호로는 바꿀수 없습니다.")
                        .setPositiveButton("확인") { _, _ -> }
                        .create()
                        .show()
                    chagePasswordMode = false
                    chagePasswordButton.setBackgroundColor(Color.BLACK)
                    return@setOnClickListener
                }
                passwordPreferences.edit(true){
                    putString("password", passwordFromUser)
                }
                chagePasswordMode = false
                chagePasswordButton.setBackgroundColor(Color.BLACK)
            }else{
                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                    chagePasswordMode = true
                    Toast.makeText(this,"변경할 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
                    chagePasswordButton.setBackgroundColor(Color.RED)
                } else {
                    //패스워드 실패
                    showErrorAlertDialog()
                }
            }
        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패")
            .setMessage("비밀번호가 틀렸습니다.")
            .setPositiveButton("확인") { _, _ -> }
            .create()
            .show()
    }


}
