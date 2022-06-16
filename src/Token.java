public record Token(TokenType type, String val) {
    public String toString() {
        return "Token Type: " + type + " \nValue: " + val;
    }
}
