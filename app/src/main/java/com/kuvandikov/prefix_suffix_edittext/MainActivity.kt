package com.kuvandikov.prefix_suffix_edittext

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.github.kuvandikov.prefixsuffix.PrefixSuffixEditText

class MainActivity : AppCompatActivity() {
    lateinit var editText: PrefixSuffixEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.edit_text)
        if (savedInstanceState != null) {
            editText.setText(savedInstanceState.getString("edit_text"))
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        try {
            outState.putString("edit_text", editText.text.toString())
        }catch (e: IllegalStateException){
            Log.e("error", e.message.orEmpty())
        }
    }
}