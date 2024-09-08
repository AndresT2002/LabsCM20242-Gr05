package co.edu.udea.compumovil.gr05_20242.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.edu.udea.compumovil.gr05_20242.lab1.ui.components.PersonalData.PersonalDataForm
import co.edu.udea.compumovil.gr05_20242.lab1.ui.theme.LabsCM20242Gr05Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabsCM20242Gr05Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PersonalDataForm(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LabsCM20242Gr05Theme {
       PersonalDataForm(modifier = Modifier)
    }
}


