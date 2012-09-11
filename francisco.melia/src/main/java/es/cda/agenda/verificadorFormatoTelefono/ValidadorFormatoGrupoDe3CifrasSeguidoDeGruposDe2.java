package es.cda.agenda.verificadorFormatoTelefono;

import java.util.regex.Pattern;

public class ValidadorFormatoGrupoDe3CifrasSeguidoDeGruposDe2 extends ValidadorFormatoTelefono
{
    public Boolean valida(String numeroTelefonoConFormato)
    {
        pattern = Pattern.compile("\\d{3} \\d{2} \\d{2} \\d{2}");

        return coincidePatron(numeroTelefonoConFormato);
    }
}
