local launcher = peripheral.wrap("left")
local loadedMissile = launcher.getMissiles()
local methods = peripheral.getMethods("left")

print("Missile: " .. loadedMissile)

for i, v in pairs(methods) 
do 
    print(i..". "..v) 
end

print("")

print("Lock Height: " .. launcher.getLockHeight())
launcher.setLockHeight(3)
print("Lock Height: " .. launcher.getLockHeight())

print("")

print("Firing Delay: " .. launcher.getFiringDelay())
launcher.setFiringDelay(15)
print("Firing Delay: " .. launcher.getFiringDelay())

local energy, maxEnergy, costEnergy = launcher.getEnergyData()
print("Battery: " .. energy .. "/" .. maxEnergy)


local targetData = {
     ["x"] = 100,
     ["y"] = 10,
     ["z"] = 101,
     ["delay"] = 5
}

print("Inaccuracy: " .. launcher.getInaccuracy(targetData))

local errored2, blocked2, key2, message2 = launcher.getStatus();
print("Status: " .. message2);

local errored3, blocked3, key3, message3 = launcher.preCheckLaunch(targetData);
print("PreCheck: " .. message3)

if(loadedMissile == null)
then 
 print("unsupported")
elseif (loadedMissile == "empty")
then
 print("no missile loaded")
else
 local errored, blocked, key, message = launcher.launch(targetData, true)
 print(message)
end
