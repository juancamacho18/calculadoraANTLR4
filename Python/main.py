import sys
from antlr4 import *
from LabeledExprLexer import LabeledExprLexer
from LabeledExprParser import LabeledExprParser
from EvalVisitor import EvalVisitor

if __name__=='__main__':
    Input=FileStream(sys.argv[1])
    lexer=LabeledExprLexer(Input)
    tokens=CommonTokenStream(lexer)
    parser=LabeledExprParser(tokens)
    tree=parser.prog()

    Eval=EvalVisitor()
    Eval.visit(tree)
