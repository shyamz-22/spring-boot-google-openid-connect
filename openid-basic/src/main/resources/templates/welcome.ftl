<#-- @ftlvariable name="authenticatedUser" type="com.ennovate.openidconnect.client.model.OpenIdConnectAuthenticationToken" -->
<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Google Open Id</title>
    <!-- Latest compiled and minified CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link href="http://localhost:8080/css/card.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-11 col-sm-5">
            <div class="card hovercard">
                <div class="cardheader"></div>
                <div class="avatar">
                    <img alt="" src="${authenticatedUser.picture!}">
                </div>
                <div class="info">
                    <div class="title">
                        <a href="#">${authenticatedUser.nameOfUser!}</a>
                    </div>
                    <div class="desc">${authenticatedUser.email!}</div>
                    <div class="desc">User Id : ${authenticatedUser.userId}</div>
                    <div class="desc">Preferred Language: ${authenticatedUser.locale!}</div>
                </div>
                <div class="bottom">
                    <a class="btn btn-primary btn-twitter btn-sm" href="https://twitter.com/shyamala_u" target="_blank">
                        <i class="fa fa-twitter"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-11 col-sm-5">
            <div class="panel panel-warning">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <span class="glyphicon glyphicon-qrcode pull-left"></span>
                        &nbsp;&nbsp;Id Token</h3>
                </div>
                <div class="panel-body" style="overflow-y: scroll">
                    <figure> ${authenticatedUser.accessToken.idToken}</figure>
                </div>
            </div>
        </div>

    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
        integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
        crossorigin="anonymous"></script>
</body>
</html>