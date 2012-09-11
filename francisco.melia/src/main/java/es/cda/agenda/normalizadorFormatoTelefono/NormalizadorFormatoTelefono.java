package es.cda.agenda.normalizadorFormatoTelefono;

public class NormalizadorFormatoTelefono
{
    public String normaliza(String numeroTelefonoConFormato)
    {
        return numeroTelefonoConFormato.replaceAll("\\D+", "");
    }
}
