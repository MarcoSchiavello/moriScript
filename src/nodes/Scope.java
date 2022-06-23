package nodes;

import java.util.ArrayList;
import java.util.List;

public class Scope extends Node {
    private List<Node> _Nodes;

    public Scope(List<Node> nodes) {
        super();
        _Nodes = nodes;
    }

    public Scope() {
        super();
        _Nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        _Nodes.add(node);
    }

    @Override
    public String toString() {
        return "Scope Node";
    }
}
