local launcher = peripheral.wrap("left")
local loadedMissile = launcher.getMissiles()
local methods = peripheral.getMethods("left")

print("Missile: " .. loadedMissile)

for i, v in pairs(methods) 
do 
    print(i..". "..v) 
end

print("")

local energy, maxEnergy = launcher.getEnergyData()
print("Battery: " .. energy .. "/" .. maxEnergy)

print("")

local x, y, z = launcher.getTarget();
print("Target: " .. x .. " " .. y .. " " .. z)
print("Aimed: " .. tostring(launcher.isAimed()))
local yaw, pitch, roll = launcher.getAimCurrent()
print("Aim: " .. yaw .. " " .. pitch)
yaw, pitch, roll = launcher.getAimTarget()
print("Aim Target: " .. yaw .. " " .. pitch)

print("")
launcher.setTarget(20, 0, 40)

x, y, z = launcher.getTarget();
print("Target: " .. x .. " " .. y .. " " .. z)
print("Aimed: " .. tostring(launcher.isAimed()))
yaw, pitch, roll = launcher.getAimCurrent()
print("Aim Current: " .. yaw .. " " .. pitch)
yaw, pitch, roll = launcher.getAimTarget()
print("Aim Target: " .. yaw .. " " .. pitch)


local targetData = {
     ["x"] = 100,
     ["y"] = 10,
     ["z"] = 101,
     ["delay"] = 5
}

print("Inaccuracy: " .. launcher.getInaccuracy(targetData))

local errored2, blocked2, key2, message2 = launcher.getStatus();
print("Status: " .. key2 .. ", " .. message2);

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
