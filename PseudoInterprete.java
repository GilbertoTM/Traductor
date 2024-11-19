
import java.util.ArrayList;

public class PseudoInterprete {
    SymbolTable ts;

    public PseudoInterprete(SymbolTable ts) {
        this.ts = ts;
    }

    public void interpret(ArrayList<Tuple> tuples) {
        int i = 0;
        Tuple t = tuples.get(i);

        do { 
            i = t.execute(ts);
            t = tuples.get(i);
        } while (!(t instanceof EndProgram));
    }
}
