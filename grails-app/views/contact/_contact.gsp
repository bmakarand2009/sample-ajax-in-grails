<ul>
  <g:each var="contact" in="${contactList}">
    <li>
      ${contact.name} - <i>${contact.phone} - <g:link action="show" params="[contactName: contact.name.encodeAsContactName()]">Show </g:link><i>
     <!--  before="alert('Deleting Record');" -->
      <g:remoteLink action="ajaxdelete"  id="${contact.id}" before="return confirm('Are you sure?');" update="contactsElement">Delete </g:remoteLink>
    </li>

  </g:each>
</ul>


