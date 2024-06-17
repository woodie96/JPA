<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.84.0">
    <title>메인 > 회원가입</title>
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
            width: 600px;
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
        
         textarea {
            width: 100%;
            height: 200px;
            padding: 10px;
            box-sizing: border-box;
            border-radius: 5px;
            border: none;
            resize: none;
        }

        .in {
            margin-bottom: 10px;
        }

        #insert {
                    width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border-radius: 5px;
            border: none;
            background-color: #1e90ff;
            margin-bottom: 10px;
            color: white;
        }
        #delete {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border-radius: 5px;
            border: none;
            background-color: #d9534f;
            margin-bottom: 10px;
            color: white;
        }
        
        #back {
                    width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border-radius: 5px;
            border: none;
            background-color: #696969;
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
        <form name="boardForm" id="boardForm" method="post">
        	<input type="hidden" name="num"  value="${board.num}"/>
            <input type="text" name="title" placeholder="제목을 입력해주세요" class="in" value="${board.title}" <c:if test="${boardAuth ne 'Y'}">disabled="disabled"</c:if>/>
            <textarea  name=content class="in" placeholder="내용을 입력해주세요" <c:if test="${boardAuth ne 'Y'}">disabled="disabled"</c:if>><c:out value="${board.content}" escapeXml="false"/></textarea>
            <c:if test="${boardAuth eq 'Y'}">
	            <button type="button"  id="insert" >수정</button>    
	            <button type="button"  id="delete" >삭제</button>          
            </c:if>
            <button type="button"  id="back" >목록</button> <br>
        </form>
        <!-- <a href="#none">아직 회원이 아니신가요?</a> -->
    </div>
</body>
	<script>
		$(document).ready(function(){
			$("#insert").click(function(){
				
				var formData = $("#boardForm").serialize();
				
				$.ajax({
					url : "/updateBoard.do",
					type: "POST",
					data:formData,
					dataType : "json",
					success : function(data){
						if(data.result == 'Y') {
							alert("글 수정이 완료되었습니다.");
							location.href = '/board.do';
						} else if(data.result == 'NL') {
							alert("로그인 후 다시 시도해주세요.");
							location.href = '/main.do';
							return false;
						} else {
							alert("오류가 발생했습니다, 관리자에게 문의해주세요.");
							return false;
						}
					}, error : function(data){
						alert("오류가 발생했습니다, 관리자에게 문의해주세요.");
						return false;
					}
				});	
			});
			
			$("#back").click(function(){
				location.href = '/board.do';
			});
			
			$("#delete").click(function(){
				$("#boardForm").attr("action","/deleteBoard.do").submit();
			});
		});
	</script>
</html>

