package co.edu.udea.compumovil.gr05_20242.lab1.ui.components.PersonalData;

import androidx.compose.runtime.Composable;
import co.edu.udea.compumovil.gr05_20242.lab1.ui.components.PersonalDataModel

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.widget.TextViewCompat
import kotlinx.coroutines.CoroutineScope
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataForm(
    modifier: Modifier,
    onPersonaUpdated: (PersonalDataModel) -> Unit,
    openForm: MutableState<Boolean>
) {
    //data
    var nombres by rememberSaveable { mutableStateOf("") }
    var apellidos by rememberSaveable  { mutableStateOf("") }
    var sexo by rememberSaveable  { mutableStateOf("") }
    val fechaNacimiento = rememberSaveable  { mutableStateOf<String>("") }
    var gradoEscolaridad by rememberSaveable  { mutableStateOf("Primaria") }

    //radio buttons sex
    var expandedDropdown by rememberSaveable { mutableStateOf(false) }
    val gradoOptions = listOf("Primaria", "Secundaria", "Universitaria", "Otro")

    //Date Form
    val openDate = rememberSaveable { mutableStateOf(false) }

    // Estado para manejar errores
    var nombresError by rememberSaveable { mutableStateOf(false) }
    var apellidosError by rememberSaveable { mutableStateOf(false) }
    var sexoError by rememberSaveable { mutableStateOf(false) }
    var fechaNacimientoError by rememberSaveable { mutableStateOf(false) }


    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE


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
            isError = nombresError,
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
        if (nombresError) {
            Text("Campo obligatorio", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = {
                Text(
                    text = "Apellidos"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            isError = apellidosError,
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
        if (apellidosError) {
            Text("Campo obligatorio", color = MaterialTheme.colorScheme.error)
        }
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
                onClick = { sexo = "Hombre"; sexoError = false }
            )
            Text("Hombre")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = sexo == "Mujer",
                onClick = { sexo = "Mujer"; sexoError = false }
            )
            Text("Mujer")
        }
        if (sexoError) {
            Text("Debe seleccionar un sexo", color = MaterialTheme.colorScheme.error)
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
                onClick = { openDate.value = true },
                colors = ButtonDefaults.buttonColors(Color(0xFF2196F3)), //
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = if (fechaNacimiento.value.isNotEmpty()) fechaNacimiento.value else "Seleccionar fecha", color = Color.White)
            }

            DatePickerDialogInput(openDate = openDate, fechaNacimiento = fechaNacimiento )

        }

        if (fechaNacimientoError) {
            Text("Debe seleccionar una fecha de nacimiento", color = MaterialTheme.colorScheme.error)
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
                nombresError = nombres.isEmpty()
                apellidosError = apellidos.isEmpty()
                sexoError = sexo.isEmpty()
                fechaNacimientoError = fechaNacimiento.value.isEmpty()

                // Si no hay errores, procede con la asignación de datos
                if (!nombresError && !apellidosError && !sexoError && !fechaNacimientoError) {

                    val updatedPersona = PersonalDataModel(
                        nombres = nombres,
                        apellidos = apellidos,
                        sexo = sexo,
                        fechaNacimiento = fechaNacimiento.value,
                        gradoEscolaridad = gradoEscolaridad
                    )

                    onPersonaUpdated(updatedPersona)

                    // Cerrar el formulario
                    openForm.value = false
                }
            },
            modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.End),
            colors =  ButtonDefaults.buttonColors(Color(0xFF2196F3))
        ) {
            Text("Enviar")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogInput(openDate:MutableState<Boolean>,fechaNacimiento:MutableState<String>){
    if (openDate.value) {
        val datePickerState = rememberDatePickerState()
        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        val formatter: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")

        DatePickerDialog(
            onDismissRequest = {
                openDate.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDate.value = false
                        fechaNacimiento.value = formatter.format(datePickerState.selectedDateMillis)
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDate.value = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
