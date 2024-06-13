<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.84.0">
    <title>게시판 메인</title>
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>

    <!-- <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/sign-in/"> -->
    <!-- Bootstrap core CSS -->
	<!-- <link href="\css\main\bootstrap.min.css" rel="stylesheet"> -->


    
    <!-- Custom styles for this template -->
    <!-- <link href="\css\main\signin.css" rel="stylesheet"> -->
    <link href="\css\main\board.css" rel="stylesheet">
  </head>
  <body class="text-center">
	  <section class="notice">
	  <div class="page-title">
	        <div class="container">
	            <h3 style="margin-bottom: 25px;">자유게시판</h3>
	            	    <!-- board seach area -->
	    <div id="board-search">
	        <div class="container">
	            <div class="search-window">
	                <form action="">
	                    <div class="search-wrap">
	                        <label for="search" class="blind">자유게시파 내용 검색</label>
	                        <input id="search" type="search" name="" placeholder="검색어를 입력해주세요." value="">
	                        <button type="button" class="btn btn-dark">검색</button>
	                    </div>
	                </form>
	            </div>
	        </div>
	    </div>
	   
	  <!-- board list area -->
	    <div id="board-list">
	        <div class="container">
	            <table class="board-table">
	                <thead>
	                <tr>
	                    <th scope="col" class="th-num">번호</th>
	                    <th scope="col" class="th-title">제목</th>
	                    <th scope="col" class="th-name">작성자</th>
	                    <th scope="col" class="th-date">등록일</th>
	                </tr>
	                </thead>
	                <tbody>
	                
	                <c:if test="${!empty boardList}">
		                <c:forEach items="${boardList.content}" var="board">
			                <tr>
			                    <td><c:out value="${board.num}" escapeXml="false"/></td>
			                    <th><a href="/boardView.do?num=${board.num}"><c:out value="${board.title}" escapeXml="false" /> </a></th>
			                    <td><c:out value="${board.regNm}" escapeXml="false"/></td>
			                    <td>
			                    	<fmt:parseDate var="parDt" value="${board.regDt}" pattern="yyyy-MM-dd'T'HH:mm:ss" />
			                    	<fmt:formatDate var="fmtDt" value="${parDt}" pattern="yyyy-MM-dd HH:mm" />
			                    	<c:out value="${fmtDt}" escapeXml="false"/>
			                    </td>
			                </tr>	                
		                </c:forEach>
	                </c:if>
	                </tbody>
	            </table>
	        </div>
	    </div>
	    <div style="margin-top: 20px; text-align: right;">
			<button type="button" class="btn btn-blue insert" style="padding-top: 5px; padding-bottom: 5px;">글작성</button>
	    </div>
	            
	        </div>
		    <div style="text-align: center;">
		    	<ul class="paging">
				    <c:if test="${hasPrevious}">
				        <li><a href="/board.do?page=0">처음</a></li>
				        <li><a href="/board.do?page=${nowPage-1}">이전</a></li>
				    </c:if>
				    <c:forEach var="cPage" begin="0" end="${totalPages-1}" varStatus="status">
					    <li><a href="/board.do?page=${status.index}">${status.index+1}</a></li>
				    </c:forEach>
				    <c:if test="${hasNext}">
				        <li><a href="/board.do?page=${nowPage+1}">다음</a></li>
				        <li><a href="/board.do?page=${totalPages-1}">마지막</a></li>
				    </c:if>
				</ul>
		    </div>
	    </div>
	    
</section>
  
  
  </body>
	<script>
		$(document).ready(function(){
			
			$(".paging > li").attr("style","display: inline-block;");
			
			$(".insert").click(function(){
				location.href = '/insertBoardPage.do';
			});
		});
	</script>
</html>

