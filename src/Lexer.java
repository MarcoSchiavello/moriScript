import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    private String _Code;
    private int _Position;
    private char _CurrentChar;
    private StringBuffer buffer;
    private List<Token> tokens;

    private final Map<String, TokenType> _Match = new HashMap<>();

    public Lexer(String code) {
        _Code     = code;
        _Position = 0;
        buffer = new StringBuffer();
        tokens = new ArrayList<>();
        update();

        _Match.put("(", TokenType.LParen);
        _Match.put(")", TokenType.RParen);
        _Match.put("{", TokenType.LScope);
        _Match.put("}", TokenType.RScope);

        _Match.put("fdixsugdix", TokenType.Function);
        _Match.put("metto2", TokenType.Return);
        _Match.put("chiamatalgiudice", TokenType.FunctionCall);

        _Match.put("condizionediif", TokenType.If);
        _Match.put("egiziani", TokenType.Elif);
        _Match.put("condizionedielse", TokenType.Else);
        _Match.put("impegno", TokenType.Try);
        _Match.put("nonvedoragionamento", TokenType.Catch);

        _Match.put("=", TokenType.Assign);
        _Match.put("==", TokenType.Compare);
        _Match.put("+", TokenType.Sum);
        _Match.put("-", TokenType.Subtract);
        _Match.put("*", TokenType.Multiply);
        _Match.put("/", TokenType.Divide);
    }

    private void update() {
        if (_Position < _Code.length())
            _CurrentChar = _Code.charAt(_Position);
    }

    private void advance(long step) {
        _Position += step;
        update();
    }

    private void advance() {
        advance(1);
        update();
    }

    private void clearBuffer() { buffer.delete(0, buffer.length()); }

    private TokenType toTokenType(StringBuffer buffer) {
        if (buffer.isEmpty())
            return TokenType.Bad;

        if (_Match.containsKey(buffer.toString()))
            return _Match.get(buffer.toString());

        if (Character.isAlphabetic(buffer.charAt(0)))
            return TokenType.Identifier;

        if (Character.isDigit(buffer.charAt(0)))
            return TokenType.Integer;

        if (buffer.charAt(0) == '"')
            return TokenType.String;

        return TokenType.Bad;
    }
    private TokenType toTokenType() { return toTokenType(this.buffer); }

    private void push(StringBuffer buffer) {
        if (!buffer.isEmpty()) {
            if (Character.isSpaceChar(buffer.charAt(0))) {
                clearBuffer();
                return;
            }

            tokens.add(new Token(toTokenType(buffer), buffer.toString()));
            clearBuffer();
        }
    }
    private void push() { push(this.buffer); }

    private StringBuffer collectString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(_CurrentChar);
        advance();

        for (; _Position < _Code.length() && _CurrentChar != '"'; advance()) {
            buffer.append(_CurrentChar);
        }

        buffer.append(_CurrentChar);
        advance(1);
        return buffer;
    }

    private void endOfToken(StringBuffer buffer) {
        push(buffer);

        if (!Character.isSpaceChar(_CurrentChar) && _CurrentChar != '"')
            buffer.append(_CurrentChar);
    }
    private void endOfToken() { endOfToken(this.buffer); }

    private boolean tokenExists(StringBuffer buffer) {
        return _Match.containsKey(buffer.toString() + _CurrentChar) || buffer.isEmpty();
    }
    private boolean tokenExists() { return tokenExists(this.buffer); }

    public List<Token> process() {
        for (; _Position < _Code.length(); advance()) {
            if (!buffer.isEmpty()) {
                if (Character.isDigit(buffer.charAt(0)) && Character.isDigit(_CurrentChar)) {
                    buffer.append(_CurrentChar);
                    continue;
                }

                if (!Character.isAlphabetic(buffer.charAt(0)) && Character.isAlphabetic(_CurrentChar)) {
                    endOfToken();
                    continue;
                }

                if (Character.isDigit(_CurrentChar)) {
                    endOfToken();
                    continue;
                }
            }

            if (Character.isAlphabetic(_CurrentChar)) {
                buffer.append(_CurrentChar);
                continue;
            }

            if (tokenExists(buffer)) {
                buffer.append(_CurrentChar);
                continue;
            }

            endOfToken();

            if (_CurrentChar == '"') {
                endOfToken(collectString());
                buffer.append(_CurrentChar);
            }
        }

        endOfToken();

        return tokens;
    }
}
