# Game Closure Devkit Plugin : Accelerometer

This module provides preliminary support for the built-in accelerometer on iOS and Android devices.

Installation instructions:

Run this command:

~~~
basil install accelerometer
~~~

Example usage from inside a JS game:

~~~
import plugins.accelerometer.accelerometer as accelerometer;
~~~

~~~
	accelerometer.start(bind(this, function(evt) {
			var x = evt.x;
			var y = evt.y;
			var z = evt.z;
	}));

...

	accelerometer.stop();
~~~

The Accelerometer plugin also includes a ShakeDetect JS module.

Example usage:

~~~
import plugins.accelerometer.ShakeDetect as ShakeDetect;
~~~

~~~
ShakeDetect.start(function() {
		logger.log("User shook the phone").
});

...

ShakeDetect.stop();
~~~

Be sure to call .stop() when done handling events.  Also, only one event handler
can be specified of either type at a time.
