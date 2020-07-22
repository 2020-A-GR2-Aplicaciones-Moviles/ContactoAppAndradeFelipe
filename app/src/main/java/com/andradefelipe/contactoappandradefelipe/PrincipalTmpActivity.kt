package com.andradefelipe.contactoappandradefelipe

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

class PrincipalTmpActivity : AppCompatActivity() {
    var contactos = arrayListOf<ContactoModelClass>()
    var selectedContactPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_tmp)

        contactos.add(ContactoModelClass(1,"Victor","Velepucha","09911223344", "victor.velepucha@epn.edu.ec"))
        contactos.add(ContactoModelClass(2,"Juan","Perez","0991234567", "juan.perez@epn.edu.ec"))
        contactos.add(ContactoModelClass(3,"Michelle","Salinas","0995225599", "michelle.salinas@epn.edu.ec"))
        contactos.add(ContactoModelClass(4,"Rosa","Vallejo","+593995225100", "rosa.vallejo@epn.edu.ec"))
        val contactoAdaptador = ContactoAdapter(this, contactos)
        listViewContacts.adapter = contactoAdaptador
        listViewContacts.setOnItemClickListener { parent, view, position, id ->
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
            contactos.add(ContactoModelClass(id,nombre,apellido,telefono, email))
            val contactoAdaptador = ContactoAdapter(this, contactos)
            listViewContacts.adapter = contactoAdaptador
            Toast.makeText(this,"Contacto añadido", Toast.LENGTH_LONG).show()
            limpiarCamposEditables()
        }

        buttonView.setOnClickListener {
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
            })
            dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                //pass
            })
            dialogBuilder.create().show()
        }


    }
    private fun limpiarCamposEditables() {
        editTextNumber.setText("")
        editTextTextFirstName.setText("")
        editTextTextLastName.setText("")
        editTextPhoneNumber.setText("")
        editTextTextEmailAddress2.setText("")
    }

}