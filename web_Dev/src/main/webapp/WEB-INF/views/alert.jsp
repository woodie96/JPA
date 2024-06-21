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
    <title>오류</title>
	<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
    <link href="\css\main\board.css" rel="stylesheet">
  </head>
  <body class="text-center">

  	<form id="errorFrom"></form>
  
  </body>
	<script>
		$(document).ready(function(){
			alert("${msg}");
			$("#errorFrom").attr("action","${url}").submit();
		});
	</script>
</html>

