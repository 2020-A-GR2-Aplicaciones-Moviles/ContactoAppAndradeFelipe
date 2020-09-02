package com.andradefelipe.contactoappandradefelipe

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.andradefelipe.contactoappandradefelipe.database.ContactsSQLiteOpenHelper
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_principal_tmp.*
import kotlinx.android.synthetic.main.activity_principal_tmp2.*
import kotlinx.android.synthetic.main.activity_principal_tmp2.buttonDelete
import kotlinx.android.synthetic.main.activity_principal_tmp2.buttonSave
import kotlinx.android.synthetic.main.activity_principal_tmp2.buttonUpdate
import kotlinx.android.synthetic.main.activity_principal_tmp2.buttonView
import kotlinx.android.synthetic.main.activity_principal_tmp2.editTextNumber
import kotlinx.android.synthetic.main.activity_principal_tmp2.editTextPhoneNumber
import kotlinx.android.synthetic.main.activity_principal_tmp2.editTextTextFirstName
import kotlinx.android.synthetic.main.activity_principal_tmp2.listViewContacts
import org.json.JSONObject

class PrincipalTmpActivity : AppCompatActivity() {
    var contactos = arrayListOf<ContactoModelClass>()
    var selectedContactPosition = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_tmp)
        val username = intent.getStringExtra(LOGIN_KEY)
        supportActionBar?.title = supportActionBar?.title.toString() + " - " + username.substringBefore("@")

        ConsultarContactos()



        ConsultarContactos()

        listViewContacts1.setOnItemClickListener { parent, view, position, id ->
            selectedContactPosition = position
            editTextNumber.setText(contactos[selectedContactPosition].userId.toString())
            editTextTextFirstName.setText(contactos[selectedContactPosition].firstName.toString())
            editTextLastName.setText(contactos[selectedContactPosition].lastName.toString())
            editTextPhoneNumber.setText(contactos[selectedContactPosition].phoneNumber.toString())
            editTextEmailAddress.setText(contactos[selectedContactPosition].emailAddress.toString())
        }

        buttonSave.setOnClickListener {
            val id = editTextNumber.text.toString().toInt()
            val nombre = editTextTextFirstName.text.toString()
            val apellido = editTextTextLastName.text.toString()
            val telefono = editTextPhoneNumber.text.toString()
            val email = editTextTextEmailAddress2.text.toString()
            //contactos.add(ContactoModelClass(id,nombre,apellido,telefono, email))

            val respuesta = ContactsSQLiteOpenHelper(this).addContactos(
                ContactoModelClass(
                    id,
                    nombre,
                    apellido,
                    telefono,
                    email
                )
            )
            if (respuesta > -1) {

            //val contactoAdaptador = ContactoAdapter(this, contactos)
            //listViewContacts.adapter = contactoAdaptador
            Toast.makeText(this, "Contacto añadido", Toast.LENGTH_LONG).show()
            limpiarCamposEditables()
            }
            else {
                Toast.makeText(this, "Error al grabar contacto", Toast.LENGTH_LONG).show()

            }

        }
            //BOTON VIEW
        buttonView.setOnClickListener {
            val contactos = ContactsSQLiteOpenHelper(this).readContacto( )
            listViewContacts.adapter = ContactoAdapter(this, contactos)
            limpiarCamposEditables()
        }

        buttonUpdate.setOnClickListener {
            contactos[selectedContactPosition].userId = editTextNumber.text.toString().toInt()
            contactos[selectedContactPosition].firstName = editTextTextFirstName.text.toString()
            contactos[selectedContactPosition].lastName = editTextTextLastName.text.toString()
            contactos[selectedContactPosition].phoneNumber = editTextPhoneNumber.text.toString()
            contactos[selectedContactPosition].emailAddress = editTextTextEmailAddress2.text.toString()
            listViewContacts.adapter = ContactoAdapter(this, contactos)
            Toast.makeText(this,"Contacto actualizado",Toast.LENGTH_LONG).show()
            limpiarCamposEditables()
        }

        buttonDelete.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Confirmación de Eliminación")
            dialogBuilder.setMessage("¿Esta seguro que desea eliminar el contacto ${contactos[selectedContactPosition].firstName} ${contactos[selectedContactPosition].lastName}?")
            dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->
                contactos.removeAt(selectedContactPosition)
                val contactoAdaptador = ContactoAdapter(this, contactos)
                listViewContacts.adapter = contactoAdaptador
                Toast.makeText(this,"Contacto eliminado",Toast.LENGTH_LONG).show()
                limpiarCamposEditables()
               // val dialogBuilder = AlertDialog.Builder(this).setIcon(
                 //   android.R.drawable.ic_dialog_alert)
            })
            dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                //pass
            })
            dialogBuilder.create().show()
            //ICONO DE WARNING DEL MENSAJE DE CONFIRMACION DE ELIMINACION.
            dialogBuilder.setIcon(android.R.drawable.ic_popup_disk_full)
        }


    }

    private fun ConsultarContactos() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.androidhive.info/contacts/"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val jsonObj = JSONObject(response.toString())
                val contacts = jsonObj.getJSONArray("contacts")
                for (i in 0 until contacts.length()) {
                    val c = contacts.getJSONObject(i)
                    val id = c.getString("id").substring(1).toInt()
                    val name = c.getString("name")
                    val nombre = name.substringBefore(" ")
                    val apellido = name.substringAfter(" ")
                    val email = c.getString("email")
                    //val address = c.getString("address")
                    //val gender = c.getString("gender")
                    val phone = c.getJSONObject("phone")
                    val mobile = phone.getString("mobile")
                    val home = phone.getString("home")
                    //val office = phone.getString("office")
                    val respuesta = ContactsSQLiteOpenHelper(this).addContactos(
                        ContactoModelClass(
                            id,
                            nombre,
                            apellido,
                            mobile,
                            email
                        )
                    )
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error to read Webservice: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.d("MYTAG",error.message)
            }
        )
        queue.add(jsonObjectRequest)


    }

    private fun limpiarCamposEditables() {
        editTextNumber.setText("")
        editTextTextFirstName.setText("")
        editTextTextLastName.setText("")
        editTextPhoneNumber.setText("")
        editTextTextEmailAddress2.setText("")
    }
    // HACER UNA LLAMADA
    fun onClickEnviarCall(view: View){
        makeCall()
    }

    fun makeCall() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + { ContactsSQLiteOpenHelper.COLUMN_NAME_USERID })
            startActivity(intent)
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + { ContactsSQLiteOpenHelper.COLUMN_NAME_USERID })
            val result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            if (result == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent)
            } else {
                requestForCallPermission()
            }
        }
    }

    private fun requestForCallPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission for call was granted!", Toast.LENGTH_SHORT).show();
                    makeCall()
                } else {
                    Toast.makeText(this, "Permission for call was denied!", Toast.LENGTH_SHORT).show();
                }
                return
            }
            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }



}