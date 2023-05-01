# INFO

This log contains changes made to the project. Each entry contains changed made after the last version but before the number was changed. Any changes made after a number change are considered part of the next release. This is regardless if versions are still being released with that version number attached. 

# Versions

## 1.0.2 - May 1st, 2023

* FIxed: version metadata

## 1.0.1 - May 1st, 2023

* Fixed: dependencies on CC and ICBM not being set in metadata

## 1.0.0 - April 30th, 2023

Init release

As a note most methods are shared over all machines. Such as `machine` being the base for all tiles. As well `launcher` being the base for all launcher blocks.

* Added: general machine commands that can be extended
* Added: general launcher commands that can be extended, extends machine
* Added: support for radar, extends machine
* Added: support for launcher base, extends launcher
* Added: support for launcher cruise, extends launcher
* Added: machine#getEnergyData() returning {energy, maxEnergy} whole numbers with ForgeEnergy as the base
* Added: machine#getEnergy() returning {energy}
* Added: machine#getEnergyLimit() returning {maxEnergy}
* Added: machine#getMachineInfo() returning a table of general machine settings, facts, and properties
* Added: machine info property "ENERGY_COST" showing per tick consumption rate. Not all machines provide this correct and will need more work
* Added: machine info property "INVENTORY_SIZE" showing slot count
* Added: radar#getDetectionRange() returning range in meters of detection
* Added: radar#setDetectionRange(range) allowing setting detection range
* Added: radar#getTriggerRange() returning range in meters to trigger alarm for incoming threats
* Added: radar#setTriggerRange(range) allowing setting trigger range
* Added: radar#getContacts(true/false(true=threats), returnCountLimit(optional)) returning table{i=entry} of entities, each entry is a table itself containg (X,Y,Z,SIZE_H,SIZE_W).
* Added: launcher#getMissiles() returning current missile, empty, or null. Future this will be an array
* Added: launcher#launch(targetData(Table: X, Y, Z, Delay), simulate(true/false)) to fire the missile, returns status message {isError, isBlocking, messageKey, messageTranslatedLocal}
* Added: launcher#getInaccuracy(targetData(Table: X, Y, Z, Delay), launcherCount) returning added inaccuracy in meters
* Added: launcher#getStatus() returning status message of launcher's current state, same output as #launch
* Added: launcher#preCheckLaunch(targetData(Table: X, Y, Z, Delay)) returning pre-flight checks on missile launch only, same output as #launch
* Added: cruise#getTarget() returning {x, y, z}
* Added: cruise#setTarget(x, y, z)
* Added: cruise#isAimed() returning true if aimed at target
* Added: cruise#getAimCurrent(asRadians(true)) returning {yaw, pitch, roll} of current turret position, doesn't always fully align and isn't wrapped
* Added: cruise#getAimTarget(asRadians(true)) returning {yaw, pitch, roll} of predicted target position
* Added: base#getLockHeight() returning lock height in meters the missile will move before changing direction
* Added: base#setLockHeight(meters) allows setting lock height
* Added: base#getFiringDelay() returning delay in ticks before triggering launch of the missile, does not modify startup timers and other delays
* Added: base#setFiringDelay(ticks) allows setting delay, #launch targetData can add to this delay

