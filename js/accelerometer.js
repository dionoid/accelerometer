var hasNativeEvents = NATIVE && NATIVE.plugins && NATIVE.plugins.sendEvent;

var handleAccel = null;

NATIVE.events.registerHandler('accelerometerEvent', function(evt) {
	if (handleAccel) {
		// evt.x, evt.y, evt.z are scaled x,y,z acceleration ranging from -1 to 1.
		handleAccel(evt);
	}
});

var Accelerometer = Class(function () {
	this.start = function(handler) {
		if (hasNativeEvents) {
			NATIVE.plugins.sendEvent("AccelerometerPlugin", "startEvents",
				JSON.stringify({}));
		}
		handleAccel = handler;
	};

	this.stop = function() {
		if (hasNativeEvents) {
			NATIVE.plugins.sendEvent("AccelerometerPlugin", "stopEvents",
				JSON.stringify({}));
		}
	};

});

exports = new Accelerometer();
