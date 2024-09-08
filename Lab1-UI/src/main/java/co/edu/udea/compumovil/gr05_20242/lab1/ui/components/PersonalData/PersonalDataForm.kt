package co.edu.udea.compumovil.gr05_20242.lab1.ui.components.PersonalData;

import androidx.compose.runtime.Composable;
import co.edu.udea.compumovil.gr05_20242.lab1.ui.components.PersonalDataModel

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataForm(
    modifier: Modifier,
    personalDataModel: PersonalDataModel
) {
    // State variables for the form
    var nombres by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var gradoEscolaridad by remember { mutableStateOf("Primaria") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var expandedDropdown by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val gradoOptions = listOf("Primaria", "Secundaria", "Universitaria", "Otro")

    var isFocusedName by remember { mutableStateOf(false) }

    // DatePicker dialog setup
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            fechaNacimiento = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = nombres,
            onValueChange = { nombres = it },
            label = {
                Text(
                    text = "Nombres"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                autoCorrect = false
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Icono de persona"
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Apellidos Field
        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = {
                Text(
                    text = "Nombres"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                autoCorrect = false
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Icono de persona"
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Sexo Radio Buttons
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()){
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Icono de sexo",
                modifier = Modifier.padding(end = 6.dp)
            )
            Text("Sexo")
            RadioButton(
                selected = sexo == "Hombre",
                onClick = { sexo = "Hombre" }
            )
            Text("Hombre")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = sexo == "Mujer",
                onClick = { sexo = "Mujer" }
            )
            Text("Mujer")
        }
        Spacer(modifier = Modifier.height(2.dp))

        // Fecha de Nacimiento Picker
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(end = 6.dp)){
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Icono de calendario",
                modifier = Modifier.padding(end = 6.dp)
            )
            Text("Fecha de nacimiento")

            //insertar boton con calendario
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(Color(0xFF2196F3)), //
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Fecha", color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expandedDropdown,
            onExpandedChange = { expandedDropdown = !expandedDropdown },
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = gradoEscolaridad,
                onValueChange = {},
                readOnly = true,
                label = { Text("Grado de Escolaridad") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Icono de escolaridad",
                        modifier = Modifier.padding(end = 6.dp)
                    )
                }
            )

            ExposedDropdownMenu(
                expanded = expandedDropdown,
                onDismissRequest = { expandedDropdown = false }
            ) {
                gradoOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            gradoEscolaridad = option
                            expandedDropdown = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Submit Button
        Button(
            onClick = {
                // Create an instance of PersonalDataModel and pass the values
                val personalData = PersonalDataModel(
                    nombres = nombres,
                    apellidos = apellidos,
                    sexo = sexo,
                    fechaNacimiento = fechaNacimiento,
                    gradoEscolaridad = gradoEscolaridad,
                    telefono = telefono,
                    email = email
                )
            },
            modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.End),
            colors =  ButtonDefaults.buttonColors(Color(0xFF2196F3))
        ) {
            Text("Enviar")
        }
    }
}

// Validation function for the form
fun validateFields(data: PersonalDataModel): Boolean {
    return data.nombres.isNotEmpty() &&
            data.apellidos.isNotEmpty() &&
            data.sexo.isNotEmpty() &&
            data.fechaNacimiento.isNotEmpty() &&
            data.telefono.isNotEmpty() &&
            data.email.isNotEmpty()
}
