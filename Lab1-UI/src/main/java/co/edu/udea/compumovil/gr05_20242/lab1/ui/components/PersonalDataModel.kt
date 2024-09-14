package co.edu.udea.compumovil.gr05_20242.lab1.ui.components;

data class PersonalDataModel(
        // Campos para la actividad PersonalDataActivity
        var nombres: String = "",
        var apellidos: String = "",
        var sexo: String = "",
        var fechaNacimiento: String = "",
        var gradoEscolaridad: String = "",

        // Campos para la actividad ContactDataActivity
        var telefono: String = "",
        var direccion: String = "",
        var email: String = "",
        var pais: String = "",
        var ciudad: String = ""
) {
        override fun toString(): String {
                return """
            Información Personal:
            --------------------
            Nombres           : $nombres
            Apellidos         : $apellidos
            Sexo              : $sexo
            Fecha de Nacimiento: $fechaNacimiento
            Grado de Escolaridad: $gradoEscolaridad

            Información de Contacto:
            ------------------------
            Teléfono          : $telefono
            Dirección         : $direccion
            Email             : $email
            País              : $pais
            Ciudad            : $ciudad
        """.trimIndent()
        }
}