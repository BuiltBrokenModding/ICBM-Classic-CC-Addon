local connector = peripheral.wrap("left")

local methods = peripheral.getMethods("left")
for i, v in pairs(methods)
do
    print(i..". "..v)
end
print("")

local targetData = {
    ["x"] = 100,
    ["y"] = 10,
    ["z"] = 101,
    ["delay"] = 5
}

local launchers, lCount, error = connector.getLaunchers()

if error ~= null then
    print("Error: " .. error)
else
    print("Launchers: " .. lCount)

    for _, entry in pairs(launchers) do
        local group = entry["GROUP_ID"]
        local index = entry["GROUP_INDEX"]
        local launcher = entry["LAUNCHER"]

        print("\tLauncher[" .. group .. "," .. index .. "]")

        local energy, maxEnergy = launcher.getEnergyData()
        print("\t\tBattery: " .. energy .. "/" .. maxEnergy)

        print("\t\tInaccuracy: " .. launcher.getInaccuracy(targetData))

        local errored2, blocked2, key2, message2 = launcher.getStatus();
        print("\t\tStatus: " .. message2);

        local errored3, blocked3, key3, message3 = launcher.preCheckLaunch(targetData);
        print("\t\tPreCheck: " .. message3)

        local errored, blocked, key, message = launcher.launch(targetData, true)
        print("\t\t" .. message)
    end
end

