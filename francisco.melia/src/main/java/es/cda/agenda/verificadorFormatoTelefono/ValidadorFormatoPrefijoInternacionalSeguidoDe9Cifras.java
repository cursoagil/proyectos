package es.cda.agenda.verificadorFormatoTelefono;

import java.util.regex.Pattern;

public class ValidadorFormatoPrefijoInternacionalSeguidoDe9Cifras extends ValidadorFormatoTelefono
{
    public Boolean valida(String numeroTelefonoConFormato)
    {
        pattern = Pattern.compile("\\+\\d{2,3} \\d{9}");

        return coincidePatron(numeroTelefonoConFormato);
    }
}
