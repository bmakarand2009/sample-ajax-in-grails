<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Contact List</title>
    <g:javascript library="prototype" />
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
      <span class="menuButton"><g:link class="create" action="create">New Contact</g:link></span>
      <span class="menuButton"><g:link class="create" url="../sample.gsp"> Outside Create Contact</g:link></span>
      <span class="menuButton"><g:link class="create" action="samplereport"> Sample Ajax Report</g:link></span>
    </div>
    <div class="body">
      <h1>Contact List</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>

      <p>Below is a list of Contacts you have :</p>
      <ul id="contactsElement">
        <g:if test="${contactList}">
          <g:render template="contact" model="[contactList:contactList]"/>
        </g:if>
      </ul>
      <ul id="errorElement">

      </ul>
      <p>
        <g:formRemote url="[action:'addContact']" update="[success: 'contactsElement', failure: 'errorElement']" name="addContacts" onSuccess="Element.update('errorElement', '')">

          <table>
            <thead>
              <tr>

                <g:sortableColumn property="name" title="Name" />

                <g:sortableColumn property="email" title="Email" />

                <g:sortableColumn property="phone" title="Phone No" />

              </tr>
            </thead>
            <tbody>
              <tr>

                <td> <g:textField name="name" value="${fieldValue(bean:contact,field:'name')}"/></td>

                <td><g:textField name="email" value="${fieldValue(bean:contact,field:'email')}"/></td>

                <td><g:textField name="phone" value="${fieldValue(bean:contact,field:'phone')}"/></td>
                <td><g:submitButton name="add" value="Add" /> </td>
              </tr>
            </tbody>
          </table>

        </g:formRemote>
      </p>
    </div>
  </body>
</html>
