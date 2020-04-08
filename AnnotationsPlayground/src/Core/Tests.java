package Core;

public class Tests implements Test{
    @ITest
    public int intTest() {
	return 69;
    }
    @ITest
    public String stringTest() {
	return "Alea jacta est";
    }
    @ITest
    public Auxillary auxTest() {
	return new Auxillary("Auxillary class");
    }
    
}

