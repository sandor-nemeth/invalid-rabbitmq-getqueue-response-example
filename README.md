# [rabbitmq/hop]: Invalid RabbitMQ GetQueue Response handling

This repository demonstrates a behavior in the [rabbitmq/hop] library when the 
library receives a JSON response from RabbitMQ which it cannot deserialize. This
can happen for example when a client's registration in the RabbitMQ server is
broken, and the server returns values which the client cannot interpret.

[rabbitmq/hop]: https://github.com/rabbitmq/hop