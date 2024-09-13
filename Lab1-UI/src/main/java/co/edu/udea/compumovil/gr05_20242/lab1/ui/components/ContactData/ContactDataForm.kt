package co.edu.udea.compumovil.gr05_20242.lab1.ui.components.ContactData;

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import co.edu.udea.compumovil.gr05_20242.lab1.R
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Definición de la interfaz de la API
interface CiudadesApiService {
    @GET("xdk5-pm3f.json")
    suspend fun getCiudades(): List<Ciudad>
}

// Modelo de datos para la ciudad (ajustado al formato de la API)
data class Ciudad(
    val municipio: String
)

// Creación del cliente Retrofit para el consumo de la api
val retrofit = Retrofit.Builder()
    .baseUrl("https://www.datos.gov.co/resource/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val ciudadesApiService = retrofit.create(CiudadesApiService::class.java)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDataForm(
    modifier: Modifier,
    onPersonaUpdated: (PersonalDataModel) -> Unit,
    openForm: MutableState<Boolean>
) {
    var telefono by rememberSaveable { mutableStateOf("") }
    var direccion by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var pais by rememberSaveable { mutableStateOf("") }
    var ciudad by rememberSaveable { mutableStateOf("") }

    var expandedPaisDropdown by rememberSaveable { mutableStateOf(false) }
    var expandedCiudadDropdown by rememberSaveable { mutableStateOf(false) }

    val paisesOptions = listOf(
        "Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Costa Rica", "Cuba", "Ecuador",
        "El Salvador", "Guatemala", "Honduras", "México", "Nicaragua", "Panamá", "Paraguay",
        "Perú", "República Dominicana", "Uruguay", "Venezuela"
    )
    var telefonoError by rememberSaveable { mutableStateOf(false) }
    var direccionError by rememberSaveable { mutableStateOf(false) }
    var paisError by rememberSaveable { mutableStateOf(false) }
    var ciudadError by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf<EmailError?>(null) }


    var ciudades by remember { mutableStateOf(listOf<Ciudad>()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    // Cargar las ciudades al iniciar el componente
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                ciudades = ciudadesApiService.getCiudades()
                isLoading = false
            } catch (e: Exception) {
                error = "Error al cargar los municipios: ${e.message}"
                isLoading = false
            }
        }
    }

    Column(modifier = Modifier
        .padding(16.dp)
        .padding(top = 52.dp)) {

        // Teléfono
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text(text = stringResource(id = R.string.telefono)) },
            modifier = Modifier.fillMaxWidth(),
            isError = telefonoError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Icono de teléfono"
                )
            }

        )
        if (telefonoError) {
            Text("Campo obligatorio", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Dirección
        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text(text = stringResource(id = R.string.direccion)) },
            modifier = Modifier.fillMaxWidth(),
            isError = direccionError,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false, imeAction = ImeAction.Next
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Icono de dirección"
                )
            }
        )
        if (direccionError) {
            Text("Campo obligatorio", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = when {
                    it.isEmpty() -> EmailError.Empty
                    !isValidEmail(it) -> EmailError.InvalidFormat
                    else -> null
                }
            },
            label = { Text(text = stringResource(id = R.string.email)) },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Icono de email"
                )
            }
        )
        emailError?.let { error ->
            Text(
                text = when (error) {
                    EmailError.Empty -> "Campo obligatorio"
                    EmailError.InvalidFormat -> "Formato de email inválido"
                },
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // País (Autocomplete)
        ExposedDropdownMenuBox(
            expanded = expandedPaisDropdown,
            onExpandedChange = { expandedPaisDropdown = !expandedPaisDropdown },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = pais,
                onValueChange = {
                    pais = it
                    expandedPaisDropdown = true
                },
                label = { Text(text = stringResource(id = R.string.pais)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPaisDropdown) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                isError = paisError,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Icono de país"
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expandedPaisDropdown,
                onDismissRequest = { expandedPaisDropdown = false }
            ) {
                paisesOptions.filter { it.contains(pais, ignoreCase = true) }.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            pais = option
                            expandedPaisDropdown = false
                            paisError = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        if (paisError) {
            Text("Campo obligatorio", color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Dropdown de ciudades (municipios)
        ExposedDropdownMenuBox(
            expanded = expandedCiudadDropdown,
            onExpandedChange = { expandedCiudadDropdown = !expandedCiudadDropdown },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = ciudad,
                onValueChange = { ciudad = it },
                label = { Text(text = stringResource(id = R.string.ciudad)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCiudadDropdown) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                readOnly = isLoading,
                enabled = !isLoading
            )
            if (!isLoading) {
                ExposedDropdownMenu(
                    expanded = expandedCiudadDropdown,
                    onDismissRequest = { expandedCiudadDropdown = false }
                ) {
                    ciudades.filter { it.municipio.contains(ciudad, ignoreCase = true) }.forEach { ciudadItem ->
                        DropdownMenuItem(
                            text = { Text(ciudadItem.municipio) },
                            onClick = {
                                ciudad = ciudadItem.municipio
                                expandedCiudadDropdown = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }
        if (isLoading) {
            Text("Cargando municipios...", color = MaterialTheme.colorScheme.secondary)
        }
        if (error != null) {
            Text(error!!, color = MaterialTheme.colorScheme.error)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button
        Button(
            onClick = {
                telefonoError = telefono.isEmpty()
                direccionError = direccion.isEmpty()
                emailError = when {
                    email.isEmpty() -> EmailError.Empty
                    !isValidEmail(email) -> EmailError.InvalidFormat
                    else -> null
                }
                paisError = pais.isEmpty()
                ciudadError = ciudad.isEmpty()

                if (!telefonoError && !direccionError && emailError == null && !paisError && !ciudadError) {
                    val updatedPersona = PersonalDataModel(
                        telefono = telefono,
                        direccion = direccion,
                        email = email,
                        pais = pais,
                        ciudad = ciudad
                    )

                    onPersonaUpdated(updatedPersona)
                    openForm.value = false
                }
            },
            modifier = Modifier.fillMaxWidth(0.5f).align(Alignment.End),
            colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
        ) {
            Text("Enviar")
        }
    }
}
enum class EmailError {
    Empty,
    InvalidFormat
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}