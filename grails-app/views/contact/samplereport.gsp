<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Contact List</title>
    <g:javascript>
      $j(document).ready(function() {
        $j('button.alert').click(function() {
          alert('this is an alert message');
        });
      });
    </g:javascript>
    <script type="text/javascript">
      function hidePhoneLabel(cid){
        Element.show("taPhone"+cid);
        Element.hide("labelphone"+cid)
      }
      function hidePhoneTextArea(cid){
        Element.hide("taPhone"+cid);
        Element.show("labelphone"+cid)
      }
    </script>
    

  </head>
  <body>
    <p>Below is a list of Contacts you have :</p>
    <div class="body">
      <h1>Contacts List</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <div class="list">
        <table>
          <thead>
            <tr>

              <g:sortableColumn property="id" title="Id" />

              <g:sortableColumn property="name" title="Name" />

              <g:sortableColumn property="phone" title="Phone No" />
            </tr>
          </thead>
          <tbody>
            <g:each in="${contactList}" status="i" var="contact">
              <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                <td><g:link action="show" id="${contact.id}">${fieldValue(bean:contact, field:'id')}</g:link></td>

                <td>${fieldValue(bean:contact, field:'name')}</td>
                <td>
                  <span id="ajaxphone">
                    <div id="labelphone${contact.id}">
                          <g:render template="updatephone" model="[contact:contact]"/>
                    </div>
                    <div id="taPhone${contact.id}" style="display:none">                      
                      <g:formRemote url="[action:'updatePhone']" update="labelphone${contact.id}" name="updatePhoneNumbers" onSuccess="hidePhoneTextArea('${contact.id}')">
                        <g:textField name="phone" value="${contact.phone}"/>
                        <g:hiddenField name="contactId" value="${contact.id}"/>
                      </g:formRemote>
                    </div>

                  </span>
                </td>
              </tr>
            </g:each>
          </tbody>
        </table>
      </div>
      <div class="paginateButtons">
        <g:paginate total="${Contact.count()}" />
      </div>
    </div>

 <button class="alert">JQuery Alert!</button>
    <div id="sample">
        This is sample code that needs to be toggled
    </div>

  </body>
</html>
