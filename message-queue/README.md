# Webhook handling via a message queue

This is a simple example of how to handle webhooks via a message queue. It uses
[Kafka](https://kafka.apache.org/) as the message queue provider.

## Running the example

Start the Kafka docker image.

```shell
docker-compose up -d
```

Start the server.

```shell
 ./gradlew :message-queue:bootRun
```

Submit a webhook request.

* Non-blocking via Kafka [/webhook](../http-requests/send-and-receive-event.http)
* Blocking without a message queue [/webhook/blocking](../http-requests/send-and-receive-event.http)

Notice that the server logs the received webhook request and the time it took to process.

## How it works

The server exposes a `/webhook` endpoint that accepts a webhook request.
The processing workflow is as follows:

1. The server receives a webhook request.
2. The server verifies the request using the signature and the timestamp headers.
3. Valid requests are processed and the server responds with a `200 OK` status code.

The difference between the blocking and non-blocking endpoints is that the blocking endpoint
naively waits for the processing to finish before responding with a `200 OK` status code.

The non-blocking endpoint uses a message queue to handle the webhook request. The server
publishes the request to the queue and immediately responds with a `200` status code.

## Processing delays

There's an intentional "gotcha" in the request processing. The service responsible for
handling of the webhook request is intentionally slow. This is to simulate a real-world
scenario where the webhook request processing might take a long time due to performance issues.

With a non-blocking approach, the webhook request is processed in the background and the
client receives a `200 OK` status code immediately. The console output looks similar to this:

```text
POST http://localhost:8080/webhook

HTTP/1.1 200 OK
content-length: 0

<Response body is empty>

Response code: 200 (OK); Time: 10ms (10 ms); Content length: 0 bytes (0 B)
```

With a blocking approach, the webhook request is processed synchronously and the client
takes the full toll of the sluggish processing. The console output looks similar to this:

```text
POST http://localhost:8080/webhook/blocking

HTTP/1.1 200 OK
content-length: 0

<Response body is empty>

Response code: 200 (OK); Time: 5017ms (5 s 17 ms); Content length: 0 bytes (0 B)
```
