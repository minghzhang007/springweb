
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="../static/js/jquery.min.js" type="text/javascript"></script>
</head>
<body>
<h1>Hello ! everyone !</h1>
<h2>HAHAHAA!</h2>
<form action="" method="get" id="form">
   ID: <input type="text" id="id" name="id" value="100"/> <br/>
   Name: <input type="text" id="name" name="name" value="lewis"/> <br/>
   Birthday: <input type="text" id="birthday" name="birthday" value="2016-12-12"/> <br/>
   Gender: <input type="text" id="gender" name="gender" value="1"/> <br/>
    <input type="button" value="send" id="send">
</form>
<script>
    $('#send').click(function(){

        var serializeArray = $("#form").serializeArray();
        var json = {};
        $.each(serializeArray,function(i,field){
            json[this.name]=this.value;
        });
        var url="/master/hello/student";
        url +="?"+JSON.stringify(json);
        window.location.href=url;
    });
</script>
</body>
</html>
