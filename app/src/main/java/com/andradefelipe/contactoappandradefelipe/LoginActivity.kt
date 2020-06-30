package com.andradefelipe.contactoappandradefelipe

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPref = getPreferences(Context.MODE_PRIVATE)



       /*button_login.setOnClickListener {

            var user = editTextTextEmailAddress.getText().toString()
            var psw = editTextTextPassword.getText().toString()

            if (psw.length >= 8 && !user.isEmpty() && !psw.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(
                    user
                ).matches()
            ) {
                Toast.makeText(this, "Bienvenido a tus contactos", Toast.LENGTH_SHORT).show()
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(user).matches() || user.isEmpty()) {
                editTextTextEmailAddress.setError("Correo no valido, intente de nuevo")
            }
            if (psw.isEmpty() || psw.length < 8) {
                editTextTextPassword.setError("Contraseña no valida, intente de nuevo")
            }
        }*/
    }
}