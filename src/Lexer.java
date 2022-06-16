import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {
    private String _Code;
    private int _Position;
    private char _CurrentChar;

    private final Map<String, TokenType> _Match = new HashMap<>();

    public Lexer(String code) {
        _Code     = code;
        _Position = 0;
        update();

        _Match.put("(", TokenType.LParen);
        _Match.put(")", TokenType.RParen);
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

    private TokenType toTokenType(StringBuffer buffer) {
        if (buffer.isEmpty())
            return TokenType.Bad;

        if (_Match.keySet().contains(buffer.toString()))
            return _Match.get(buffer.toString());

        if (Character.isAlphabetic(buffer.charAt(0)))
            return TokenType.Identifier;

        if (Character.isDigit(buffer.charAt(0)))
            return TokenType.Integer;

        if (buffer.charAt(0) == '"')
            return TokenType.String;

        return TokenType.Bad;
    }

    private void push(List<Token> tokens, StringBuffer buffer) {
        if (!buffer.isEmpty()) {
            tokens.add(new Token(toTokenType(buffer), buffer.toString()));;
            buffer.delete(0, buffer.length());
        }
    }

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

    private void endOfToken(List<Token> tokens, StringBuffer buffer) {
        push(tokens, buffer);

        if (!Character.isSpaceChar(_CurrentChar) && _CurrentChar != '"')
            buffer.append(_CurrentChar);
    }

    public List<Token> process() {
        List<Token> tokens  = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();

        for (; _Position < _Code.length(); advance()) {
            if (!buffer.isEmpty()) {
                if (Character.isDigit(buffer.charAt(0)) && Character.isDigit(_CurrentChar)) {
                    buffer.append(_CurrentChar);
                    continue;
                }

                if (!Character.isAlphabetic(buffer.charAt(0)) && Character.isAlphabetic(_CurrentChar)) {
                    endOfToken(tokens, buffer);
                    continue;
                }

                if (Character.isDigit(_CurrentChar)) {
                    endOfToken(tokens, buffer);
                    continue;
                }
            }

            if (Character.isAlphabetic(_CurrentChar)) {
                buffer.append(_CurrentChar);
                continue;
            }

            endOfToken(tokens, buffer);

            if (_CurrentChar == '"') {
                endOfToken(tokens, collectString());
                buffer.append(_CurrentChar);
            }
        }

        endOfToken(tokens, buffer);

        return tokens;
    }
}
