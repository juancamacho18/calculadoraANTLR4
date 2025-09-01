import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Calc {
    public static void main(String[] args) {
        try {
            String inputFile=null;
            if (args.length>0) inputFile=args[0];
            InputStream is=(inputFile!=null)? new FileInputStream(inputFile) : System.in;

            CharStream input=CharStreams.fromStream(is);
            LabeledExprLexer lexer= new LabeledExprLexer(input);
            CommonTokenStream tokens= new CommonTokenStream(lexer);

            LabeledExprParser parser=new LabeledExprParser(tokens);
            ParseTree tree=parser.prog();
            EvalVisitor eval=new EvalVisitor();
            eval.visit(tree);
            //System.out.println(tree.toStringTree(parser));
        } catch (Exception e) {
            System.err.println("Error (test): "+ e);
        }
    }
}
