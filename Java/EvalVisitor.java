import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends LabeledExprBaseVisitor<Integer> {
    /** "memory" for our calculator; variable/value pairs go here */
    Map<String, Integer> memory=new HashMap<String, Integer>();
    private String mode="radianes"; 


    /** ID '=' expr NEWLINE */
    @Override
    public Integer visitAssign(LabeledExprParser.AssignContext ctx) {
        String id=ctx.ID().getText(); 
        int value=visit(ctx.expr());   
        memory.put(id, value); 
        return value;
       }

    /* 'mode=' ID NEWLINE */
    @Override
    public Integer visitModo(LabeledExprParser.ModoContext ctx) {
        String Mode=ctx.ID().getText();
        if (Mode.equals("grados") || Mode.equals("radianes")) {
            mode=Mode;
            System.out.println("modo "+mode);
        } else {
            System.out.println("Error, modo no valido");
        }
        return 0;
    }

    /* expr NEWLINE */
    @Override
    public Integer visitPrintExpr(LabeledExprParser.PrintExprContext ctx){
        Integer value=visit(ctx.expr());
        System.out.println(value);
        return 0;
    }

    /*INT */
    @Override
    public Integer visitInt(LabeledExprParser.IntContext ctx){
        return Integer.valueOf(ctx.INT().getText());
    }

    /*ID */
    @Override
    public Integer visitId(LabeledExprParser.IdContext ctx){
        String id=ctx.ID().getText();
        if (memory.containsKey(id)) return memory.get(id);
        return 0;
    }

    /*expr op=('+'|'-') expr */
    @Override
    public Integer visitMulDiv(LabeledExprParser.MulDivContext ctx){
        int left=visit(ctx.expr(0));
        int right=visit(ctx.expr(1));
        if (ctx.op.getType()==LabeledExprParser.MUL) return left*right;
        return left/right;
    }

    /*expr op=('+'|'-') expr */
    @Override
    public Integer visitAddSub(LabeledExprParser.AddSubContext ctx){
        int left=visit(ctx.expr(0));
        int right=visit(ctx.expr(1));
        if (ctx.op.getType()==LabeledExprParser.ADD) return left+right;
        return left-right;
    }

    /* 'Sqrt(' expr ')'*/
    @Override
    public Integer visitRaiz(LabeledExprParser.RaizContext ctx){
        int value=visit(ctx.expr());
        return (int)Math.sqrt(value);
    }

    /* op=('ln'|'log') '(' expr ')'*/
    @Override
    public Integer visitLog(LabeledExprParser.LogContext ctx){
        int value=visit(ctx.expr());
        String op=ctx.op.getText(); 
        double result;

        if(op.equals("ln")){
            result=Math.log(value);      
        }else{
            result=Math.log10(value);    
        }
        return (int) result;
    }

    /* op=('sin'|'cos'|'tan') '(' expr ')' */
    @Override
    public Integer visitSinCosTan(LabeledExprParser.SinCosTanContext ctx){
        double value;
        String op=ctx.op.getText(); 
        double result;

        if(mode.equals("grados")){
            value=Math.toRadians(visit(ctx.expr()));
        }else{
            value=visit(ctx.expr());
        }

        if(op.equals("sin")){
            result=Math.sin(value);      
        }else if(op.equals("cos")) {
            result=Math.cos(value);    
        }else{
            result=Math.tan(value);
        }
        return (int)result;
    }

    /* expr '!' */
    @Override
    public Integer visitFact(LabeledExprParser.FactContext ctx) {
        int value=visit(ctx.expr());
        return factorial(value);
    }

    /*factorial */
    private int factorial(int n){
        if(n<0){
        throw new RuntimeException("Error, no valores negativos");
        }else if(n<=1){
            return 1;
        }else{
            return n*factorial(n-1);
        }
    }

    /* '(' expr')' */
    @Override
    public Integer visitParens(LabeledExprParser.ParensContext ctx){
        return visit(ctx.expr());
    }
}

