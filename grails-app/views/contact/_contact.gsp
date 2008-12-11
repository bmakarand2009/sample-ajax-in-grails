<ul>
  <g:each var="contact" in="${contactList}">
    <li>
      ${contact.name} - <i>${contact.phone} - <g:link action="show" id="${contact.id}">Show </g:link><i>
     <!--  before="alert('Deleting Record');" -->
      <g:remoteLink action="ajaxdelete"  id="${contact.id}" update="contactsElement">Delete </g:remoteLink>
    </li>

  </g:each>
</ul>


