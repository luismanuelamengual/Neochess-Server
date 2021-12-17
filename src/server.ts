const socketServer = require('http').createServer();
const io = require('socket.io')(socketServer, {});
const socketPort = 5000;

io.on('connection', client => {
    console.log('New incoming Connection from', client.id);
    client.on('testMsg', (message) => {
        console.log('Message from the client:',client.id,'->',message);
    })
});
socketServer.listen(8080);
