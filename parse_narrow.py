import sys

f = open(sys.argv[1]).read().split("\n")
searchTime075 = 0
searchTime05 = 0
searchTime025 = 0

for i in f:
    if "0.75" in i:
        searchTime075 += float(i.replace("0.75:compareTime:", "").replace("msec", ""))
    elif "0.5" in i:
        searchTime05 += float(i.replace("0.5:compareTime:", "").replace("msec", ""))
    elif "0.25" in i:
        searchTime025 += float(i.replace("0.25:compareTime:", "").replace("msec", ""))


print searchTime075
print searchTime05
print searchTime025
