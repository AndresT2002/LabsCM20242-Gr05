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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.widget.TextViewCompat
import co.edu.udea.compumovil.gr05_20242.lab1.R
import kotlinx.coroutines.CoroutineScope
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataForm(
    modifier: Modifier,
    onPersonaUpdated: (PersonalDataModel) -> Unit,
    openForm: MutableState<Boolean>,
    openForm2:MutableState<Boolean>
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


    Column(modifier = Modifier
        .padding(16.dp)
        .padding(top = 52.dp)) {



        Text(
            text = stringResource(id = R.string.titulo_uno),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
        )


        if (landscape) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = nombres,
                    onValueChange = { nombres = it },
                    label = {
                        Text(text = stringResource(id = R.string.nombres))
                    },
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    isError = nombresError,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Icono de persona"
                        )
                    }
                )

                OutlinedTextField(
                    value = apellidos,
                    onValueChange = { apellidos = it },
                    label = {
                        Text(text = stringResource(id = R.string.apellidos))
                    },
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    isError = apellidosError,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Icono de persona"
                        )
                    }
                )
            }
        } else {
            // Layout para la orientación vertical
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = nombres,
                    onValueChange = { nombres = it },
                    label = {
                        Text(text = stringResource(id = R.string.nombres))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = nombresError,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Icono de persona"
                        )
                    }
                )
                // Mensajes de error, si son necesarios
                if (nombresError) {
                    Text(stringResource(id = R.string.campo_obligatorio), color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = apellidos,
                    onValueChange = { apellidos = it },
                    label = {
                        Text(text = stringResource(id = R.string.apellidos))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = apellidosError,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        autoCorrect = false,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = "Icono de persona"
                        )
                    }
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))
        if (apellidosError) {
            Text(stringResource(id = R.string.campo_obligatorio), color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Sexo Radio Buttons
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically ,modifier = Modifier.fillMaxWidth()){
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Icono de sexo",
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(text = stringResource(id = R.string.sexo))
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

            Text(stringResource(id = R.string.error_sexo), color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(2.dp))

        // Fecha de Nacimiento Picker
        Row( horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(end = 6.dp)){
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Icono de calendario",
                modifier = Modifier.padding(end = 6.dp)
            )
            Text(text = stringResource(id = R.string.fecha_nacimiento))

            //insertar boton con calendario
            Button(
                onClick = { openDate.value = true },
                colors = ButtonDefaults.buttonColors(Color(0xFF2196F3)), //
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(16.dp)
            ) {
                Text(text = if (fechaNacimiento.value.isNotEmpty()) fechaNacimiento.value else stringResource(id = R.string.seleccionar_fecha), color = Color.White)
            }

            DatePickerDialogInput(openDate = openDate, fechaNacimiento = fechaNacimiento )

        }

        if (fechaNacimientoError) {
            Text(stringResource(id = R.string.error_fecha_nacimiento), color = MaterialTheme.colorScheme.error)
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
                label = { Text(text = stringResource(id = R.string.grado_escolaridad)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth(0.6f),
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
                    // Abrir el formulario
                    openForm2.value = true
                }
            },
            modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.End),
            colors =  ButtonDefaults.buttonColors(Color(0xFF2196F3))
        ) {
            Text(stringResource(id = R.string.enviar))
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
