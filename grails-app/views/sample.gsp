<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
   <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Sample Form With Explicit Controller specified</title>
    
  </head>
  <body>
    <h1>This demostrated the ability of GRAILS to Invoice AJAX on a form from Outside the controller</h1>
    <p>Below is a list of Contacts you have :</p>
  
     <div id="contactsElement">
        <g:if test="${contact?.id}">
          <g:render template="createcontact" model="[contact:contact]"/>
        </g:if>
      </div>

      <div id="errorElement">
      </div>

      <div id="mycform">
        <g:formRemote url="[controller:'contact',action:'ajaxsamplesave']" update="[success:'contactsElement', failure:'errorElement']" name="addContact" onSuccess="Element.update('errorElement', '')">
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

  </body>
</html>
