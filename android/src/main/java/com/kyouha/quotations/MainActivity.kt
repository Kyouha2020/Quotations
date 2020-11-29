package com.kyouha.quotations

import QuotationsTheme
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import ui.Home

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuotationsTheme {
                Home(
                    onAvatarClick = {
                        startActivity(Intent(this, QuotationsActivity::class.java).apply {
                            putExtra("owner", it)
                        })
                    }
                )
            }
        }
    }
}