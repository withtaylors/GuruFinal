package com.example.guru2

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

//식단 기록 삭제 화면

class MealDelete : AppCompatActivity() {

    lateinit var MealdbManager: MealDBManager
    lateinit var sqlitedb: SQLiteDatabase

    lateinit var deleteTime: TextView
    lateinit var deleteFood: TextView
    lateinit var deleteKcal: TextView
    lateinit var delButton: Button
    lateinit var deleteTitle: TextView
    lateinit var delcancelButton: Button

    lateinit var str_food: String
    lateinit var str_kcal: String
    lateinit var str_year: String
    lateinit var str_month: String
    lateinit var str_date: String
    lateinit var str_hour: String
    lateinit var str_minute: String
    lateinit var str_title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_delete)

        deleteTime = findViewById(R.id.deleteTime)
        deleteFood = findViewById(R.id.deleteFood)
        deleteKcal = findViewById(R.id.deleteKcal)
        delButton = findViewById(R.id.delButton)
        deleteTitle = findViewById(R.id.deleteTitle)
        delcancelButton = findViewById(R.id.delcancelButton)

        // 인텐트로 삭제하고 싶은 기록 내용 받기
        val intent = intent
        str_year = intent.getStringExtra("intent_year").toString()
        str_month = intent.getStringExtra("intent_month").toString()
        str_date = intent.getStringExtra("intent_date").toString()
        str_hour = intent.getStringExtra("intent_hour").toString()
        str_minute = intent.getStringExtra("intent_minute").toString()
        str_food = intent.getStringExtra("intent_food").toString()
        str_kcal = intent.getStringExtra("intent_kcal").toString()
        str_title = intent.getStringExtra("intent_title").toString()

        deleteTitle.text = "기록 제목: " + str_title
        deleteTime.text = "기록 날짜: " + str_year+ "년 " + str_month + "월 " + str_date + "일 "+ str_hour + "시 " + str_minute + "분"
        deleteFood.text = "먹은 음식: " + str_food
        deleteKcal.text = "음식 칼로리: " + str_kcal + "Kcal"

        //삭제 버튼을 누르게 되면
        delButton.setOnClickListener {
            MealdbManager = MealDBManager(this, "mealDB", null, 1)
            sqlitedb = MealdbManager.readableDatabase

            // db에서 기록 삭제
            sqlitedb.execSQL("DELETE FROM meal WHERE title = '"+str_title+"' AND year = "+str_year+" AND month = "+str_month+" AND date = "+str_date+" AND hour = "+str_hour+" AND minute = "+str_minute+" AND food = '"+str_food+"' And kcal = "+str_kcal+";")
            sqlitedb.close()
            MealdbManager.close()
            Toast.makeText(applicationContext, "기록이 삭제되었습니다.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MealChoiceActivity::class.java)
            startActivity(intent)
        }
            // 취소 버튼을 누르게 되면
        delcancelButton.setOnClickListener {
            Toast.makeText(applicationContext, "기록 삭제가 취소되었습니다.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MealChoiceActivity::class.java)
            startActivity(intent)
        }
    }
}