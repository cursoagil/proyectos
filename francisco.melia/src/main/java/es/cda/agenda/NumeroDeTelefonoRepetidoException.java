package es.cda.agenda;

@SuppressWarnings("serial")
public class NumeroDeTelefonoRepetidoException extends Exception
{
    public NumeroDeTelefonoRepetidoException()
    {
        super("Este número de teléfono está repetido");
    }
    
    public NumeroDeTelefonoRepetidoException(String message)
    {
        super(message);
    }
    
}
