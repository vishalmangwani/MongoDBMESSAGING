This is a very Simple Messaging Systtem with MongoDB and RabbitMQ

Whenever a new user is created.. A new Login is created for him with his own exchange in the Messaging System
Whenever he starts texting a new user in the system a queue is automatically built and added to the exchange

These exchanges and Queues are a web of connection like a graph which can be connected and messaging system can be built on it.

When a message is sent it is sent to the exchange and then to the User Queue.

The UI is very begginer type however the CSS can be modified as required

The Funcitionalities present are:
1) Create User
2) Log in User
3) Logout User
4) Maintain session
5) Message Users in the system
6) Rest API's to get active and Inactive users in the System
