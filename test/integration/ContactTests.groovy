class ContactTests extends GroovyTestCase {

    void testNameTooShort() {
        def c = new Contact(name:'xx')
        assertFalse 'validation should have failed', c.validate()
        
        def nameError = c.errors.getFieldError('name')
        assertNotNull 'name error should not have been null', nameError
        
        assertTrue 'expected error code was not found', 'Contact.name.size.toosmall' in nameError.codes
    }
    
    void testNameTooLong() {
        def c = new Contact(name:'123456789012345678901')
        assertFalse 'validation should have failed', c.validate()
        
        def nameError = c.errors.getFieldError('name')
        assertNotNull 'name error should not have been null', nameError

        assertTrue 'expected error code was not found', 'Contact.name.size.toobig' in nameError.codes
    }
    
    void testNameWithUnderscore() {
        def c = new Contact(name:'Tony_Iommi')
        assertFalse 'validation should have failed', c.validate()
        
        def nameError = c.errors.getFieldError('name')
        assertNotNull 'name error should not have been null', nameError

        assertTrue 'expected error code was not found', 'Contact.name.matches.invalid' in nameError.codes
    }
}
