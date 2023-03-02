<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<script>
	var messageBox = function(title, message, callback) {
		$('#dialog-message p ').text(message);
		$( "#dialog-message" ).attr("title", title).dialog({
			width: 340,
			height: 170, 
			modal: true,
			buttons: {
				"확인": function() {
					//dialog를 부르면서 close를 부르면 꺼짐
					$(this).dialog('close'); //this는 dialog-message
				}
			},
			close: callback
		});
	}
	$(function() {
		$('#join-form').submit(function() {
			event.preventDefault(); //event 일어나는거 막아두기
			
			//1. 이름 유효성 검사
			if($('#name').val() === '') {
				messageBox('회원가입', '이름이 비어있습니다.', function() {
					$('#name').focus();
				});
				return;
			}
			//2. 이메일 유효성 체크
			if($("#email").val() === ''){
				messageBox('회원가입', '이메일이 비어있습니다.', function() {
					$('#email').focus();
				});
				return;
			}
			//3. 이메일 중복 체크 유무
			if(!$('#img-check').is(":visible")){ //보이지 않으면 체크안했음
				messageBox('회원가입', '이메일 중복체크를 하지 않았습니다.');
				return;
			}
			//4. 비밀번호 유효성 체크
			if($('#password').val() === '') {
				messageBox('회원가입', '비밀번호가 비어 있습니다.', function() {
					$('#password').focus();
				});
				return;
			}
			//5. 약관 동의 유무
			if(!$('#agree-prov').is(":checked")){
				messageBox('회원가입', '약관 동의를 하지 않았습니다.');
				return;
			}
			//6. OK form
			this.submit();
		});
		$('#email').change(function() {
			$("#img-check").hide(); 	 
			$('#btn-checkemail').show(); 
		});
		$('#btn-checkemail').click(function() {
			//데이터 가져오기
			var email = $("#email").val();
			if(email === ''){
				return;
			}
			
			$.ajax({
				url: "${pageContext.request.contextPath}/user/api/checkemail?email="+ email
				,type: "get"
				,dataType: "json"
				,error: function(xhr, status, error){
					console.log(status, error);
				}
				,success: function(response) {
					console.log(response);
					if(response.result === 'fail') {
						//console.error(response.message);
						return;
					}
					
					if(response.data) {
						messageBox('회원가입', "존재하는 이메일입니다. 다른 이메일을 선택해주세요.", function() {
							$('#email').val("").focus();
						});
						return;
					}
					$("#img-check").show(); 	 //display
					$('#btn-checkemail').hide(); //display:none
				}
			})
		});
	});
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">
				<form:form 
					modelAttribute="userVo"
					id="join-form" 
					name="joinForm"
					method="post" 
					action="${pageContext.request.contextPath }/user/join">
					<label class="block-label" for="name">이름</label>
					<form:input path="name" />
					<spring:hasBindErrors name="userVo">
						<p style="color:#f00; text-align:left; padding:0">
							<c:if test="${errors.hasFieldErrors('name') }">
								<!--
									${errors.getFieldError("name").defaultMessage }
								-->
								<spring:message code='${errors.getFieldError("name").codes[0] }' />
							</c:if>
						</p>
					</spring:hasBindErrors>
					<label class="block-label" for="email">이메일</label>
					<form:input path="email" />
					<img id="img-check" src="${pageContext.request.contextPath }/assets/images/check.png" style="width: 18px; vertical-align: bottom; display:none;">
					<input type="button" id="btn-checkemail" value="중복체크" style="display: ;">
					<p style="color:#f00; text-align:left; padding:0">
						<form:errors path="email" />
					</p>
					
					<label class="block-label">
						<spring:message code="user.join.label.password"/>
					</label>
					<form:password path="password"/>
					<p style="color:#f00; text-align:left; padding:0">
						<form:errors path="password" />
					</p>			
					<fieldset>
						<legend>성별</legend>
						<form:radiobutton path="gender" value="female" label="여"/>
						<form:radiobutton path="gender" value="male" label="남"/>
					</fieldset>
					
					<fieldset>
						<legend>약관동의</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label>서비스 약관에 동의합니다.</label>
					</fieldset>
					
					<input type="submit" value="가입하기">
				</form:form>
			</div>
		</div>
		<div id="dialog-message" title="" style=':none'>
	    	<p style="line-height: 60px">
	    	
	    	</p>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>