package co.edu.udea.compumovil.gr05_20242.lab1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.edu.udea.compumovil.gr05_20242.lab1.ui.components.ContactData.ContactDataForm
import co.edu.udea.compumovil.gr05_20242.lab1.ui.components.PersonalData.PersonalDataForm
import co.edu.udea.compumovil.gr05_20242.lab1.ui.components.PersonalDataModel
import co.edu.udea.compumovil.gr05_20242.lab1.ui.theme.LabsCM20242Gr05Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabsCM20242Gr05Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Forms(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun Forms(modifier: Modifier) {
    var persona = remember { mutableStateOf<PersonalDataModel>(PersonalDataModel())} // informacion
    val openForm1 = rememberSaveable { mutableStateOf<Boolean>(true) }
    val openForm2 = rememberSaveable { mutableStateOf<Boolean>(false) }
    if (openForm1.value) {
        PersonalDataForm(modifier = Modifier, openForm = openForm1, openForm2 = openForm2, onPersonaUpdated = { updatedPersona ->
            // Actualiza todo el objeto de persona con los cambios
            persona.value = updatedPersona
        })
    }
    if (openForm2.value) {
        ContactDataForm(modifier = Modifier, openForm = openForm2,   onPersonaUpdated = { updatedPersona ->
            // Crea un nuevo objeto con los campos actualizados
            persona.value = persona.value.copy(
                pais = updatedPersona.pais,
                ciudad = updatedPersona.ciudad,
                email = updatedPersona.email,
                direccion = updatedPersona.direccion,
                telefono = updatedPersona.telefono
            )
        })
    }

    if (!openForm1.value && !openForm2.value) {
        Log.d("resultado", persona.value.toString()) // Debería mostrar los datos actualizados de contacto
    }
}

