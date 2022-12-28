package com.kotlin.tiaistiana.ui.negara

import android.os.Bundle
import com.kotlin.tiaistiana.R
import com.kotlin.tiaistiana.base.BaseActivity
import com.kotlin.tiaistiana.databinding.ActivityCountiresBinding
import dagger.hilt.android.AndroidEntryPoint

// Views data binding

@AndroidEntryPoint
class CountriesActivity : BaseActivity<ActivityCountiresBinding>() {

    /**
     * membuat data Binding
     */
    override fun createBinding(): ActivityCountiresBinding =
        ActivityCountiresBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, CountriesFragment.newInstance(1))
                .commit()
        }

    }
}
