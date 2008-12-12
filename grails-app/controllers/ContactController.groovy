class ContactController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']


     def addShowContact = {
        def contact = Contact.get(params.contactId)
        contact.addToAddresses(name:params.address)
        contact.save()
        render(template:'addresses', model:[theAddresesToRender:contact.addresses])
    }

    def addContact = {
        log.debug("MARK lets see if You Got the Params")
        def contact = new Contact(params)
        if(!contact.hasErrors() && contact.save(flush:true)) {
             render(template:"contact", model:[contactList:Contact.list()])
        } else {
            log.debug("There was a error MARK, Sending ERROR STATUS")
            response.status = response.SC_INTERNAL_SERVER_ERROR // conflictrender "Invalid Test, conflicts with existing ID"
            render(template: 'addContactError', model:[contact: contact])
       }
    }
 
    def list = {
        if(!params.max) params.max = 10
        [ contactList: Contact.list( params ) ]
    }

    def show = {
        def contact = Contact.get( params.id )

        if(!contact) {
            flash.message = "Contact not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ contact : contact ] }
    }

    def delete = {
        def contact = Contact.get( params.id )
        if(contact) {
            contact.delete()
            flash.message = "Contact ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Contact not found with id ${params.id}"
            redirect(action:list)
        }
    }

  

    def edit = {
        def contact = Contact.get( params.id )

        if(!contact) {
            flash.message = "Contact not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ contact : contact ]
        }
    }

    def update = {
        def contact = Contact.get( params.id )
        if(contact) {
            contact.properties = params
            if(!contact.hasErrors() && contact.save()) {
                flash.message = "Contact ${params.id} updated"
                redirect(action:show,id:contact.id)
            }
            else {
                render(view:'edit',model:[contact:contact])
            }
        }
        else {
            flash.message = "Contact not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def contact = new Contact()
        contact.properties = params
        return ['contact':contact]
    }

    def save = {
        def contact = new Contact(params)
        if(!contact.hasErrors() && contact.save()) {
            flash.message = "Contact ${contact.id} created"
            redirect(action:show,id:contact.id)            
        }
        else {
            render(view:'create',model:[contact:contact])
        }
    }

    def ajaxsave = {
        def contact = new Contact(params)
        if(!contact.hasErrors() && contact.save()) {
             render(template:"createcontact", model:[contact:contact])
        }else {
            //render(template:"errorcontact")
            log.debug("There was a error MARK, Sending ERROR STATUS")
//            response.status = response.SC_INTERNAL_SERVER_ERROR // conflictrender "Invalid Test, conflicts with existing ID"
//            render(error: [code: 500, message:"Plese Try Again, Error Occured"])
            render(view:'create',model:[contact:contact])
        }
    }

    def ajaxdelete = {
        log.debug("MARK, DELETED SOMETHING FOR YOUR")
        def contact = Contact.get( params.id )
        contact?.delete(flush: true)
        if(!params.max) params.max = 10
        log.debug("Contact Deleted Succesfuly")
        render(template:"contact", model:[contactList:Contact.list(params)])
    }
}
