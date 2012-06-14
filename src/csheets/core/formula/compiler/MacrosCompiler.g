header {package csheets.core.formula.compiler;}

{/**
 * A parser that generates expressions from lists of lexical tokens.
 * @author Einar Pehrson
 */}
class MacrosParser extends Parser;
options {
	buildAST = true;
	defaultErrorHandler = false;
}

/**
 * The start rule for formula expressions.
 */
/* content
	: ( expression | literal ) EOF
	; */

expression
	: res COLON EQ comparison EOF!
	;

res
	: (LOCVAR
        | CELL_REF)
	;

comparison
	: concatenation
		( ( EQ^ | NEQ^ | GT^ | LT^ | LTEQ^ | GTEQ^ ) concatenation )?
	;

concatenation
	: arithmetic_lowest
		( AMP^ arithmetic_lowest )*
	;

arithmetic_lowest
	:	arithmetic_low
		( ( PLUS^ | MINUS^ ) arithmetic_low )*
	;

arithmetic_low
	:	arithmetic_medium
		( ( MULTI^ | DIV^ ) arithmetic_medium )*
	;

arithmetic_medium
	:	arithmetic_high
		( POWER^ arithmetic_high )?
	;

arithmetic_high
	:	arithmetic_highest ( PERCENT^ )?
	;

arithmetic_highest
	:	( MINUS^ )? atom
	;

atom
	:	function_call
	|	reference
	|	literal
	|	LPAR! comparison RPAR!
	;

function_call
	:	FUNCTION^ 
		( comparison ( SEMI! comparison )* )?
		RPAR!
	;

reference
	:	CELL_REF
		( ( COLON^ ) CELL_REF )?
	|	NAME
        |       LOCVAR
	;

literal
	:	NUMBER
	|	STRING
	;

{import csheets.core.formula.lang.Language;

/**
 * A lexer that splits a string into a list of lexical tokens.
 * @author Einar Pehrson
 */
@SuppressWarnings("all")}
class MacrosLexer extends Lexer;

options {
	k = 4;
	caseSensitive = false;
	caseSensitiveLiterals = false;
}

/* Function calls, named ranges and cell references */
LETTER: ('a'..'z') ;

ALPHABETICAL
	:	( ( LETTER )+ LPAR ) => ( LETTER )+ LPAR! {
			try {
				Language.getInstance().getFunction(#getText());
				$setType(FUNCTION);
			} catch (Exception ex) {
				throw new RecognitionException(ex.toString());
			}
		}
	|	/* ( LETTER ( LETTER | NUMBER )* EXCL )? */


		LETTER ( LETTER )? ( DIGIT )+ {
			$setType(CELL_REF);
		}
	;

LOCVAR
	:	( ABS ) ( LETTER )+ (NUMBER)?
	;


/* String literals, i.e. anything inside the delimiters */
STRING
	:	QUOT!
		(options {greedy=false;}:.)*
		QUOT!
	;
protected QUOT: '"';

/* Numeric literals */
NUMBER: ( DIGIT )+ ( COMMA ( DIGIT )+ )? ;
protected DIGIT : '0'..'9' ;

/* Comparison operators */
EQ		: "=" ;
NEQ		: "<>" ;
LTEQ	: "<=" ;
GTEQ	: ">=" ;
GT		: '>' ;
LT		: '<' ;

/* Text operators */
AMP		: '&' ;

/* Arithmetic operators */
PLUS	: '+' ;
MINUS	: '-' ;
MULTI	: '*' ;
DIV		: '/' ;
POWER	: '^' ;
PERCENT : '%' ;

/* Reference operators */
protected ABS : '$' ;
protected EXCL:  '!'  ;
COLON	: ':' ;

/* Miscellaneous operators */
COMMA	: ',' ;
SEMI	: ';' ;
LPAR	: '(' ;
RPAR	: ')' ;

/* White-space (ignored) */
WS: ( ' '
	| '\r' '\n'
	| '\n'
	| '\t'
	)
	{$setType(Token.SKIP);}
	;