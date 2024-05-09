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
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap');

        * {
            font-family: 'Noto Sans KR', sans-serif;
        }

        body {
        	margin-top: 15%;
            /* background-color: #1BBC9B; */
        }

        div {
            margin: auto;
            width: 300px;
            background-color: #EEEFF1;
            border-radius: 5px;
            text-align: center;
            padding: 20px;
        }

        input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border-radius: 5px;
            border: none;
        }

        .in {
            margin-bottom: 10px;
        }

        #insert {
            background-color: #1BBC9B;
            margin-bottom: 30px;
            color: white;
        }

        a {
            text-decoration: none;
            color: #9B9B9B;
            font-size: 12px;
        }
    </style>
  </head>
  <body>
    <div>
        <form name="infoForm" id="infoForm">
            <input type="text" name="email" placeholder="ex) google@gmil.com" class="in">
            <input type="password" name="pwd" placeholder="비밀번호" class="in">
            <input type="button" id="insert" value="로그인"><br>
        </form>
        <a href="#none">아직 회원이 아니신가요?</a>
    </div>
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
						console.log(data)
						if(data.result == "N") {
							alert("입력하신 아이디 또는 패스워드를 확인해주세요.");
							return false;
						} else {
							location.href ="/board.do";
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

