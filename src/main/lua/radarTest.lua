local radar = peripheral.wrap("right")


print("Methods: ")
local methods = peripheral.getMethods("right")
for i, v in pairs(methods)
do
    print(i..". "..v)
end

print("")

print("Info: ")
local info = radar.getMachineInfo()
for key, value in pairs(info)
do
    print("\t" .. key .. " = " .. tostring(value))
end

print("")

local energy, maxEnergy = radar.getEnergyData()
print("Energy Data: " .. energy .. "/" .. maxEnergy)

energy = radar.getEnergy();
print("Energy: " .. energy)

maxEnergy = radar.getEnergyLimit();
print("EnergyLimit: " .. energy)

local detectionRange = radar.getDetectionRange()
print("DetectionRange: " .. detectionRange)

radar.setDetectionRange(500);

detectionRange = radar.getDetectionRange()
print("DetectionRange: " .. detectionRange)

print("")

local triggerRange = radar.getTriggerRange()
print("TriggerRange: " .. triggerRange)

radar.setTriggerRange(200);

triggerRange = radar.getTriggerRange()
print("TriggerRange: " .. triggerRange)

print("")

local contacts, count = radar.getContacts(false)

print("Detected: " .. count)
for key, value in pairs(contacts)
do
    print(key .. ". ")
    for key2, value2 in pairs(value)
    do
        print("\t" .. key2 .. " = " .. value2)
    end
end

print("")

contacts, count = radar.getContacts(true)

print("Trigger: " .. count)
for key, value in pairs(contacts)
do
    print(key .. ". ")
    for key2, value2 in pairs(value)
    do
        print("\t" .. key2 .. " = " .. value2)
    end
end
