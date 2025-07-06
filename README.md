# 📨 Pub-Sub In-Memory System

An in-memory publish-subscribe architecture built in Java to simulate how messages are produced, stored, and delivered to subscribers via polling and publishing mechanisms.

---

## 📐 Architecture Overview

```text
Producer ➡️ MessageStore ➡️ MessagePoller ➡️ MessagePublisher ➡️ Subscribers
```

🧭 Class Diagram
[!Class Diagram](https://github.com/sargarpramod-rgb/pub-sub-in-memory/blob/5d7b85c0740dd369129acee1b4c3852f824de1eb/src/resources/InMemorySubscriberArchitecture.png)

### 🔸 MessageProducer

* Responsible for generating and pushing messages to the `MessageStore`.

### 🔸 MessageStore

* Implemented as a **Singleton**.
* Internally maintains a `Map<Topic, LinkedBlockingQueue<Message>>`.

#### Operations:

* `addMessage(topic, message)` – Adds a message to the queue for the given topic.
* `readAllMessages(topic)` – Reads all available messages from the queue.

### 🔸 MessagePoller

* One poller per topic (topic swim-laning).
* Continuously monitors the message queue for a topic.
* Waits until a new message arrives, then pushes it to the `MessagePublisher`.

> 🔍 Investigate Java's `wait()` and queue blocking mechanisms for efficient waiting.

### 🔸 MessagePublisher

* Publishes incoming messages to all registered subscribers of the topic.
* Uses the `TopicRegistry` for resolving subscribers.

### 🔸 TopicRegistry

* Singleton class that maintains `Map<Topic, List<Subscriber>>`.

#### Operations:

* `addSubscriber(topic, subscriber)`
* `removeSubscriber(topic, subscriber)`

### 🔸 Subscriber

* Implements an `onMessage(Message)` method to receive and process messages.

---

## 🚀 Planned Enhancements

### ✅ High-Volume Message Generation

* Simulate a high-throughput producer that continuously generates random messages.

### ✅ Design Pattern Exploration

* Investigate alternate patterns, e.g., **Observer Pattern**, in addition to Singleton.

### ✅ Offline Subscriber Handling & Retry Queue

* If a subscriber is offline when a message is published:

  * Message is added to a **RetryQueue**.
  * A **RetryProcessor** attempts delivery every 10 seconds.
  * After **15 failed attempts**, the message is moved to a **FailedQueue**.

### ✅ Subscriber Online/Offline Simulation

* Simulate dynamic availability:

  * `Subscriber 1`: Offline after 20s, stays offline for 1 min, then comes back online.
  * `Subscriber 2`: Permanently offline.
  * `Subscriber 3`: Always online.

> For now, use hardcoded values or flags (optionally stored in-memory).

### ✅ Rate Limiting

* Implement per-subscriber **rate limits** using a library like [Bucket4j](https://github.com/vladimir-bukhtoyarov/bucket4j).
* Subscribers should **pause message consumption** upon exceeding their rate limit.

### ✅ Bulk Read and Batch Publishing

* Instead of immediate push on message arrival, implement **batch-based publishing**.
* Explore advanced APIs of `LinkedBlockingQueue`, such as `drainTo()`.

### ✅ Java Generics

* Refactor key components (e.g., `MessageStore`, `Publisher`) to support generics for type-safe message handling.

---

## 📦 Tech Stack

* Java (Concurrency utilities: `LinkedBlockingQueue`, `Executors`)
* In-memory data structures (maps, queues)
* Design Patterns: Singleton, Observer (planned)
* Optional: Rate limiting libraries

---

## 📌 TODO List

* [ ] Add unit tests for core components
* [ ] Logging framework integration
* [ ] Performance benchmark (for batch vs. real-time publishing)
* [ ] Explore integration with Kafka for persistent queues (future scope)

---

