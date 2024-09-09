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
    val persona = PersonalDataModel() // informacion
    val openForm1 = rememberSaveable { mutableStateOf<Boolean>(true) }
    if(openForm1.value) {
        PersonalDataForm(modifier = Modifier, persona, openForm1)
    }
    if(!openForm1.value) {
        Log.d("Ayuda", persona.toString())
    }
}

