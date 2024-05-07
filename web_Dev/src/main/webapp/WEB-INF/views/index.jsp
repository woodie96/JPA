<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.84.0">
    <title>메인 > 로그인</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>

    <!-- <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sign-in/"> -->
    <!-- Bootstrap core CSS -->
	<link href="\css\main\bootstrap.min.css" rel="stylesheet">

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>

    
    <!-- Custom styles for this template -->
    <link href="\css\main\signin.css" rel="stylesheet">
  </head>
  <body class="text-center">
		<main class="form-signin">
		  <form id="infoForm" name="infoForm" method="POST">
		    <img class="mb-4" src="\images\main\bootstrap-logo.svg" alt="" width="72" height="57">
		    <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
		
		    <div class="form-floating">
		      <input type="email" class="form-control" id="floatingInput" name="email" placeholder="name@example.com">
		      <label for="floatingInput">Email address</label>
		    </div>
		    <div class="form-floating">
		      <input type="password" class="form-control" id="floatingPassword" name="pwd" placeholder="Password">
		      <label for="floatingPassword">Password</label>
		    </div>
		
		    <div class="checkbox mb-3">
		      <label>
		       <!--  <input type="checkbox" value="remember-me"> Remember me -->
		      </label>
		    </div>
		    <button type="button" class="w-100 btn btn-lg btn-primary" id="insert">Sign in</button>
		    <p class="mt-5 mb-3 text-muted">&copy; 2024</p>
		  </form>
		</main>
  </body>
	<script>
		$(document).ready(function(){
			$("#insert").click(function(){
				
				var formData = $("#infoForm").serialize();
				
				$.ajax({
					url : "/login.do",
					type: "POST",
					data:formData,
					dataType : "json",
					success : function(data){
						if(data.result == "N") {
							alert("입력하신 아이디 또는 패스워드를 확인해주세요.");
							return false;
						}
					}, error : function(data){
						alert("입력하신 아이디 또는 패스워드를 확인해주세요.");
						return false;
					}
				});	
			});
		});
	</script>
</html>

