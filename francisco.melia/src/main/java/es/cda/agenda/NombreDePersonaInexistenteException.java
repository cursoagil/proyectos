package es.cda.agenda;

@SuppressWarnings("serial")
public class NombreDePersonaInexistenteException extends Exception
{
    public NombreDePersonaInexistenteException()
    {
        super("Este número de teléfono no existe");
    }
    
    public NombreDePersonaInexistenteException(String message)
    {
        super(message);
    }
    
}
