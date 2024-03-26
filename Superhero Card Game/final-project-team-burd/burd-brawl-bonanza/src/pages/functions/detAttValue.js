// Determines attribute value; just returns the numeric value if not null, else returns a random number
function detAttValue(attVal) {
if (attVal === "null") {
    return Math.floor(Math.random() * 61) + 20
}
return attVal;
}

module.exports = detAttValue;