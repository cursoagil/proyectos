package es.cda.agenda.verificadorFormatoTelefono;

public class VerificadorFormatoTelefono
{

    ValidadorFormatoTelefono[] listaValidadoresFormato = { new ValidadorFormato9CifrasSeguidas(),
            new ValidadorFormatoGruposDe3Cifras(),
            new ValidadorFormatoGrupoDe3CifrasSeguidoDeGruposDe2(),
            new ValidadorFormatoGrupoDe3CifrasSeguidoDe6Cifras(),
            new ValidadorFormatoGrupoDe3CifrasEntreParentesisSeguidoDe6Cifras(),
            new ValidadorFormatoPrefijoInternacionalSeguidoDe9Cifras()};

    public Boolean verifica(String numeroTelefonoConFormato)
    {
        Boolean resultadoValidacion = false;

        if (esEntradaIncorrecta(numeroTelefonoConFormato))
        {
            return resultadoValidacion;
        }

        for (ValidadorFormatoTelefono validador : listaValidadoresFormato)
        {
            resultadoValidacion = resultadoValidacion || validador.valida(numeroTelefonoConFormato);
        }

        return resultadoValidacion;
    }

    private boolean esEntradaIncorrecta(String telefono)
    {
        return telefono == null || telefono.isEmpty();
    }
}
