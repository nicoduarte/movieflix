package com.nicoduarte.movieflix.ui.start

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nicoduarte.movieflix.R
import com.nicoduarte.movieflix.ui.main.MainActivity
import com.nicoduarte.movieflix.ui.utils.show
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        ivLogo.show()

        lifecycleScope.launch {
            delay(DELAY_TIME)
            startActivity(Intent(this@StartActivity, MainActivity::class.java))
            finish()
        }
    }

    companion object {
        const val DELAY_TIME = 3000L
    }
}