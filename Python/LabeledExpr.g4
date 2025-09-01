grammar LabeledExpr;
options { caseInsensitive=true; }

prog:	stat+ ;

stat:	expr NEWLINE         	#printExpr
    |	ID '=' expr NEWLINE 	#assign
    |	'modo=' ID NEWLINE 	#Modo
    |	NEWLINE			#blank
    ;

expr:	expr op=('*'|'/') expr			#MulDiv
    |	expr op=('+'|'-') expr			#AddSub
    |	'Sqrt(' expr ')'			#Raiz
    |	op=('ln'|'log') '(' expr ')'		#Log
    |	op=('sin'|'cos'|'tan') '(' expr ')'  	#SinCosTan
    |	expr '!'				#Fact
    |	INT					#int
    |	ID					#id
    |	'(' expr ')'				#parens
    ;
    
//rules
MUL: '*' ;
DIV: '/' ;
ADD: '+' ;
SUB: '-' ;

ID: [a-z]+ ;      
INT: [0-9]+ ;                    
NEWLINE: [\r\n]+ ;             
WS: [ \t]+ -> skip ;
