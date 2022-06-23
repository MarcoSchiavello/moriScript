package nodes;

public class Function extends Node {
    private Scope _Scope;
    private String _Name;

    public Function(String name, Scope scope) {
        super();

        _Name  = name;
        _Scope = scope;
    }

    public Function(String name) {
        super();

        _Name  = name;
        _Scope = new nodes.Scope();
    }

    @Override
    public String toString() {
        return "Function Node:\n\tName" + _Name;
    }
}
