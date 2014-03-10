#import "AccelerometerPlugin.h"
#import <CoreMotion/CoreMotion.h>

CMMotionManager* motionManager;

@implementation AccelerometerPlugin

- (void) dealloc {
	[super dealloc];
}

- (id) init {
	self = [super init];
	if (!self) {
		return nil;
	}

	NSLog(@"{accelerometer} Initializing");

	motionManager = [[CMMotionManager alloc] init];
    [motionManager stopAccelerometerUpdates];

	return self;
}

- (void) startEvents:(NSDictionary *)jsonObject {
	@try {
        if (motionManager.isAccelerometerAvailable) {
            NSLog(@"{accelerometer} Starting");
            [motionManager stopAccelerometerUpdates];
            [motionManager setAccelerometerUpdateInterval:1/40.0];
            [motionManager startAccelerometerUpdatesToQueue:[[NSOperationQueue alloc] init]
                                                withHandler:^(CMAccelerometerData *accelerometerData, NSError *error) {
                [[PluginManager get] dispatchJSEvent:@{
                                                       @"name": @"accelerometerEvent",
                                                       @"x": @(accelerometerData.acceleration.x),
                                                       @"y": @(accelerometerData.acceleration.y),
                                                       @"z": @(accelerometerData.acceleration.z)
                                                       }];
            }];
        }
	}
	@catch (NSException *exception) {
		NSLog(@"{accelerometer} WARNING: Unable to start events: %@", exception);
	}
}

- (void) stopEvents:(NSDictionary *)jsonObject {
	@try {
		[motionManager stopAccelerometerUpdates];
		NSLog(@"{accelerometer} Stopped");
	}
	@catch (NSException *exception) {
		NSLog(@"{accelerometer} WARNING: Unable to stop events: %@", exception);
	}
}

@end
