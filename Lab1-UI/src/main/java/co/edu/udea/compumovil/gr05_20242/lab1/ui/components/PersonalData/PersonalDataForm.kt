package co.edu.udea.compumovil.gr05_20242.lab1.ui.components.PersonalData;

import androidx.compose.runtime.Composable;
import co.edu.udea.compumovil.gr05_20242.lab1.ui.components.PersonalDataModel

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataForm(
    onFormSubmit: (PersonalDataModel) -> Unit // Function to handle form submission
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
        // Nombres Field
        TextField(
            value = nombres,
            onValueChange = { nombres = it },
            label = { Text("Nombres") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text
            ),
            isError = nombres.isEmpty(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Apellidos Field
        TextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text
            ),
            isError = apellidos.isEmpty(),
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Sexo Radio Buttons
        Text("Sexo")
        Row {
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
        Spacer(modifier = Modifier.height(8.dp))

        // Fecha de Nacimiento Picker
        TextField(
            value = fechaNacimiento,
            onValueChange = { },
            label = { Text("Fecha de Nacimiento") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(Icons.Filled.DateRange, contentDescription = "Select Date")
                }
            },
            readOnly = true,
            isError = fechaNacimiento.isEmpty(),
        )
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
                modifier = Modifier.menuAnchor().fillMaxWidth()
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
                if (validateFields(personalData)) {
                    onFormSubmit(personalData) // Call the function to handle form submission
                }
            },
            modifier = Modifier.fillMaxWidth()
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
