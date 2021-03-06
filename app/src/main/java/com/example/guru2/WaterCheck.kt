package com.example.guru2

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WaterCheck : AppCompatActivity() {
    lateinit var WaterDBManger: WaterDBManger
    lateinit var sqlitedb: SQLiteDatabase
    lateinit var layout: LinearLayout
    lateinit var scrollWater: ScrollView
    lateinit var bgChoice:ImageView
    lateinit var watertext:TextView
    lateinit var textView8:TextView
    lateinit var addButton:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_check2)

        WaterDBManger = WaterDBManger(this, "waterlist", null, 1 )
        sqlitedb = WaterDBManger.readableDatabase

        layout = findViewById(R.id.waterlist)
        scrollWater = findViewById(R.id.scrollWater)
        bgChoice = findViewById(R.id.bgChoice)
        watertext = findViewById(R.id.watertext)
        textView8 = findViewById(R.id.textView8)
        addButton = findViewById(R.id.addButton)

        //커서 연결
        var cursor: Cursor
        cursor = sqlitedb.rawQuery("SELECT * FROM waterlist", null)

        // DB에서 해당하는 날짜의 기록을 가져와서 레이아웃에 추가한다
        var num: Int = 0
        while (cursor.moveToNext()){
            var str_year = cursor.getString(cursor.getColumnIndex("year")).toString()
            var str_month = cursor.getString(cursor.getColumnIndex("month")).toString()
            var str_date = cursor.getString(cursor.getColumnIndex("date")).toString()
            var str_waterml = cursor.getString(cursor.getColumnIndex("water")).toString()

            var layout_item: LinearLayout = LinearLayout(this)
            layout_item.orientation = LinearLayout.VERTICAL
            layout_item.id = num

            var tvCalView: TextView = TextView(this)
            tvCalView.text = str_year + "년" + str_month + "월" + str_date + "일 음수량"
            tvCalView.textSize = 20F
            tvCalView.setBackgroundColor(Color.parseColor("#FAF2EE"))
            tvCalView.setTextColor(Color.BLACK)
            layout_item.addView(tvCalView)

            var tvWater: TextView = TextView(this)
            tvWater.text = str_waterml+"ml"
            tvWater.textSize = 20F
            tvWater.setBackgroundColor(Color.WHITE)
            tvWater.setTextColor(Color.GRAY)
            layout_item.addView(tvWater)

            //추가하기 floating 버튼 클릭시
            addButton.setOnClickListener {
                val intent = Intent(this, WaterEdit::class.java)
                startActivity(intent)
            }

            //삭제할 기록을 누르면 삭제 화면으로 이동
            layout_item.setOnClickListener{
                val intent = Intent(this, WaterDelete::class.java)
                intent.putExtra("year", str_year)
                intent.putExtra("month", str_month)
                intent.putExtra("date", str_date)
                intent.putExtra("water", str_waterml)
                startActivity(intent)
            }
            layout.addView(layout_item)
            num++
        }
        cursor.close()
        sqlitedb.close()
        WaterDBManger.close()
    }
    //메뉴바 추가
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    //메뉴바 이동 하기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.main->{
                val mainIntent= Intent(this,MainActivity::class.java)
                startActivity(mainIntent)
            }
            R.id.sport->{
                val sportIntent=Intent(this,HealthListActivity::class.java)
                startActivity(sportIntent)
            }
            R.id.meal->{
                val mealIntent=Intent(this,MealWriteActivity::class.java)
                startActivity(mealIntent)
            }
            R.id.water->{
                val waterIntent=Intent(this,WaterCheck::class.java)
                startActivity(waterIntent)
            }
            R.id.mental->{
                val mentalIntent=Intent(this,MentalDailyActivity::class.java)
                startActivity(mentalIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
