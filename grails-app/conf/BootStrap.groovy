class BootStrap {

     def init = { servletContext ->
         def jeff = new Contact(name:'jeff',email:'jeff@sample.com',phone:'393303003')
         jeff.save()
         def mark = new Contact(name:'mark',email:'mark@sample.com',phone:'77034343')
         mark.save()
         def rick = new Contact(name:'rick',email:'rick@sample.com',phone:'334343')
         rick.save()
    }
     def destroy = {
     }
} 