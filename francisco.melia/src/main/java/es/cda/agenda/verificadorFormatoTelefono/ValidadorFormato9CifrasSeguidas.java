package es.cda.agenda.verificadorFormatoTelefono;

import java.util.regex.Pattern;

public class ValidadorFormato9CifrasSeguidas extends ValidadorFormatoTelefono
{
    public Boolean valida(String numeroTelefonoConFormato)
    {
        pattern = Pattern.compile("\\d{9}");

        return coincidePatron(numeroTelefonoConFormato);
    }
}
