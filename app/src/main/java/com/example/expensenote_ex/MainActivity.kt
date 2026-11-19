package com.example.expensenote_ex

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    //สร้างตัวแปรสำหรับ UI
    private lateinit var etAmount: EditText
    private lateinit var etNote: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnCler: Button
    private lateinit var tvLastItem: TextView
    private lateinit var tvTotal: TextView

    //ตัวแปรสำหรับเก็บค่ายอดรวมทั้งหมด
    private var total: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //เชื่อมโยงตัวแปร เข้ากับ ID ในหน้า XML
        etAmount = findViewById<EditText>(R.id.etAmount)
        etNote = findViewById<EditText>(R.id.etNote)
        btnAdd = findViewById<Button>(R.id.btnAdd)
        btnCler = findViewById<Button>(R.id.btnClear)
        tvTotal = findViewById<TextView>(R.id.tvTotal)
        tvLastItem = findViewById<TextView>(R.id.tvLastItem)

        //กำหนดการทำงานเมื่อผู้ใช้ทำการกดปุ่มเพิ่มรายการ
        btnAdd.setOnClickListener {
            //สร้างตัวแปรที่เอาไว้เก็บข้อมูลจากช่อง
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()

            //ทำการตรวจสอบว่าผู้ใช้ทำการกรอกจำนวนเงินมาแล้วหรือยัง
            if (amountText.isEmpty()){
                Toast.makeText(this,"กรุณากรอกจำนวนเงิน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountText.toDoubleOrNull()
            if (amount == null){
                //แจ้งเตือนผู้ใช้ว่าให้กรอกตัวเลขมา ไม่ใช่ตัวหนังสือ
                Toast.makeText(this,"กรุณากรอกเฉพาะตัวเลข ในช่องกรอกจำนวนเงิน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //เอาจำนวนมารวมกัน
            total += amount

            //ตรวจสอบว่ามีการกรอกคำอธิบายมั้ย ถ้าไม่มี ให้เป็นคำว่า "ไม่ระบุ"
            val showNote = if (noteText.isNotEmpty()) noteText else "ไม่ระบุ"

            tvLastItem.text = "รายการล่าสุด: $showNote - %.2f บาท".format(amount)
            tvTotal.text = "ยอดรวมวันนี้: %.2f บาท".format(total)

            etAmount.text.clear()
            etNote.text.clear()
        }
        btnCler.setOnClickListener {
            total = 0.0

            etAmount.text.clear()
            etNote.text.clear()

            tvLastItem.text = "รายการล่าสุด: -"
            tvTotal.text = "ยอดรวมวันนี้: 0.00 บาท"

        }
    }
}