package es.cda.agenda.verificadorFormatoTelefono;

import java.util.regex.Pattern;

public class ValidadorFormatoGrupoDe3CifrasSeguidoDe6Cifras extends ValidadorFormatoTelefono
{
    public Boolean valida(String numeroTelefonoConFormato)
    {
        pattern = Pattern.compile("\\d{3} \\d{6}");

        return coincidePatron(numeroTelefonoConFormato);
    }
}
