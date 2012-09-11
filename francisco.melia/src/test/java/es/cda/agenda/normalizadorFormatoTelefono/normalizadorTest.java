package es.cda.agenda.normalizadorFormatoTelefono;

import junit.framework.Assert;

import org.junit.Test;

import es.cda.agenda.normalizadorFormatoTelefono.NormalizadorFormatoTelefono;

public class normalizadorTest
{
    NormalizadorFormatoTelefono normalizadorTelefono = new NormalizadorFormatoTelefono();

    @Test
    public void seObtieneElMismoNumeroNormalizadoDelTelefonoConDisintosFormatos()
    {
        String telefonoNormalizado = "964252525";

        aseverarEquivalencia("964 252 525", telefonoNormalizado);

        aseverarEquivalencia("964 252525", telefonoNormalizado);

        aseverarEquivalencia("(964) 252525", telefonoNormalizado);

        aseverarEquivalencia("34 964252525", "34" + telefonoNormalizado);
    }

    public void aseverarEquivalencia(String telefonoSinNormalizar, String telefonoNormalizado)
    {
        Assert.assertEquals(telefonoNormalizado,
                normalizadorTelefono.normaliza(telefonoSinNormalizar));
    }
}
