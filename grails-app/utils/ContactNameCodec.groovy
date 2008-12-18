class ContactNameCodec {

    static encode = { theTarget ->
        theTarget.replaceAll(' ', '_')
    }
    
    static decode = { theTarget ->
        theTarget.replaceAll('_', ' ')
    }
}