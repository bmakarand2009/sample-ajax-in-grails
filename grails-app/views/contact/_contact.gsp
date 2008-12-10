<ul>
  <g:each var="contact" in="${contactList}">
    <li>
      <g:link action="show" id="${contact.id}">${contact.name}</g:link>
        - <i>${contact.phone}<i>
     <!--  before="alert('Deleting Record');" -->
      <g:remoteLink action="ajaxdelete"  id="${contact.id}" update="contactsElement">Delete</g:remoteLink>
    </li>

  </g:each>
</ul>


