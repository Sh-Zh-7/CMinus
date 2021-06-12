grammar CMinus;

@lexer::members {
    public int _tokenErrors = 0;

	@Override
	public void notifyListeners(LexerNoViableAltException e) {
		++this._tokenErrors;
		super.notifyListeners(e);
	}

	public int getNumberOfTokenErrors() {
		return this._tokenErrors;
	}
}

// Hign level definition
program: extDefList;
extDefList
    : extDef extDefList
    | EOF
    ;
extDef
    : specifier extDecList SEMI
    | specifier SEMI
    | specifier funDec compSt
    ;
extDecList
    : varDec
    | varDec COMMA extDecList
    ;

// Specifiers
specifier
    : TYPE
    | structSpecifier
    ;
structSpecifier
    : STRUCT optTag LC defList RC
    | STRUCT tag
    ;
optTag
    : ID
    |
    ;
tag: ID;

// Declartors
varDec
    : ID
    | varDec LB INT RB
    ;
funDec
    : ID LP varList RP
    | ID LP RP
    ;
varList
    : paramDec COMMA varList
    | paramDec
    ;
paramDec: specifier varDec;

// Statements
compSt: LC defList stmtList RC;
stmtList
    : stmt stmtList
    |
    ;
stmt
    : exp SEMI
    | compSt
    | RETURN exp SEMI
    | IF LP exp RP stmt
    | IF LP exp RP stmt ELSE stmt
    | WHILE LP exp RP stmt
    ;

// Local definitions
defList
    : def defList
    |
    ;
def: specifier decList SEMI;
decList
    : dec
    | dec COMMA decList
    ;
dec
    : varDec
    | varDec ASSIGNOP exp
    ;

// Expressions
exp
    // Heighest priority
    : LP exp RP
    // 1st priority
    |
    exp
    ( LP args RP
    | LP RP
    | (LB exp RB)
    | DOT ID
    )
    // 2nd priority
    | <assoc=right> (MINUS | NOT) exp
    // 3rd priority
    | exp (STAR | DIV) exp
    // 4th priority
    | exp (PLUS | MINUS) exp
    // 5th priority
    | exp RELOP exp
    // 6th priority
    | exp AND exp
    // 7th priority
    | exp OR exp
    // 8th priority
    | <assoc=right> exp ASSIGNOP exp
    // others
    | ID
    | INT
    | FLOAT
    ;
args
    : exp COMMA args
    | exp
    ;

// Tokens
INT: '0' | [1-9][0-9]*;
FLOAT: INT '.' INT;
SEMI: ';';
COMMA: ',';
ASSIGNOP: '=';
RELOP: '>' | '<' | '>=' | '<=' | '==' | '!=';
PLUS: '+';
MINUS: '-';
STAR: '*';
DIV: '/';
AND: '&&';
OR: '||';
DOT: '.';
NOT: '!';
TYPE: 'int' | 'float';
LP: '(';
RP: ')';
LB: '[';
RB: ']';
LC: '{';
RC: '}';
STRUCT: 'struct';
RETURN: 'return';
IF: 'if';
ELSE: 'else';
WHILE: 'while';
ID: NONE_DIGIT (NONE_DIGIT | DIGIT)*;
fragment NONE_DIGIT: [a-zA-Z_];
fragment DIGIT: [0-9];

WS: [ \t\n\r] -> skip;
SL_COMMENT: '//' ~[\r\n]* -> skip;
ML_COMMENT: '/*' .*? '*/' -> skip;