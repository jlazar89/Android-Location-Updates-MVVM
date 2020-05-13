package com.android.locationmvvm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.locationmvvm.ui.main.LocationFragment
import com.android.locationmvvm.utils.Constants.RequestCode.GPS_REQUEST_CODE
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {

    val locationFragment :LocationFragment = LocationFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GPS_REQUEST_CODE) {
            locationFragment.onActivityResult(requestCode, resultCode, data)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
