local connector = peripheral.wrap("left")

local methods = peripheral.getMethods("left")
for i, v in pairs(methods)
do
    print(i..". "..v)
end
print("")
