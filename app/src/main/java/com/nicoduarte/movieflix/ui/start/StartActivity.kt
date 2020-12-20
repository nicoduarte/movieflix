package com.nicoduarte.movieflix.ui.start

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.nicoduarte.movieflix.databinding.ActivityStartBinding
import com.nicoduarte.movieflix.ui.BaseActivity
import com.nicoduarte.movieflix.ui.main.MainActivity
import com.nicoduarte.movieflix.ui.utils.show
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : BaseActivity() {

    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivLogo.show()
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