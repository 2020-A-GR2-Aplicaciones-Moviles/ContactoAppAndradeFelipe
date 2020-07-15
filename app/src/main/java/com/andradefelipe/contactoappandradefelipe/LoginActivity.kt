package com.andradefelipe.contactoappandradefelipe

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        LeerDatosDeArchivoPreferenciasEncriptado()
        button_login.setOnClickListener {

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

            EscribirDatosEnArchivoPreferenciasEncriptado()
            Toast.makeText(this, "Datos Guardados", Toast.LENGTH_SHORT).show()
            if (editTextTextEmailAddress.text.toString().equals("felipe@gmail.com") and editTextTextPassword.text.toString().equals("qwertyui")){
                var intent = Intent(this,PrincipalTmpActivity::class.java)
                intent.putExtra(LOGIN_KEY,editTextTextEmailAddress.text.toString())
                startActivity(intent)
                finish()

            }

        }

    }

    fun EscribirDatosEnArchivoPreferenciasEncriptado() {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            SECRET_FILENAME,//filename
            masterKeyAlias,
            this,//context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )


        if (checkBox_Recordarme.isChecked) {
            val editor = sharedPreferences.edit()
            editor.putString(LOGIN_KEY, editTextTextEmailAddress.text.toString())
            editor.putString(PASSWORD_KEY, editTextTextPassword.text.toString())
            editor.apply()
        } else {
            val editor = sharedPreferences.edit()
            editor.putString(LOGIN_KEY, "")
            editor.putString(PASSWORD_KEY, "")
            editor.apply()
        }
    }
    fun LeerDatosDeArchivoPreferenciasEncriptado() {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            SECRET_FILENAME,//filename
            masterKeyAlias,
            this,//context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        editTextTextEmailAddress.setText(sharedPreferences.getString(LOGIN_KEY, ""))
        editTextTextPassword.setText(sharedPreferences.getString(PASSWORD_KEY, ""))
    }

    fun EscribirDatosEnArchivoInterno4(){
        val texto = "texto" + System.lineSeparator() + "almacenado"
        openFileOutput(CONTACTS_FILENAME, Context.MODE_PRIVATE).bufferedWriter().use {fos ->
            fos.write(editTextTextEmailAddress.text.toString())
            fos.write(System.lineSeparator())
            fos.write(editTextTextEmailAddress.text.toString())
        }
    }


    fun LeerDatosEnArchivoInterno4() {
        openFileInput(CONTACTS_FILENAME).bufferedReader().use {
            val datoLeido = it.readText()
            if (datoLeido.isNullOrEmpty()) return
            val textArray = datoLeido.split(System.lineSeparator())
            val login  = textArray[0]
            val clave = textArray[1]
        }
    }




}



