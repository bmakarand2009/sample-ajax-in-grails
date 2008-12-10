

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Show Contact</title>
    <g:javascript library="prototype" />
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
      <span class="menuButton"><g:link class="list" action="list">Contact List</g:link></span>
      <span class="menuButton"><g:link class="create" action="create">New Contact</g:link></span>
    </div>
    <div class="body">
      <h1>Show Contact</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <div class="dialog">
        <table>
          <tbody>


            <tr class="prop">
              <td valign="top" class="name">Id:</td>

              <td valign="top" class="value">${fieldValue(bean:contact, field:'id')}</td>

            </tr>

            <tr class="prop">
              <td valign="top" class="name">Name:</td>

              <td valign="top" class="value">${fieldValue(bean:contact, field:'name')}</td>

            </tr>

            <tr class="prop">
              <td valign="top" class="name">Email:</td>

              <td valign="top" class="value">${fieldValue(bean:contact, field:'email')}</td>

            </tr>

            <tr class="prop">
              <td valign="top" class="name">Phone:</td>

              <td valign="top" class="value">${fieldValue(bean:contact, field:'phone')}</td>

            </tr>
            
            <tr class="prop">
              <td valign="top" class="name">Addresses:</td>
              
              <td  valign="top" style="text-align:left;" class="value">
                <div id="addressElement">
                  <g:render template="addresses" model="[theAddresesToRender:contact.addresses]"/>
                </div>
              </td>
              
            </tr>
          </tbody>
        </table>
      </div>
      <h3>Add Address</h3>
      <g:formRemote url="[action:'addShowContact']" update="addressElement" name="addContacts">
        <g:textField name="address" value=""/>
        <g:hiddenField name="contactId" value="${contact.id}"/>
        <input type="submit" value="Add" />
      </g:formRemote>    
      <div class="buttons">
        <g:form>
          <input type="hidden" name="id" value="${contact?.id}" />
          <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
          <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
        </g:form>
      </div>
    </div>
  </body>
</html>
