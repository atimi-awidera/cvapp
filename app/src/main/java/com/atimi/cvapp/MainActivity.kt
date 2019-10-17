package com.atimi.cvapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.atimi.cvapp.ui.main.CvFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CvFragment.newInstance())
                .commitNow()
        }
    }

}
