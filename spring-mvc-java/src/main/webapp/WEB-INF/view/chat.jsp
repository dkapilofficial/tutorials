<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
	<head>
	    <title>Chat WebSocket</title>
	    
	    <spring:url value="/resources/js/sockjs-0.3.4.js" var="sockjs" />
	    <spring:url value="/resources/js/stomp.js" var="stomp" />
	    
	    <script src="${sockjs}"></script>
	    <script src="${stomp}"></script>
	    
	    <script type="text/javascript">
	    
	        var stompClient = null;
	        
	        function setConnected(connected) {
	        	
	            document.getElementById('connect').disabled = connected;
	            document.getElementById('disconnect').disabled = !connected;
	            document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
	            document.getElementById('response').innerHTML = '';
	        }
	        
	        function connect() {
	        	
	            var socket = new SockJS('/spring-mvc-java/chat');
	            stompClient = Stomp.over(socket);  
	            
	            stompClient.connect({}, function(frame) {
	                
	            	setConnected(true);
	                console.log('Connected: ' + frame);
	                stompClient.subscribe('/topic/message', function(messageOutput) {
	                    showMessageOutput(JSON.parse(messageOutput.body));
	                });
	            });
	        }
	        
	        function disconnect() {
	        	
	            if(stompClient != null) {
	                stompClient.disconnect();
	            }
	            
	            setConnected(false);
	            console.log("Disconnected");
	        }
	        
	        function sendMessage() {
	        	
	            var text = document.getElementById('text').value;
	            stompClient.send("/app/chat", {}, JSON.stringify({ 'text': text }));
	        }
	        
	        function showMessageOutput(messageOutput) {
	        	
	            var response = document.getElementById('response');
	            var p = document.createElement('p');
	            p.style.wordWrap = 'break-word';
	            p.appendChild(document.createTextNode(messageOutput.time + ": " + messageOutput.text));
	            response.appendChild(p);
	        }
	        
	    </script>
	    
	</head>
	
	<body onload="disconnect()">

		<div>
		    <div>
		        <button id="connect" onclick="connect();">Connect</button>
		        <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
		    </div>
		    <br />
		    <div id="conversationDiv">
		        <input type="text" id="text" placeholder="Write a message..."/>
		        <button id="sendMessage" onclick="sendMessage();">Send</button>
		        <p id="response"></p>
		    </div>
		</div>

	</body>
</html>