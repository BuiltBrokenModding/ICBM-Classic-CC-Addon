local args = {...}

local connector = peripheral.wrap("left")

local targetData = {
    ["x"] = tonumber(args[1]),
    ["y"] = tonumber(args[2]),
    ["z"] = tonumber(args[3])
}
local simulate = string.lower(args[4]) == 'true'

print("Firing At [" .. targetData["x"] .. "," .. targetData["y"] .. "," .. targetData["z"] .. "]")

local results, count, error = connector.launch(targetData, simulate)

print("Results:")

if error ~= null then
    local error, blocking, key, message = error
    print("\tFailed:  " .. message)
else
    for _, entry in pairs(results) do
        local x = entry["X"]
        local y = entry["Y"]
        local z = entry["Z"]
        local result = entry["RESULT"]
        local message = result[4]
        print("\tLauncher[" .. x .. "," .. y .. "," .. z .. "]: " .. message)
    end
end


