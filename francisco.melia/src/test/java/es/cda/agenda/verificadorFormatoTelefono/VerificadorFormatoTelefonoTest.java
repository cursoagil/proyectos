package es.cda.agenda.verificadorFormatoTelefono;

import junit.framework.Assert;

import org.junit.Test;

import es.cda.agenda.verificadorFormatoTelefono.VerificadorFormatoTelefono;

public class VerificadorFormatoTelefonoTest
{
    VerificadorFormatoTelefono verificadorFormato = new VerificadorFormatoTelefono();

    @Test
    public void verificadorTelefonosTieneEnCuentaEntradaNulaOVacia()
    {
        Assert.assertFalse(verificadorFormato.verifica(null));
        Assert.assertFalse(verificadorFormato.verifica(""));
    }

    @Test
    public void verificadorTelefonosTieneEnCuentaFormatosCorrectos()
    {
        Assert.assertTrue(verificadorFormato.verifica("900 900 900"));
        Assert.assertTrue(verificadorFormato.verifica("900 90 09 00"));
        Assert.assertTrue(verificadorFormato.verifica("900 900900"));
        Assert.assertTrue(verificadorFormato.verifica("(900) 900900"));
        Assert.assertTrue(verificadorFormato.verifica("+34 900900900"));
    }
    
    @Test
    public void verificadorTelefonosTieneEnCuentaFormatosIncorrectos()
    {
        Assert.assertFalse(verificadorFormato.verifica("1900900900"));
        Assert.assertFalse(verificadorFormato.verifica("190 09 009 00"));
        Assert.assertFalse(verificadorFormato.verifica("+1903 0900900"));
    }
}
