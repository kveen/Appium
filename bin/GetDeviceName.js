var iosDevice = require('/usr/local/lib/node_modules/node-ios-device');
iosDevice.devices(function (err, devices) {
//console.log(devices);
devices.forEach(function (device) {
console.log(device.name);
});
});
