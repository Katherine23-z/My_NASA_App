package com.example.mynasaapplication.ui.main.api.ui.canvasExperiment

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mynasaapplication.R

class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SpringTheme)
        super.onCreate(savedInstanceState)
        val myDrawing = MyDrawing(this)
        setContentView(myDrawing)
    }

}