// $ANTLR 2.7.5 (20050128): "../src/csheets/core/formula/compiler/MacrosCompiler.g" -> "MacrosParser.java"$
package csheets.core.formula.compiler;
import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;
/**
 * A parser that generates expressions from lists of lexical tokens.
 * @author Einar Pehrson
 */
public class MacrosParser extends antlr.LLkParser       implements MacrosParserTokenTypes
 {

protected MacrosParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public MacrosParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected MacrosParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public MacrosParser(TokenStream lexer) {
  this(lexer,1);
}

public MacrosParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

/**
 * The start rule for formula expressions.
 */
	public final void expression() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		res();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp1_AST = null;
		tmp1_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp1_AST);
		match(COLON);
		AST tmp2_AST = null;
		tmp2_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp2_AST);
		match(EQ);
		comparison();
		astFactory.addASTChild(currentAST, returnAST);
		match(Token.EOF_TYPE);
		expression_AST = (AST)currentAST.root;
		returnAST = expression_AST;
	}
	
	public final void res() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST res_AST = null;
		
		{
		switch ( LA(1)) {
		case LOCVAR:
		{
			AST tmp4_AST = null;
			tmp4_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp4_AST);
			match(LOCVAR);
			break;
		}
		case CELL_REF:
		{
			AST tmp5_AST = null;
			tmp5_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp5_AST);
			match(CELL_REF);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		res_AST = (AST)currentAST.root;
		returnAST = res_AST;
	}
	
	public final void comparison() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST comparison_AST = null;
		
		concatenation();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case EQ:
		case NEQ:
		case GT:
		case LT:
		case LTEQ:
		case GTEQ:
		{
			{
			switch ( LA(1)) {
			case EQ:
			{
				AST tmp6_AST = null;
				tmp6_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp6_AST);
				match(EQ);
				break;
			}
			case NEQ:
			{
				AST tmp7_AST = null;
				tmp7_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp7_AST);
				match(NEQ);
				break;
			}
			case GT:
			{
				AST tmp8_AST = null;
				tmp8_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp8_AST);
				match(GT);
				break;
			}
			case LT:
			{
				AST tmp9_AST = null;
				tmp9_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp9_AST);
				match(LT);
				break;
			}
			case LTEQ:
			{
				AST tmp10_AST = null;
				tmp10_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp10_AST);
				match(LTEQ);
				break;
			}
			case GTEQ:
			{
				AST tmp11_AST = null;
				tmp11_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp11_AST);
				match(GTEQ);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			concatenation();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case EOF:
		case RPAR:
		case SEMI:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		comparison_AST = (AST)currentAST.root;
		returnAST = comparison_AST;
	}
	
	public final void concatenation() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST concatenation_AST = null;
		
		arithmetic_lowest();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop9:
		do {
			if ((LA(1)==AMP)) {
				AST tmp12_AST = null;
				tmp12_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp12_AST);
				match(AMP);
				arithmetic_lowest();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop9;
			}
			
		} while (true);
		}
		concatenation_AST = (AST)currentAST.root;
		returnAST = concatenation_AST;
	}
	
	public final void arithmetic_lowest() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arithmetic_lowest_AST = null;
		
		arithmetic_low();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop13:
		do {
			if ((LA(1)==PLUS||LA(1)==MINUS)) {
				{
				switch ( LA(1)) {
				case PLUS:
				{
					AST tmp13_AST = null;
					tmp13_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp13_AST);
					match(PLUS);
					break;
				}
				case MINUS:
				{
					AST tmp14_AST = null;
					tmp14_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp14_AST);
					match(MINUS);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				arithmetic_low();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop13;
			}
			
		} while (true);
		}
		arithmetic_lowest_AST = (AST)currentAST.root;
		returnAST = arithmetic_lowest_AST;
	}
	
	public final void arithmetic_low() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arithmetic_low_AST = null;
		
		arithmetic_medium();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop17:
		do {
			if ((LA(1)==MULTI||LA(1)==DIV)) {
				{
				switch ( LA(1)) {
				case MULTI:
				{
					AST tmp15_AST = null;
					tmp15_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp15_AST);
					match(MULTI);
					break;
				}
				case DIV:
				{
					AST tmp16_AST = null;
					tmp16_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp16_AST);
					match(DIV);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				arithmetic_medium();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop17;
			}
			
		} while (true);
		}
		arithmetic_low_AST = (AST)currentAST.root;
		returnAST = arithmetic_low_AST;
	}
	
	public final void arithmetic_medium() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arithmetic_medium_AST = null;
		
		arithmetic_high();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case POWER:
		{
			AST tmp17_AST = null;
			tmp17_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp17_AST);
			match(POWER);
			arithmetic_high();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case EOF:
		case EQ:
		case NEQ:
		case GT:
		case LT:
		case LTEQ:
		case GTEQ:
		case AMP:
		case PLUS:
		case MINUS:
		case MULTI:
		case DIV:
		case RPAR:
		case SEMI:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		arithmetic_medium_AST = (AST)currentAST.root;
		returnAST = arithmetic_medium_AST;
	}
	
	public final void arithmetic_high() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arithmetic_high_AST = null;
		
		arithmetic_highest();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case PERCENT:
		{
			AST tmp18_AST = null;
			tmp18_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp18_AST);
			match(PERCENT);
			break;
		}
		case EOF:
		case EQ:
		case NEQ:
		case GT:
		case LT:
		case LTEQ:
		case GTEQ:
		case AMP:
		case PLUS:
		case MINUS:
		case MULTI:
		case DIV:
		case POWER:
		case RPAR:
		case SEMI:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		arithmetic_high_AST = (AST)currentAST.root;
		returnAST = arithmetic_high_AST;
	}
	
	public final void arithmetic_highest() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arithmetic_highest_AST = null;
		
		{
		switch ( LA(1)) {
		case MINUS:
		{
			AST tmp19_AST = null;
			tmp19_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp19_AST);
			match(MINUS);
			break;
		}
		case LOCVAR:
		case CELL_REF:
		case LPAR:
		case FUNCTION:
		case NAME:
		case NUMBER:
		case STRING:
		case EXE:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		atom();
		astFactory.addASTChild(currentAST, returnAST);
		arithmetic_highest_AST = (AST)currentAST.root;
		returnAST = arithmetic_highest_AST;
	}
	
	public final void atom() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST atom_AST = null;
		
		switch ( LA(1)) {
		case FUNCTION:
		{
			function_call();
			astFactory.addASTChild(currentAST, returnAST);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case LOCVAR:
		case CELL_REF:
		case NAME:
		{
			reference();
			astFactory.addASTChild(currentAST, returnAST);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case NUMBER:
		case STRING:
		{
			literal();
			astFactory.addASTChild(currentAST, returnAST);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case EXE:
		{
			execucao();
			astFactory.addASTChild(currentAST, returnAST);
			atom_AST = (AST)currentAST.root;
			break;
		}
		case LPAR:
		{
			match(LPAR);
			comparison();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAR);
			atom_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = atom_AST;
	}
	
	public final void function_call() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST function_call_AST = null;
		
		AST tmp22_AST = null;
		tmp22_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp22_AST);
		match(FUNCTION);
		{
		switch ( LA(1)) {
		case LOCVAR:
		case CELL_REF:
		case MINUS:
		case LPAR:
		case FUNCTION:
		case NAME:
		case NUMBER:
		case STRING:
		case EXE:
		{
			comparison();
			astFactory.addASTChild(currentAST, returnAST);
			{
			_loop28:
			do {
				if ((LA(1)==SEMI)) {
					match(SEMI);
					comparison();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop28;
				}
				
			} while (true);
			}
			break;
		}
		case RPAR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(RPAR);
		function_call_AST = (AST)currentAST.root;
		returnAST = function_call_AST;
	}
	
	public final void reference() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST reference_AST = null;
		
		switch ( LA(1)) {
		case CELL_REF:
		{
			AST tmp25_AST = null;
			tmp25_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp25_AST);
			match(CELL_REF);
			{
			switch ( LA(1)) {
			case COLON:
			{
				{
				AST tmp26_AST = null;
				tmp26_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp26_AST);
				match(COLON);
				}
				AST tmp27_AST = null;
				tmp27_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp27_AST);
				match(CELL_REF);
				break;
			}
			case EOF:
			case EQ:
			case NEQ:
			case GT:
			case LT:
			case LTEQ:
			case GTEQ:
			case AMP:
			case PLUS:
			case MINUS:
			case MULTI:
			case DIV:
			case POWER:
			case PERCENT:
			case RPAR:
			case SEMI:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			reference_AST = (AST)currentAST.root;
			break;
		}
		case NAME:
		{
			AST tmp28_AST = null;
			tmp28_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp28_AST);
			match(NAME);
			reference_AST = (AST)currentAST.root;
			break;
		}
		case LOCVAR:
		{
			AST tmp29_AST = null;
			tmp29_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp29_AST);
			match(LOCVAR);
			reference_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = reference_AST;
	}
	
	public final void literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST literal_AST = null;
		
		switch ( LA(1)) {
		case NUMBER:
		{
			AST tmp30_AST = null;
			tmp30_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp30_AST);
			match(NUMBER);
			literal_AST = (AST)currentAST.root;
			break;
		}
		case STRING:
		{
			AST tmp31_AST = null;
			tmp31_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp31_AST);
			match(STRING);
			literal_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = literal_AST;
	}
	
	public final void execucao() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST execucao_AST = null;
		
		AST tmp32_AST = null;
		tmp32_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp32_AST);
		match(EXE);
		AST tmp33_AST = null;
		tmp33_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp33_AST);
		match(TEXTO);
		execucao_AST = (AST)currentAST.root;
		returnAST = execucao_AST;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"COLON",
		"EQ",
		"LOCVAR",
		"CELL_REF",
		"NEQ",
		"GT",
		"LT",
		"LTEQ",
		"GTEQ",
		"AMP",
		"PLUS",
		"MINUS",
		"MULTI",
		"DIV",
		"POWER",
		"PERCENT",
		"LPAR",
		"RPAR",
		"FUNCTION",
		"SEMI",
		"NAME",
		"NUMBER",
		"STRING",
		"EXE",
		"TEXTO",
		"LETTER",
		"ALPHABETICAL",
		"QUOT",
		"DIGIT",
		"ABS",
		"EXCL",
		"COMMA",
		"WS"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	
	}
