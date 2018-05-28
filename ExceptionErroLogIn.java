/**
 * Exception a ser utilizada em varias situaçoes de erros no log in
 *
 * 
 */
public class ExceptionErroLogIn extends Exception
{
    /**
     * Constructor da classe ExceptionErroLogIn
     */
    public ExceptionErroLogIn(String erro)
    {
        super(erro);
    }
}
