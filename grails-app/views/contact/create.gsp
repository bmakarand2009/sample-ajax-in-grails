

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Create Contact</title>
    <g:javascript library="prototype" />
  <script type="text/javascript">
      function hidecontactform(){
        Element.hide('mycform');
      }
    </script>
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
      <span class="menuButton"><g:link class="list" action="list">Contact List</g:link></span>
    </div>
    <div class="body">
      <h1>Create Contact</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${contact}">
        <div class="errors">
          <g:renderErrors bean="${contact}" as="list" />
        </div>
      </g:hasErrors>

      <div id="contactsElement">
        <g:if test="${contact.id}">
          <g:render template="createcontact" model="[contact:contact]"/>
        </g:if>
      </div>

      <div id="errorElement" style="display:none">
          <g:render template="errorcontact" model="[contact:contact]"/>
      </div>

      <div id="mycform">
        <g:formRemote url="[action:'ajaxsave']" update="[success:'contactsElement', failure:'errorElement']" name="addContact" onSuccess="hidecontactform()">
          <div class="dialog">
            <table>
              <tbody>

                <tr class="prop">
                  <td valign="top" class="name">
                    <label for="name">Name:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:contact,field:'name','errors')}">
                    <input type="text" maxlength="20" id="name" name="name" value="${fieldValue(bean:contact,field:'name')}"/>
                  </td>
                </tr>

                <tr class="prop">
                  <td valign="top" class="name">
                    <label for="email">Email:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:contact,field:'email','errors')}">
                    <input type="text" id="email" name="email" value="${fieldValue(bean:contact,field:'email')}"/>
                  </td>
                </tr>

                <tr class="prop">
                  <td valign="top" class="name">
                    <label for="phone">Phone:</label>
                  </td>
                  <td valign="top" class="value ${hasErrors(bean:contact,field:'phone','errors')}">
                    <input type="text" id="phone" name="phone" value="${fieldValue(bean:contact,field:'phone')}"/>
                  </td>
                </tr>

              </tbody>
            </table>
          </div>
          <div class="buttons">
            <span class="button"><input class="save" type="submit" value="Create" /></span>
          </div>
        </g:formRemote>
      </div>
    </div>
     
  </body>
</html>
