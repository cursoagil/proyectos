package es.cda.agenda;

@SuppressWarnings("serial")
public class FormatoDeNumeroDeTelefonoIncorrectoException extends Exception
{
    public FormatoDeNumeroDeTelefonoIncorrectoException()
    {
        super("Formato de número de teléfono incorrecto");
    }
    
    public FormatoDeNumeroDeTelefonoIncorrectoException(String message)
    {
        super(message);
    }
    
}
