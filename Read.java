
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Read extends Tuple{
    Token variable;
    SymbolTable ts;
    
    public Read(Token variable){
        super(0, 0);
        this.variable = variable;
    }

    //toString
    public String toString(){
        return "Read(" + variable + ")";
    }

    //execute
    @Override
    public int execute(SymbolTable ts){
        this.ts = ts;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            input = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Variable var = (Variable) ts.resolve(variable.getNombre());
        var.setValue(Float.parseFloat(input));
        return rlJump;
    }
}
