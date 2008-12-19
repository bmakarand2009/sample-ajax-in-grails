<ul>
  <g:each var="contact" in="${contactList}">

    <li>
      ${contact.name} - <i>${contact.phone}</i> - <g:link action="show" params="[contactName: contact.name.encodeAsContactName()]">Show </g:link>
      <gui:dialog
          title="Please confirm!"
          width="300px"
          triggers="[show:[type:'link', text:'Delete', on:'click']]"
          controller='contact' 
          action='ajaxdelete'
          update='contactsElement'
          params="[id:contact.id]" 
          form="true">Are you sure you want to delete ${contact.name}?</gui:dialog>
    </li>

  </g:each>
</ul>


