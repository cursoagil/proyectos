package es.cda.agenda.verificadorFormatoTelefono;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ValidadorFormatoTelefono
{
    Pattern pattern;

    abstract Boolean valida(String numeroTelefonoConFormato);

    public Boolean coincidePatron(String numeroTelefonoConFormato)
    {
        Matcher matcher = pattern.matcher(numeroTelefonoConFormato);

        if (matcher.matches())
        {
            return true;
        }

        return false;
    }
}
