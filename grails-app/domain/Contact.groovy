class Contact {
   String name
   String email
   String phone

   static hasMany = [addresses:Address]
   static optionals = ['addresses']

   static constraints = {
        name unique:true, size:3..20, matches:/[^_]*/
        email(blank:false)
    }
}
