	function drawTrack(){
		drawSoftCurveTrackElement(200, 200, 1);
		drawHorizontalTrackElement(200, 0, "grey");
		drawHorizontalTrackElement(400, 0, "darkgrey");
		drawSoftCurveTrackElement(600, 200, 1.5);
		drawSoftCurveTrackElement(600, 200, 0);
		drawHorizontalTrackElement(400, 200, "darkgrey");
		drawSoftCurveTrackElement(400, 400, 0.5);
		drawRectangle(200,400,200,200,"white");
		drawSoftCurveTrackElement(200, 400, 0);
		drawSoftCurveTrackElement(200, 400, 0.5);
		drawFinishElement(0, 200);
	}
	
	function drawHorizontalTrackElement(x, y, roadColor){
		drawRectangle(x, y, 200, 200, "black");
		drawRectangle(x, y + 15, 200, 170, "green");
		drawRectangle(x, y + 50, 200, 100, roadColor);
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
	
	function drawFinishElement(x, y){
		drawVerticalTrackElement(x, y);
		drawRectangle(x + 50, y, 25, 50, "black");
		drawRectangle(x + 75, y, 25, 50, "white");
		drawRectangle(x + 100, y, 25, 50, "black");
		drawRectangle(x + 125, y, 25, 50, "white");
		
		drawRectangle(x + 50, y + 50, 25, 50, "white");
		drawRectangle(x + 75, y + 50, 25, 50, "black");
		drawRectangle(x + 100, y + 50, 25, 50, "white");
		drawRectangle(x + 125, y + 50, 25, 50, "black");
		
		drawRectangle(x + 50, y + 100, 25, 50, "black");
		drawRectangle(x + 75, y + 100, 25, 50, "white");
		drawRectangle(x + 100, y + 100, 25, 50, "black");
		drawRectangle(x + 125, y + 100, 25, 50, "white");
		
		drawRectangle(x + 50, y + 150, 25, 50, "white");
		drawRectangle(x + 75, y + 150, 25, 50, "black");
		drawRectangle(x + 100, y + 150, 25, 50, "white");
		drawRectangle(x + 125, y + 150, 25, 50, "black");
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