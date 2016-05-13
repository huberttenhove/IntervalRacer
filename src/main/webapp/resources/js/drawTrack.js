	function drawTrack(){
		drawSoftCurveTrackElement(200, 200, 1);
		drawHorizontalTrackElement(200, 0);
		drawHorizontalTrackElement(400, 0);
		drawSoftCurveTrackElement(600, 200, 1.5);
		drawSoftCurveTrackElement(600, 200, 0);
		drawHorizontalTrackElement(400, 200);
		drawSoftCurveTrackElement(400, 400, 0.5);
		drawRectangle(200,400,200,200,"white");
		drawSoftCurveTrackElement(200, 400, 0);
		drawSoftCurveTrackElement(200, 400, 0.5);
		drawVerticalTrackElement(0, 200);
	}
	
	function drawHorizontalTrackElement(x, y){
		drawRectangle(x, y, 200, 200, "black");
		drawRectangle(x, y + 15, 200, 170, "green");
		drawRectangle(x, y + 50, 200, 100, "grey");
	}
	
	function drawVerticalTrackElement(x, y){
		drawRectangle(x, y, 200, 200, "black");
		drawRectangle(x + 15, y, 170, 200, "green");
		drawRectangle(x  + 50, y, 100, 200, "grey");
	}
	
	function drawSoftCurveTrackElement(x, y, angle){
		drawCirkel(x, y, 200, angle, "black");
		drawCirkel(x, y, 185, angle, "green");
		drawCirkel(x, y, 150, angle, "grey");
		drawCirkel(x, y, 50, angle, "green");
		drawCirkel(x, y, 15, angle, "black");
	}
	
	function drawRectangle(x, y, w, h, style){
		context.fillStyle = style;
		context.fillRect(x, y, w, h);
	}

	function drawCirkel(x, y, r, angle, style){
		context.beginPath();
	    context.arc(x, y, r, Math.PI * angle, Math.PI * (1 + angle), false);
	    context.fillStyle = style;
	    context.fill();
	}