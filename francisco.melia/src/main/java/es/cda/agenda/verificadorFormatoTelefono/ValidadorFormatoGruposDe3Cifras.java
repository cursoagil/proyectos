package es.cda.agenda.verificadorFormatoTelefono;

import java.util.regex.Pattern;

public class ValidadorFormatoGruposDe3Cifras extends ValidadorFormatoTelefono
{
    public Boolean valida(String tnumeroTelefonoConFormato)
    {
        pattern = Pattern.compile("\\d{3} \\d{3} \\d{3}");

        return coincidePatron(tnumeroTelefonoConFormato);
    }
}
