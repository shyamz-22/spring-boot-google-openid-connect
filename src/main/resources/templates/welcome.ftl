<#-- @ftlvariable name="authenticatedUser" type="com.ennovate.openidconnect.client.model.OpenIdConnectAuthenticationToken" -->
<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<html lang="en">
<body>
<div>
    <h1>WELCOME</h1>
    <img src="${authenticatedUser.picture}"/>
    <p>Name: ${authenticatedUser.nameOfUser}</p>
    <p>Email: ${authenticatedUser.email}</p>
    <p>Id: ${authenticatedUser.userId}</p>
    <p>${authenticatedUser.nameOfUser} likes to see website in language ${authenticatedUser.locale}</p>
</div>
</body>

</html>