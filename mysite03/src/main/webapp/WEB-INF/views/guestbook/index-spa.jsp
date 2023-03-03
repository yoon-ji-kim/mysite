<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/guestbook-spa.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	//1. 템플릿로딩 //2. 바인딩
	var listItemTemplate = new EJS({
		url: "${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs"
	});
	
	var listTemplate = new EJS({
		url: "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
	});
	
	var fetch = function(sno) {
		$.ajax({
			url:"${pageContext.request.contextPath}/guestbook/api?sno="+sno
			, type: "get"
			, dataType: "json"
			, success: function(response) {
				if(response.result === 'fail'){
					console.error(response.message);
					return;
				}
				//response.data.forEach(function(vo) {
				//	render(vo);
				//})
				var htmls = listTemplate.render(response);
				$('#list-guestbook').append(htmls);
			}
		})
	}
	
	var messageBox = function(title, message, callback) {
		$('#dialog-message p').text(message);
		$('#dialog-message').attr('title', title).dialog({
			modal:true
			,bottons: {
				"확인": function() {
					$(this).dialog('close');
				}
			}
			,close: callback
		});
	}
	$(function() {
		$(window).scroll(function() {
			var $window = $(this);
			var $document = $(document);
			
			var windowHeight = $window.height();
			var documentHeight = $document.height();
			var scrollTop = $window.scrollTop();
			
			if(documentHeight < windowHeight + scrollTop +5){
				console.log($('#list-guestbook li:last-child').data("no"));
				//console.log($('lastLi').data("no"));
				fetch($('#list-guestbook li:last-child').data("no"));
			}
		})
		
		fetch(0);
		
		$('#add-form').submit(function(event) {
			event.preventDefault();
			var vo = {};
			if($('#input-name').val() === '') {
				messageBox('방명록 등록','이름을 입력해주세요.', function() {
					$('#input-name').focus();
				});
				return;
			}
			if($('#input-password').val() === ''){
				messageBox('방명록 등록', '비밀번호를 입력해주세요.', function() {
					$('#input-password').focus();
				});
				return;
			}
			if($('#tx-content').val() === ''){
				messageBox('방명록 등록', '내용을 입력해주세요.', function() {
					$('#tx-content').focus();
				});
				return;
			}
			
			vo.name = $('#input-name').val();
			vo.password = $('#input-password').val();
			vo.message = $('#tx-content').val();
			$.ajax({
				url: "${pageContext.request.contextPath}/guestbook/api"
				, type: "post"
				, dataType: "json"
				, contentType: "application/json"
				, data: JSON.stringify(vo)
				, success: function(response) {
					if(response.result === 'fail'){
						console.error(response.message);
						return;
					}
					//수정****
					var htmls = listItemTemplate.render(response.data);
					$('#list-guestbook').prpend(htmls);
					$('#add-form')[0].reset();
				}
			})
		})
		
		var $dialogDelete = $('#dialog-delete-form').dialog({
			autoOpen: false
			, modal: true
			, buttons: {
				"삭제": function() {
					//console.log($('#hidden-no').val());
					//console.log($('#password-delete').val());
					$.ajax({
						url: "${pageContext.request.contextPath}/guestbook/api/"+$('#hidden-no').val()+'/'+$('#password-delete').val()
						, type: "delete"
						, dataType: "json"
						, success: function(response) {
							console.log(response.data);
							if(response.result === 'fail'){
								console.error(response.message);
								return;
							}
							if((response.data) === 1) {
								$('li[data-no='+$('#hidden-no').val()+']').remove()
								$dialogDelete.dialog('close');
								return;
							} else {
								//$('.validateTips error').show();
								$('#dialog-delete-form p:nth-child(2)').show();
								$('#password-delete').val('').focus();
							}
							//$(this).dialog('close');
							return;
						}
					})

				}
				,"취소": function() {
					$('#password-delete').val('');
					$(this).dialog('close');
				}
			}
		})
		
		$(document).on('click', '#list-guestbook li a', function() {
			event.preventDefault();
			//$(this).data('no');
			$('#hidden-no').val($(this).data('no'));
			$dialogDelete.dialog('open');
		})
	})
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<h1>방명록</h1>
				<form id="add-form" action="" method="post">
					<input type="text" id="input-name" placeholder="이름">
					<input type="password" id="input-password" placeholder="비밀번호">
					<textarea id="tx-content" placeholder="내용을 입력해 주세요."></textarea>
					<input type="submit" value="보내기" />
				</form>
				<ul id="list-guestbook"></ul>
			</div>
			<div id="dialog-delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">작성시 입력했던 비밀번호를 입력하세요.</p>
  				<p class="validateTips error" style="display:none">비밀번호가 틀립니다.</p>
  				<form>
 					<input type="password" id="password-delete" value="" class="text ui-widget-content ui-corner-all">
					<input type="hidden" id="hidden-no" value="">
					<input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
  				</form>
			</div>
			<div id="dialog-message" title="" style="display:none">
  				<p style='line-height: 60px'></p>
			</div>						
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="guestbook-ajax"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>