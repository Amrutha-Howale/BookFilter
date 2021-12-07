package com.example.bookfilter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val AuthorName = findViewById<TextInputLayout>(R.id.AuthorInput)
        val CountryName = findViewById<TextInputLayout>(R.id.Country)
        val rescount = findViewById<TextView>(R.id.resultOne)
        val result1 = findViewById<TextView>(R.id.resulttwo)
        val result2 = findViewById<TextView>(R.id.resultThree)
        val result3 = findViewById<TextView>(R.id.resultFour)
        val Filterbutton = findViewById<Button>(R.id.button)

        val titles = mutableListOf<String>()

        Filterbutton.setOnClickListener {
            titles.clear()
            rescount.text = ""
            result1.text = ""
            result2.text = ""
            result3.text = ""

            val myApplication = application as MyApplication
            val httpApiService = myApplication.httpApiService

            CoroutineScope(Dispatchers.IO).launch {
                val decodedJsonResult = httpApiService.getMyBook()

                for (item in decodedJsonResult) {
                    if (item.author.lowercase() == AuthorName.editText?.text.toString()
                            .lowercase() && item.country.lowercase() == CountryName.editText?.text.toString()
                            .lowercase()
                    ) {
                        titles.add(item.title)

                    }
                }

                    withContext(Dispatchers.Main) {
                        rescount.text = "Result: " + titles.count().toString()
                        if (titles.count() > 0)
                            result1.text = "Result: " + titles[0]
                        if (titles.count() > 1)
                            result2.text = "Result: " + titles[1]
                        if (titles.count() > 2)
                            result3.text = "Result: " + titles[2]
                    }
                }
            }
        }
    }
