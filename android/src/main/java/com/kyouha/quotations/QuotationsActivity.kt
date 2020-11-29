package com.kyouha.quotations

import QuotationsTheme
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.platform.setContent
import data.QuotationOwner
import ui.Quotations

class QuotationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuotationsTheme {
                Quotations(intent.getSerializableExtra("owner") as QuotationOwner)
            }
        }
    }
}