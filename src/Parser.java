import nodes.Node;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Token> _Tokens;
    private List<Node> _Nodes = new ArrayList<>();

    public Parser(List<Token> cst) {
        _Tokens = cst;
    }

    public List<Node> parse() {
        return _Nodes;
    }
}
