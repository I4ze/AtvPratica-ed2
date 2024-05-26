public class Generics<Key, Value extends Comparable<Value>> implements Comparable<Generics<?, ?>> {
    private final Key key;
    private final Value value;

    public Generics(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public Key getKey() {
        return this.key;
    }

    public Value getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "{ " + this.key + " : " + this.value + " }";
    }

    @Override
    public int compareTo(Generics<?, ?> o) {

        // Teste do tipo do valor object
        if (this.value instanceof Integer) {
            return ((Integer) this.value).compareTo((Integer) o.getValue() );
        } else if (this.value instanceof Double) {
            return ((Double) this.value).compareTo((Double) o.getValue());
        }

        return ((String) this.value).compareTo((String) o.getValue() );
    }
}


