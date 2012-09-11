package es.cda.agenda;

@SuppressWarnings("serial")
public class NumeroDeTelefonoInexistenteException extends Exception
{
    public NumeroDeTelefonoInexistenteException()
    {
        super("Este número de teléfono no existe");
    }
    
    public NumeroDeTelefonoInexistenteException(String message)
    {
        super(message);
    }
    
}
