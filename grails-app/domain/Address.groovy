class Address {
    String name
    static belongsTo = [Contact]
    
    String toString() {
        name
    }
}
