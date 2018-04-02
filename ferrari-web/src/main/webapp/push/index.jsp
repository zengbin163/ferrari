<script type="text/javascript" src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
<script type="text/javascript" src="jquery.min.js"></script>
<script>
	var websocket;
	if ('WebSocket' in window) {
		//websocket = new WebSocket("ws://127.0.0.1:8080/ferrari/webSocketServer?sessionId=bW9iaWxlPWw1dllhc2h3bU0xNi9RdXFBRUlWOVE9PXxwbGF0Zm9ybT1URVNMQXxpcEFkZHJlc3M9MTI3LjAuMC4x");
		websocket = new WebSocket("ws://114.55.225.207/ferrari/webSocketServer?sessionId=bW9iaWxlPWw1dllhc2h3bU0xNi9RdXFBRUlWOVE9PXxwbGF0Zm9ybT1URVNMQXxpcEFkZHJlc3M9MTI3LjAuMC4x");
	} else if ('MozWebSocket' in window) {
		//websocket = new MozWebSocket("ws://127.0.0.1:8080/ferrari/webSocketServer?sessionId=bW9iaWxlPWw1dllhc2h3bU0xNi9RdXFBRUlWOVE9PXxwbGF0Zm9ybT1URVNMQXxpcEFkZHJlc3M9MTI3LjAuMC4x");
		websocket = new MozWebSocket("ws://114.55.225.207/ferrari/webSocketServer?sessionId=bW9iaWxlPWw1dllhc2h3bU0xNi9RdXFBRUlWOVE9PXxwbGF0Zm9ybT1URVNMQXxpcEFkZHJlc3M9MTI3LjAuMC4x");
	} else {
		//websocket = new SockJS("http://127.0.0.1:8080/ferrari/sockjs/webSocketServer?sessionId=bW9iaWxlPWw1dllhc2h3bU0xNi9RdXFBRUlWOVE9PXxwbGF0Zm9ybT1URVNMQXxpcEFkZHJlc3M9MTI3LjAuMC4x");
		websocket = new SockJS("http://114.55.225.207/ferrari/sockjs/webSocketServer?sessionId=bW9iaWxlPWw1dllhc2h3bU0xNi9RdXFBRUlWOVE9PXxwbGF0Zm9ybT1URVNMQXxpcEFkZHJlc3M9MTI3LjAuMC4x");
	}
	websocket.onopen = function(evnt) {
	};
	websocket.onmessage = function(evnt) {
		$("#msgcount").html(evnt.data);
	};
	websocket.onerror = function(evnt) {
	};
	websocket.onclose = function(evnt) {
	}
</script>
<span id="msgcount"></span>