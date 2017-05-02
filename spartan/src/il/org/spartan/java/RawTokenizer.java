/* The following code was generated by JFlex 1.4.3 on 10/14/12 10:56 AM */
/** A general purpose Java tokenizer,
 * @author Yossi Gil
 * @since 2007/04/02 */
package il.org.spartan.java;

import static il.org.spartan.java.Token.*;

@SuppressWarnings("all")
/** This class is a scanner generated by
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3 on 10/14/12 10:56 AM from the
 * specification file
 * <tt>/home/yogi/Workspace/Services/src/il/ac/technion/cs/ssdl/java/Tokenizer.flex</tt> */
public class RawTokenizer {
  /** This character denotes the end of file */
  public static final int YYEOF = -1;
  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;
  /** lexical states */
  public static final int SCAN_LINE_COMMENT = 10;
  public static final int BLOCK_EOLN = 16;
  public static final int DOC_EOLN = 18;
  public static final int RESET = 2;
  public static final int SCAN_STRING_LITERAL = 6;
  public static final int SCAN_BLOCK_COMMENT = 14;
  public static final int YYINITIAL = 0;
  public static final int SCAN_DOC_COMMENT = 12;
  public static final int SCAN_CHAR_LITERAL = 8;
  public static final int SCAN_CODE = 4;
  /** ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l at the
   * beginning of a line l is of the form l = 2*k, k a non negative integer */
  private static final int ZZ_LEXSTATE[] = { 0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8 };
  /** Translates characters to character classes */
  private static final String ZZ_CMAP_PACKED = "\11\5\1\3\1\1\1\0\1\3\1\2\16\5\4\0\1\3\1\46" + "\1\20\1\0\1\4\1\56\1\52\1\21\1\33\1\34\1\17\1\54"
      + "\1\42\1\15\1\13\1\16\1\6\7\12\2\7\1\51\1\41\1\45" + "\1\43\1\44\1\50\1\22\3\11\1\61\1\14\1\60\5\4\1\57"
      + "\13\4\1\10\2\4\1\37\1\62\1\40\1\55\1\4\1\0\1\31" + "\1\11\1\32\1\61\1\26\1\30\2\4\1\23\2\4\1\57\1\4"
      + "\1\24\3\4\1\27\1\4\1\25\3\4\1\10\2\4\1\35\1\53" + "\1\36\1\47\41\5\2\0\4\4\4\0\1\4\2\0\1\5\7\0"
      + "\1\4\4\0\1\4\5\0\27\4\1\0\37\4\1\0\u013f\4\31\0" + "\162\4\4\0\14\4\16\0\5\4\11\0\1\4\21\0\130\5\5\0"
      + "\23\5\12\0\1\4\13\0\1\4\1\0\3\4\1\0\1\4\1\0" + "\24\4\1\0\54\4\1\0\46\4\1\0\5\4\4\0\202\4\1\0"
      + "\4\5\3\0\105\4\1\0\46\4\2\0\2\4\6\0\20\4\41\0" + "\46\4\2\0\1\4\7\0\47\4\11\0\21\5\1\0\27\5\1\0"
      + "\3\5\1\0\1\5\1\0\2\5\1\0\1\5\13\0\33\4\5\0" + "\3\4\15\0\4\5\14\0\6\5\13\0\32\4\5\0\13\4\16\5"
      + "\7\0\12\5\4\0\2\4\1\5\143\4\1\0\1\4\10\5\1\0" + "\6\5\2\4\2\5\1\0\4\5\2\4\12\5\3\4\2\0\1\4" + "\17\0\1\5\1\4\1\5\36\4\33\5\2\0\3\4\60\0\46\4"
      + "\13\5\1\4\u014f\0\3\5\66\4\2\0\1\5\1\4\20\5\2\0" + "\1\4\4\5\3\0\12\4\2\5\2\0\12\5\21\0\3\5\1\0"
      + "\10\4\2\0\2\4\2\0\26\4\1\0\7\4\1\0\1\4\3\0" + "\4\4\2\0\1\5\1\4\7\5\2\0\2\5\2\0\3\5\11\0" + "\1\5\4\0\2\4\1\0\3\4\2\5\2\0\12\5\4\4\15\0"
      + "\3\5\1\0\6\4\4\0\2\4\2\0\26\4\1\0\7\4\1\0" + "\2\4\1\0\2\4\1\0\2\4\2\0\1\5\1\0\5\5\4\0" + "\2\5\2\0\3\5\13\0\4\4\1\0\1\4\7\0\14\5\3\4"
      + "\14\0\3\5\1\0\11\4\1\0\3\4\1\0\26\4\1\0\7\4" + "\1\0\2\4\1\0\5\4\2\0\1\5\1\4\10\5\1\0\3\5" + "\1\0\3\5\2\0\1\4\17\0\2\4\2\5\2\0\12\5\1\0"
      + "\1\4\17\0\3\5\1\0\10\4\2\0\2\4\2\0\26\4\1\0" + "\7\4\1\0\2\4\1\0\5\4\2\0\1\5\1\4\6\5\3\0" + "\2\5\2\0\3\5\10\0\2\5\4\0\2\4\1\0\3\4\4\0"
      + "\12\5\1\0\1\4\20\0\1\5\1\4\1\0\6\4\3\0\3\4" + "\1\0\4\4\3\0\2\4\1\0\1\4\1\0\2\4\3\0\2\4" + "\3\0\3\4\3\0\10\4\1\0\3\4\4\0\5\5\3\0\3\5"
      + "\1\0\4\5\11\0\1\5\17\0\11\5\11\0\1\4\7\0\3\5" + "\1\0\10\4\1\0\3\4\1\0\27\4\1\0\12\4\1\0\5\4" + "\4\0\7\5\1\0\3\5\1\0\4\5\7\0\2\5\11\0\2\4"
      + "\4\0\12\5\22\0\2\5\1\0\10\4\1\0\3\4\1\0\27\4" + "\1\0\12\4\1\0\5\4\2\0\1\5\1\4\7\5\1\0\3\5" + "\1\0\4\5\7\0\2\5\7\0\1\4\1\0\2\4\4\0\12\5"
      + "\22\0\2\5\1\0\10\4\1\0\3\4\1\0\27\4\1\0\20\4" + "\4\0\6\5\2\0\3\5\1\0\4\5\11\0\1\5\10\0\2\4"
      + "\4\0\12\5\22\0\2\5\1\0\22\4\3\0\30\4\1\0\11\4" + "\1\0\1\4\2\0\7\4\3\0\1\5\4\0\6\5\1\0\1\5" + "\1\0\10\5\22\0\2\5\15\0\60\4\1\5\2\4\7\5\4\0"
      + "\10\4\10\5\1\0\12\5\47\0\2\4\1\0\1\4\2\0\2\4" + "\1\0\1\4\2\0\1\4\6\0\4\4\1\0\7\4\1\0\3\4" + "\1\0\1\4\1\0\1\4\2\0\2\4\1\0\4\4\1\5\2\4"
      + "\6\5\1\0\2\5\1\4\2\0\5\4\1\0\1\4\1\0\6\5" + "\2\0\12\5\2\0\2\4\42\0\1\4\27\0\2\5\6\0\12\5" + "\13\0\1\5\1\0\1\5\1\0\1\5\4\0\2\5\10\4\1\0"
      + "\42\4\6\0\24\5\1\0\2\5\4\4\4\0\10\5\1\0\44\5" + "\11\0\1\5\71\0\42\4\1\0\5\4\1\0\2\4\1\0\7\5"
      + "\3\0\4\5\6\0\12\5\6\0\6\4\4\5\106\0\46\4\12\0" + "\51\4\7\0\132\4\5\0\104\4\5\0\122\4\6\0\7\4\1\0"
      + "\77\4\1\0\1\4\1\0\4\4\2\0\7\4\1\0\1\4\1\0" + "\4\4\2\0\47\4\1\0\1\4\1\0\4\4\2\0\37\4\1\0" + "\1\4\1\0\4\4\2\0\7\4\1\0\1\4\1\0\4\4\2\0"
      + "\7\4\1\0\7\4\1\0\27\4\1\0\37\4\1\0\1\4\1\0" + "\4\4\2\0\7\4\1\0\47\4\1\0\23\4\16\0\11\5\56\0"
      + "\125\4\14\0\u026c\4\2\0\10\4\12\0\32\4\5\0\113\4\3\0" + "\3\4\17\0\15\4\1\0\4\4\3\5\13\0\22\4\3\5\13\0"
      + "\22\4\2\5\14\0\15\4\1\0\3\4\1\0\2\5\14\0\64\4" + "\40\5\3\0\1\4\3\0\2\4\1\5\2\0\12\5\41\0\3\5"
      + "\2\0\12\5\6\0\130\4\10\0\51\4\1\5\126\0\35\4\3\0" + "\14\5\4\0\14\5\12\0\12\5\36\4\2\0\5\4\u038b\0\154\4"
      + "\224\0\234\4\4\0\132\4\6\0\26\4\2\0\6\4\2\0\46\4" + "\2\0\6\4\2\0\10\4\1\0\1\4\1\0\1\4\1\0\1\4"
      + "\1\0\37\4\2\0\65\4\1\0\7\4\1\0\1\4\3\0\3\4" + "\1\0\7\4\3\0\4\4\2\0\6\4\4\0\15\4\5\0\3\4" + "\1\0\7\4\17\0\4\5\32\0\5\5\20\0\2\4\23\0\1\4"
      + "\13\0\4\5\6\0\6\5\1\0\1\4\15\0\1\4\40\0\22\4" + "\36\0\15\5\4\0\1\5\3\0\6\5\27\0\1\4\4\0\1\4" + "\2\0\12\4\1\0\1\4\3\0\5\4\6\0\1\4\1\0\1\4"
      + "\1\0\1\4\1\0\4\4\1\0\3\4\1\0\7\4\3\0\3\4" + "\5\0\5\4\26\0\44\4\u0e81\0\3\4\31\0\11\4\6\5\1\0" + "\5\4\2\0\5\4\4\0\126\4\2\0\2\5\2\0\3\4\1\0"
      + "\137\4\5\0\50\4\4\0\136\4\21\0\30\4\70\0\20\4\u0200\0" + "\u19b6\4\112\0\u51a6\4\132\0\u048d\4\u0773\0\u2ba4\4\u215c\0\u012e\4\2\0"
      + "\73\4\225\0\7\4\14\0\5\4\5\0\1\4\1\5\12\4\1\0" + "\15\4\1\0\5\4\1\0\1\4\1\0\2\4\1\0\2\4\1\0"
      + "\154\4\41\0\u016b\4\22\0\100\4\2\0\66\4\50\0\15\4\3\0" + "\20\5\20\0\4\5\17\0\2\4\30\0\3\4\31\0\1\4\6\0"
      + "\5\4\1\0\207\4\2\0\1\5\4\0\1\4\13\0\12\5\7\0" + "\32\4\4\0\1\4\1\0\32\4\12\0\132\4\3\0\6\4\2\0" + "\6\4\2\0\6\4\2\0\3\4\3\0\2\4\3\0\2\4\22\0"
      + "\3\5\4\0";
  /** Translates characters to character classes */
  private static final char[] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);
  /** Translates DFA states to action switch labels. */
  private static final int[] ZZ_ACTION = zzUnpackAction();
  private static final String ZZ_ACTION_PACKED_0 = "\11\0\2\1\1\2\2\3\1\4\1\5\2\6\1\7" + "\1\10\1\11\1\12\1\13\1\14\1\2\1\15\1\16"
      + "\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26" + "\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\36" + "\1\37\1\40\1\41\2\42\1\43\1\41\2\44\1\45"
      + "\1\41\2\46\2\47\1\41\2\50\1\41\2\51\2\52" + "\1\6\2\0\1\53\1\0\1\54\1\6\1\53\1\55" + "\1\56\1\57\1\60\1\61\1\62\2\63\1\64\1\65"
      + "\1\66\1\67\1\70\1\71\1\72\1\73\1\74\1\75" + "\1\76\1\77\1\100\1\101\1\102\1\103\1\6\1\53" + "\1\0\1\104\1\63\1\105\1\106\1\107\1\110\1\63"
      + "\1\111\5\63\1\112";
  /** Translates a state to a row index in the transition table */
  private static final int[] ZZ_ROWMAP = zzUnpackRowMap();
  private static final String ZZ_ROWMAP_PACKED_0 = "\0\0\0\63\0\146\0\231\0\314\0\377\0\u0132\0\u0165"
      + "\0\u0198\0\u01cb\0\u01fe\0\u01cb\0\u01cb\0\u0231\0\u01cb\0\u0264" + "\0\u0297\0\u02ca\0\u02fd\0\u0330\0\u0363\0\u0396\0\u01cb\0\u01cb"
      + "\0\u03c9\0\u01cb\0\u01cb\0\u01cb\0\u01cb\0\u01cb\0\u01cb\0\u01cb" + "\0\u01cb\0\u03fc\0\u042f\0\u0462\0\u0495\0\u01cb\0\u01cb\0\u01cb"
      + "\0\u04c8\0\u04fb\0\u052e\0\u0561\0\u0594\0\u01cb\0\u01cb\0\u05c7" + "\0\u01cb\0\u05fa\0\u01cb\0\u062d\0\u01cb\0\u0660\0\u01cb\0\u0693"
      + "\0\u01cb\0\u06c6\0\u06f9\0\u01cb\0\u072c\0\u075f\0\u01cb\0\u0792" + "\0\u01cb\0\u07c5\0\u07f8\0\u082b\0\u085e\0\u0891\0\u08c4\0\u01cb"
      + "\0\u01cb\0\u01cb\0\u01cb\0\u01cb\0\u01cb\0\u08f7\0\u01cb\0\u01cb" + "\0\u092a\0\u095d\0\u01cb\0\u01cb\0\u0990\0\u01cb\0\u09c3\0\u01cb"
      + "\0\u01cb\0\u01cb\0\u01cb\0\u01cb\0\u01cb\0\u01cb\0\u01cb\0\u01cb" + "\0\u01cb\0\u01cb\0\u09f6\0\u0a29\0\u0a5c\0\u0a8f\0\u0ac2\0\u01cb"
      + "\0\u0af5\0\u01cb\0\u01cb\0\u0b28\0\u01cb\0\u0b5b\0\u0b8e\0\u0bc1" + "\0\u0bf4\0\u0c27\0\u092a";
  /** The transition table of the DFA */
  private static final int[] ZZ_TRANS = zzUnpackTrans();
  private static final String ZZ_TRANS_PACKED_0 = "\2\12\1\13\60\12\1\14\1\15\1\16\1\17\1\20" + "\1\14\1\21\1\22\2\20\1\22\1\23\1\20\1\24"
      + "\1\25\1\26\1\27\1\30\1\31\10\20\1\32\1\33" + "\1\34\1\35\1\36\1\37\1\40\1\41\1\42\1\43" + "\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53"
      + "\1\54\1\55\3\20\1\14\1\56\1\57\1\60\15\56" + "\1\61\41\56\1\62\1\56\1\63\1\64\16\56\1\65" + "\40\56\1\66\1\56\1\67\1\70\61\56\1\71\1\72"
      + "\14\56\1\73\44\56\1\74\1\75\14\56\1\76\43\56" + "\1\0\1\77\1\100\61\0\1\101\1\102\144\0\1\12" + "\62\0\1\15\65\0\7\20\1\0\1\20\6\0\10\20"
      + "\24\0\3\20\7\0\1\103\1\104\1\105\1\0\1\103" + "\1\106\1\107\11\0\1\107\1\0\1\110\26\0\1\111" + "\1\110\1\112\7\0\2\22\2\0\1\22\1\106\1\107"
      + "\11\0\1\107\1\0\1\110\26\0\1\111\1\110\1\112" + "\7\0\2\106\2\0\1\106\65\0\1\113\25\0\1\114" + "\35\0\1\115\1\116\23\0\1\117\62\0\1\120\23\0"
      + "\1\121\3\0\2\121\2\0\1\121\6\0\1\122\7\121" + "\24\0\3\121\44\0\1\123\62\0\1\124\1\125\61\0" + "\1\126\1\0\1\127\60\0\1\130\62\0\1\131\6\0"
      + "\1\132\53\0\1\133\7\0\1\134\52\0\1\135\10\0" + "\1\136\51\0\1\137\62\0\1\140\20\0\1\57\101\0" + "\1\56\41\0\1\56\1\0\1\63\102\0\1\56\40\0"
      + "\1\56\1\0\1\67\62\0\1\71\77\0\1\141\45\0" + "\1\74\77\0\1\142\45\0\1\77\62\0\1\101\67\0" + "\1\103\1\104\2\0\1\103\1\106\43\0\1\111\11\0"
      + "\2\104\2\0\1\104\1\106\55\0\2\143\1\0\2\143" + "\1\0\1\143\11\0\1\143\1\0\3\143\25\0\2\143" + "\7\0\2\106\2\0\1\106\1\0\1\107\11\0\1\107"
      + "\1\0\1\110\27\0\1\110\1\112\7\0\2\144\2\0" + "\1\144\2\0\1\145\36\0\1\145\25\0\1\146\47\0" + "\7\121\1\0\1\121\6\0\10\121\24\0\3\121\5\0"
      + "\7\121\1\0\1\121\6\0\1\121\1\147\6\121\24\0" + "\3\121\44\0\1\150\1\151\61\0\1\152\25\0\2\143" + "\1\0\2\143\1\0\1\143\11\0\1\143\1\0\3\143"
      + "\24\0\1\111\2\143\7\0\2\144\2\0\1\144\15\0" + "\1\110\27\0\1\110\1\112\7\0\2\144\2\0\1\144" + "\66\0\1\153\50\0\7\121\1\0\1\121\6\0\2\121"
      + "\1\154\5\121\24\0\3\121\44\0\1\155\23\0\7\121" + "\1\0\1\121\6\0\3\121\1\156\4\121\24\0\3\121" + "\5\0\7\121\1\0\1\121\6\0\4\121\1\157\3\121"
      + "\24\0\3\121\5\0\7\121\1\0\1\121\6\0\5\121" + "\1\160\2\121\24\0\3\121\5\0\7\121\1\0\1\121" + "\6\0\6\121\1\161\1\121\24\0\3\121\5\0\7\121"
      + "\1\0\1\121\6\0\7\121\1\162\24\0\3\121\5\0" + "\7\121\1\0\1\121\6\0\3\121\1\163\4\121\24\0" + "\3\121\1\0";
  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;
  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = { "Unkown internal scanner error", "Error: could not match input",
      "Error: pushback value was too large" };
  /** ZZ_ATTRIBUTE[aState] contains the attributes of state
   * <code>aState</code> */
  private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();
  private static final String ZZ_ATTRIBUTE_PACKED_0 = "\11\0\1\11\1\1\2\11\1\1\1\11\7\1\2\11" + "\1\1\10\11\4\1\3\11\5\1\2\11\1\1\1\11"
      + "\1\1\1\11\1\1\1\11\1\1\1\11\1\1\1\11" + "\2\1\1\11\2\1\1\11\1\1\1\11\2\1\2\0" + "\1\1\1\0\6\11\1\1\2\11\2\1\2\11\1\1"
      + "\1\11\1\1\13\11\2\1\1\0\2\1\1\11\1\1" + "\2\11\1\1\1\11\6\1";

  private static int[] zzUnpackAction() {
    final int[] result = new int[115];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(final String packed, final int offset, final int[] result) {
    int i = 0; /* index in packed string */
    int j = offset; /* index in unpacked array */
    final int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      final int value = packed.charAt(i++);
      do
        result[j++] = value;
      while (--count > 0);
    }
    return j;
  }

  private static int[] zzUnpackAttribute() {
    final int[] result = new int[115];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(final String packed, final int offset, final int[] result) {
    int i = 0; /* index in packed string */
    int j = offset; /* index in unpacked array */
    final int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      final int value = packed.charAt(i++);
      do
        result[j++] = value;
      while (--count > 0);
    }
    return j;
  }

  /** Unpacks the compressed character translation table.
   * @param packed the packed character translation table
   * @return the unpacked character translation table */
  private static char[] zzUnpackCMap(final String packed) {
    final char[] map = new char[0x10000];
    int i = 0; /* index in packed string */
    int j = 0; /* index in unpacked array */
    while (i < 1764) {
      int count = packed.charAt(i++);
      final char value = packed.charAt(i++);
      do
        map[j++] = value;
      while (--count > 0);
    }
    return map;
  }

  private static int[] zzUnpackRowMap() {
    final int[] result = new int[115];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(final String packed, final int offset, final int[] result) {
    int i = 0; /* index in packed string */
    int j = offset; /* index in unpacked array */
    final int l = packed.length();
    while (i < l) {
      final int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  private static int[] zzUnpackTrans() {
    final int[] result = new int[3162];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(final String packed, final int offset, final int[] result) {
    int i = 0; /* index in packed string */
    int j = offset; /* index in unpacked array */
    final int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do
        result[j++] = value;
      while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;
  /** the current state of the DFA */
  private int zzState;
  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;
  /** this buffer contains the current text to be matched and is the source of
   * the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];
  /** the textposition at the last accepting state */
  private int zzMarkedPos;
  /** the current text position in the buffer */
  private int zzCurrentPos;
  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;
  /** endRead marks the last character in the buffer, that has been read from
   * input */
  private int zzEndRead;
  /** number of newlines encountered up to the start of the matched text */
  private int yyline;
  /** the number of characters up to the start of the matched text */
  private int yychar;
  /** the number of characters from the last newline up to the start of the
   * matched text */
  private int yycolumn;
  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;
  private final StringBuffer $ = new StringBuffer();

  /** Creates a new scanner. There is also java.io.Reader version of this
   * constructor.
   * @param in the java.io.Inputstream to read input from. */
  public RawTokenizer(final java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** Creates a new scanner There is also a java.io.InputStream version of this
   * constructor.
   * @param in the java.io.Reader to read input from. */
  public RawTokenizer(final java.io.Reader in) {
    reset();
    zzReader = in;
  }

  public int chars() {
    return yychar + 1;
  }

  public int column() {
    return yycolumn + 1;
  }

  public void error(final String s) {
    System.err.println(notify(s));
    reset();
  }

  public int line() {
    return yyline + 1;
  }

  public String location() {
    return "[" + line() + "," + column() + "]: ";
  }

  /** Resumes scanning until the next regular expression is matched, the end of
   * input is encountered or an I/O-Error occurs.
   * @return the next token
   * @exception java.io.IOException if any I/O-Error occurs */
  public Token next() throws java.io.IOException {
    int zzInput;
    int zzAction;
    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char[] zzBufferL = zzBuffer;
    final char[] zzCMapL = ZZ_CMAP;
    final int[] zzTransL = ZZ_TRANS;
    final int[] zzRowMapL = ZZ_ROWMAP;
    final int[] zzAttrL = ZZ_ATTRIBUTE;
    while (true) {
      zzMarkedPosL = zzMarkedPos;
      yychar += zzMarkedPosL - zzStartRead;
      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL; zzCurrentPosL++)
        switch (zzBufferL[zzCurrentPosL]) {
          case '\u000B':
          case '\u000C':
          case '\u0085':
          case '\u2028':
          case '\u2029':
            yyline++;
            yycolumn = 0;
            zzR = false;
            break;
          case '\r':
            yyline++;
            yycolumn = 0;
            zzR = true;
            break;
          case '\n':
            if (zzR)
              zzR = false;
            else {
              yyline++;
              yycolumn = 0;
            }
            break;
          default:
            zzR = false;
            yycolumn++;
        }
      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too
        // much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          final boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof)
            zzPeek = false;
          else
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek)
          yyline--;
      }
      zzAction = -1;
      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
      zzState = ZZ_LEXSTATE[zzLexicalState];
      zzForAction: {
        while (true) {
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          } else {
            // store back cached positions
            zzCurrentPos = zzCurrentPosL;
            zzMarkedPos = zzMarkedPosL;
            final boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL = zzCurrentPos;
            zzMarkedPosL = zzMarkedPos;
            zzBufferL = zzBuffer;
            zzEndReadL = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            } else
              zzInput = zzBufferL[zzCurrentPosL++];
          }
          final int zzNext = zzTransL[zzRowMapL[zzState] + zzCMapL[zzInput]];
          if (zzNext == -1)
            break zzForAction;
          zzState = zzNext;
          final int zzAttributes = zzAttrL[zzState];
          if ((zzAttributes & 1) == 1) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ((zzAttributes & 8) == 8)
              break zzForAction;
          }
        }
      }
      // store back cached position
      zzMarkedPos = zzMarkedPosL;
      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 4: {
          return SPACE;
        }
        case 75:
          break;
        case 74: {
          return AT_INTERFACE;
        }
        case 76:
          break;
        case 38: {
          endExcluding();
          return LINE_COMMENT;
        }
        case 77:
          break;
        case 73: {
          return URSHIFTEQ;
        }
        case 78:
          break;
        case 13: {
          return LPAREN;
        }
        case 79:
          break;
        case 26: {
          return QUESTION;
        }
        case 80:
          break;
        case 28: {
          return AND;
        }
        case 81:
          break;
        case 50: {
          return MULTEQ;
        }
        case 82:
          break;
        case 61: {
          return OROR;
        }
        case 83:
          break;
        case 37: {
          endIncluding();
          return CHARACTER_LITERAL;
        }
        case 84:
          break;
        case 27: {
          return COLON;
        }
        case 85:
          break;
        case 46: {
          return MINUSEQ;
        }
        case 86:
          break;
        case 53: {
          return GTEQ;
        }
        case 87:
          break;
        case 21: {
          return EQ;
        }
        case 88:
          break;
        case 44: {
          return FLOAT_LITERAL;
        }
        case 89:
          break;
        case 55: {
          return LTEQ;
        }
        case 90:
          break;
        case 69: {
          return RSHIFTEQ;
        }
        case 91:
          break;
        case 1: {
          regret();
          reset();
          continue;
        }
        case 92:
          break;
        case 39: {
          gotoExcluding(DOC_EOLN);
          return PARTIAL_DOC_COMMENT;
        }
        case 93:
          break;
        case 48: {
          begin(SCAN_BLOCK_COMMENT);
          continue;
        }
        case 94:
          break;
        case 63: {
          return PLUSPLUS;
        }
        case 95:
          break;
        case 51: {
          return ANNOTATION;
        }
        case 96:
          break;
        case 42: {
          truncate();
          goTo(SCAN_DOC_COMMENT);
          return NL_DOC_COMMENT;
        }
        case 97:
          break;
        case 19: {
          return SEMICOLON;
        }
        case 98:
          break;
        case 5: {
          try {
            return Token.valueOf("_" + yytext());
          } catch (final IllegalArgumentException e) {
            return IDENTIFIER;
          }
        }
        case 99:
          break;
        case 65: {
          return MODEQ;
        }
        case 100:
          break;
        case 20: {
          return COMMA;
        }
        case 101:
          break;
        case 10: {
          return MULT;
        }
        case 102:
          break;
        case 41: {
          truncate();
          goTo(SCAN_BLOCK_COMMENT);
          return NL_BLOCK_COMMENT;
        }
        case 103:
          break;
        case 32: {
          return MOD;
        }
        case 104:
          break;
        case 43: {
          return DOUBLE_LITERAL;
        }
        case 105:
          break;
        case 31: {
          return XOR;
        }
        case 106:
          break;
        case 9: {
          return DIV;
        }
        case 107:
          break;
        case 36: {
          endExcluding();
          return UNTERMINATED_CHARACTER_LITERAL;
        }
        case 108:
          break;
        case 71: {
          return LSHIFTEQ;
        }
        case 109:
          break;
        case 40: {
          gotoExcluding(BLOCK_EOLN);
          return PARTIAL_BLOCK_COMMENT;
        }
        case 110:
          break;
        case 30: {
          return PLUS;
        }
        case 111:
          break;
        case 45: {
          return MINUSMINUS;
        }
        case 112:
          break;
        case 64: {
          return XOREQ;
        }
        case 113:
          break;
        case 62: {
          return PLUSEQ;
        }
        case 114:
          break;
        case 34: {
          endExcluding();
          return UNTERMINATED_STRING_LITERAL;
        }
        case 115:
          break;
        case 52: {
          return EQEQ;
        }
        case 116:
          break;
        case 68: {
          begin(SCAN_DOC_COMMENT);
          continue;
        }
        case 117:
          break;
        case 23: {
          return LT;
        }
        case 118:
          break;
        case 12: {
          begin(SCAN_CHAR_LITERAL);
          continue;
        }
        case 119:
          break;
        case 49: {
          return DIVEQ;
        }
        case 120:
          break;
        case 7: {
          return DOT;
        }
        case 121:
          break;
        case 2: {
          return UNKNOWN_CHARACTER;
        }
        case 122:
          break;
        case 60: {
          return OREQ;
        }
        case 123:
          break;
        case 16: {
          return RBRACE;
        }
        case 124:
          break;
        case 22: {
          return GT;
        }
        case 125:
          break;
        case 18: {
          return RBRACK;
        }
        case 126:
          break;
        case 58: {
          return ANDEQ;
        }
        case 127:
          break;
        case 11: {
          begin(SCAN_STRING_LITERAL);
          continue;
        }
        case 128:
          break;
        case 70: {
          return URSHIFT;
        }
        case 129:
          break;
        case 67: {
          endIncluding();
          return BLOCK_COMMENT;
        }
        case 130:
          break;
        case 25: {
          return COMP;
        }
        case 131:
          break;
        case 29: {
          return OR;
        }
        case 132:
          break;
        case 3: {
          return NL;
        }
        case 133:
          break;
        case 6: {
          return INTEGER_LITERAL;
        }
        case 134:
          break;
        case 66: {
          endIncluding();
          return DOC_COMMENT;
        }
        case 135:
          break;
        case 15: {
          return LBRACE;
        }
        case 136:
          break;
        case 35: {
          endIncluding();
          return STRING_LITERAL;
        }
        case 137:
          break;
        case 17: {
          return LBRACK;
        }
        case 138:
          break;
        case 24: {
          return NOT;
        }
        case 139:
          break;
        case 33: {
          extend();
          continue;
        }
        case 140:
          break;
        case 72: {
          return EMPTY_BLOCK_COMMENT;
        }
        case 141:
          break;
        case 54: {
          return RSHIFT;
        }
        case 142:
          break;
        case 47: {
          begin(SCAN_LINE_COMMENT);
          continue;
        }
        case 143:
          break;
        case 8: {
          return MINUS;
        }
        case 144:
          break;
        case 57: {
          return NOTEQ;
        }
        case 145:
          break;
        case 14: {
          return RPAREN;
        }
        case 146:
          break;
        case 56: {
          return LSHIFT;
        }
        case 147:
          break;
        case 59: {
          return ANDAND;
        }
        case 148:
          break;
        default:
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            switch (zzLexicalState) {
              case SCAN_LINE_COMMENT: {
                endExcluding();
                return LINE_COMMENT;
              }
              case 116:
                break;
              case RESET: {
                return EOF;
              }
              case 117:
                break;
              case SCAN_STRING_LITERAL: {
                regret();
                end();
                return UNTERMINATED_STRING_LITERAL;
              }
              case 118:
                break;
              case SCAN_BLOCK_COMMENT: {
                endExcluding();
                return UNTERMINATED_BLOCK_COMMENT;
              }
              case 119:
                break;
              case YYINITIAL: {
                return EOF;
              }
              case 120:
                break;
              case SCAN_DOC_COMMENT: {
                endExcluding();
                return UNTERMINATED_DOC_COMMENT;
              }
              case 121:
                break;
              case SCAN_CHAR_LITERAL: {
                endExcluding();
                return UNTERMINATED_CHARACTER_LITERAL;
              }
              case 122:
                break;
              case SCAN_CODE: {
                return EOF;
              }
              case 123:
                break;
              default:
                return null;
            }
          } else
            zzScanError(ZZ_NO_MATCH);
      }
    }
  }

  public String notify(final String s) {
    return location() + s + " " + token();
  }

  public void reset() {
    truncate();
    yybegin(SCAN_CODE);
  }

  /* user code: */
  public String text() {
    return $.length() > 0 ? $.toString() : yytext();
  }

  public String token() {
    return "<" + text() + ">";
  }

  /** Enters a new lexical state
   * @param newState the new lexical state */
  public final void yybegin(final int newState) {
    zzLexicalState = newState;
  }

  /** Returns the character at position <tt>pos</tt> from the matched text. It
   * is equivalent to yytext().charAt(pos), but faster
   * @param pos the position of the character to fetch. A value from 0 to
   *        yylength()-1.
   * @return the character at position pos */
  public final char yycharat(final int pos) {
    return zzBuffer[zzStartRead + pos];
  }

  /** Closes the input stream. */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true; /* indicate end of file */
    zzEndRead = zzStartRead; /* invalidate buffer */
    if (zzReader != null)
      zzReader.close();
  }

  /** Returns the length of the matched text region. */
  public final int yylength() {
    return zzMarkedPos - zzStartRead;
  }

  /** Pushes the specified amount of characters back into the input stream. They
   * will be read again by then next call of the scanning method
   * @param number the number of characters to be read again. This number must
   *        not be greater than yylength()! */
  public void yypushback(final int number) {
    if (number > yylength())
      zzScanError(ZZ_PUSHBACK_2BIG);
    zzMarkedPos -= number;
  }

  /** Resets the scanner to read from a new input stream. Does not close the old
   * reader. All internal variables are reset, the old input stream
   * <b>cannot</b> be reused (internal buffer is discarded and lost). Lexical
   * state is set to <tt>ZZ_INITIAL</tt>.
   * @param reader the new input stream */
  public final void yyreset(final java.io.Reader reader) {
    zzReader = reader;
    zzAtEOF = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }

  /** Returns the current lexical state. */
  public final int yystate() {
    return zzLexicalState;
  }

  /** Returns the text matched by the current regular expression. */
  public final String yytext() {
    return new String(zzBuffer, zzStartRead, zzMarkedPos - zzStartRead);
  }

  private void begin(final int state) {
    truncate();
    extend();
    yybegin(state);
  }

  private void end() {
    goTo(RESET);
  }

  private void endExcluding() {
    regret();
    end();
  }

  private void endIncluding() {
    extend();
    end();
  }

  private void extend() {
    $.append(yytext());
  }

  private void goTo(final int state) {
    yybegin(state);
  }

  private void gotoExcluding(final int state) {
    regret();
    goTo(state);
  }

  private void regret() {
    yypushback(yylength());
  }

  private void truncate() {
    $.setLength(0);
  }

  /** Refills the input buffer.
   * @return <code>false</code>, iff there was new input.
   * @exception java.io.IOException if any I/O-Error occurs */
  private boolean zzRefill() throws java.io.IOException {
    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead, zzBuffer, 0, zzEndRead - zzStartRead);
      /* translate stored positions */
      zzEndRead -= zzStartRead;
      zzCurrentPos -= zzStartRead;
      zzMarkedPos -= zzStartRead;
      zzStartRead = 0;
    }
    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      final char newBuffer[] = new char[zzCurrentPos * 2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }
    /* finally: fill the buffer with new input */
    final int numRead = zzReader.read(zzBuffer, zzEndRead, zzBuffer.length - zzEndRead);
    if (numRead > 0) {
      zzEndRead += numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream
    if (numRead == 0) {
      final int c = zzReader.read();
      if (c == -1)
        return true;
      else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }
    }
    // numRead < 0
    return true;
  }

  /** Reports an error that occured while scanning. In a wellformed scanner (no
   * or only correct usage of yypushback(int) and a match-all fallback rule)
   * this method will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong (e.g. a JFlex bug
   * producing a faulty scanner etc.). Usual syntax/scanner level error handling
   * should be done in error fallback rules.
   * @param errorCode the code of the errormessage to display */
  private void zzScanError(final int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    } catch (final ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }
    throw new Error(message);
  }
}