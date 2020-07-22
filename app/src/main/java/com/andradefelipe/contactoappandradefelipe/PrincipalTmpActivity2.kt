package com.andradefelipe.contactoappandradefelipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PrincipalTmpActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_tmp2)
        val username = intent.getStringExtra(LOGIN_KEY)
        supportActionBar?.title = supportActionBar?.title.toString() + " - " + username.substringBefore("@")
    }

}