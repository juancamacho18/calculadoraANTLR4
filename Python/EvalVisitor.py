import math
from LabeledExprVisitor import LabeledExprVisitor

class EvalVisitor(LabeledExprVisitor):
    #"memory"
    def __init__(self):
        self.memory={}
        self.mode ="radianes"

    #ID '=' expr NEWLINE
    def visitAssign(self, ctx):
        Id=ctx.ID().getText()
        value = self.visit(ctx.expr())
        self.memory[Id]=value
        return value

    #'mode=' ID NEWLINE 
    def visitModo(self, ctx):
        Mode=ctx.ID().getText().lower()
        if Mode in ("grados", "radianes"):
            self.mode=Mode
            print(f"modo {self.mode}")
        else:
            print(f"Error, modo no valido")
        return 0

    #expr NEWLINE 
    def visitPrintExpr(self, ctx):
        value=self.visit(ctx.expr())
        print(value)
        return value

    #INT
    def visitInt(self, ctx):
        return int(ctx.INT().getText())

    #ID
    def visitId(self, ctx):
        Id=ctx.ID().getText()
        return self.memory.get(Id, 0.0)

    #expr op=('*'|'/') expr
    def visitMulDiv(self, ctx):
        left=self.visit(ctx.expr(0))
        right=self.visit(ctx.expr(1))
        if ctx.op.text=='*':
            return left*right
        else:
            return left/right

    #expr op=('+'|'-') expr
    def visitAddSub(self, ctx):
        left=self.visit(ctx.expr(0))
        right=self.visit(ctx.expr(1))
        if ctx.op.text=='+':
            return left+right
        else:
            return left-right

    #'Sqrt(' expr ')'
    def visitRaiz(self, ctx):
        value=self.visit(ctx.expr())
        return math.sqrt(value)

    #op=('ln'|'log') '(' expr ')'
    def visitLog(self, ctx):
        value=self.visit(ctx.expr())
        op=ctx.op.text.lower()
        if op=='ln':
            return math.log(value)
        else:
            return math.log10(value)

    #op=('sin'|'cos'|'tan') '(' expr ')'
    def visitSinCosTan(self, ctx):
        value=math.radians(self.visit(ctx.expr())) if (self.mode=="grados") else self.visit(ctx.expr())
        op=ctx.op.text.lower()

        if op=='sin':
            return math.sin(value)
        elif op=='cos':
            return math.cos(value)
        else:
            return math.tan(value)

    #expr '!'
    def visitFact(self, ctx):
        value=int(self.visit(ctx.expr()))
        return self._factorial(value)

    #factorial
    def _factorial(self, v):
        if(v<0):
            raise ValueError("Error, no valores negativos")
        elif(v<=1):
            return 1
        else:
            return v*self._factorial(v-1)
    

    #'(' expr')'
    def visitParens(self, ctx):
        return self.visit(ctx.expr())

