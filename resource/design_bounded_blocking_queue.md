1188. Design Bounded Blocking Queue
      Description
      Implement a thread-safe bounded blocking queue that has the following methods:

BoundedBlockingQueue(int capacity) The constructor initializes the queue with a maximum capacity.
void enqueue(int element) Adds an element to the front of the queue. If the queue is full, the calling thread is blocked until the queue is no longer full.
int dequeue() Returns the element at the rear of the queue and removes it. If the queue is empty, the calling thread is blocked until the queue is no longer empty.
int size() Returns the number of elements currently in the queue.
Your implementation will be tested using multiple threads at the same time. Each thread will either be a producer thread that only makes calls to the enqueue method or a consumer thread that only makes calls to the dequeue method. The size method will be called after every test case.

Please do not use built-in implementations of bounded blocking queue as this will not be accepted in an interview.



Example 1:

Input:
1
1
["BoundedBlockingQueue","enqueue","dequeue","dequeue","enqueue","enqueue","enqueue","enqueue","dequeue"]
[[2],[1],[],[],[0],[2],[3],[4],[]]

Output:
[1,0,2,2]

Explanation:
Number of producer threads = 1
Number of consumer threads = 1

BoundedBlockingQueue queue = new BoundedBlockingQueue(2);   // initialize the queue with capacity = 2.

queue.enqueue(1);   // The producer thread enqueues 1 to the queue.
queue.dequeue();    // The consumer thread calls dequeue and returns 1 from the queue.
queue.dequeue();    // Since the queue is empty, the consumer thread is blocked.
queue.enqueue(0);   // The producer thread enqueues 0 to the queue. The consumer thread is unblocked and returns 0 from the queue.
queue.enqueue(2);   // The producer thread enqueues 2 to the queue.
queue.enqueue(3);   // The producer thread enqueues 3 to the queue.
queue.enqueue(4);   // The producer thread is blocked because the queue's capacity (2) is reached.
queue.dequeue();    // The consumer thread returns 2 from the queue. The producer thread is unblocked and enqueues 4 to the queue.
queue.size();       // 2 elements remaining in the queue. size() is always called at the end of each test case.
Example 2:

Input:
3
4
["BoundedBlockingQueue","enqueue","enqueue","enqueue","dequeue","dequeue","dequeue","enqueue"]
[[3],[1],[0],[2],[],[],[],[3]]
Output:
[1,0,2,1]

Explanation:
Number of producer threads = 3
Number of consumer threads = 4

BoundedBlockingQueue queue = new BoundedBlockingQueue(3);   // initialize the queue with capacity = 3.

queue.enqueue(1);   // Producer thread P1 enqueues 1 to the queue.
queue.enqueue(0);   // Producer thread P2 enqueues 0 to the queue.
queue.enqueue(2);   // Producer thread P3 enqueues 2 to the queue.
queue.dequeue();    // Consumer thread C1 calls dequeue.
queue.dequeue();    // Consumer thread C2 calls dequeue.
queue.dequeue();    // Consumer thread C3 calls dequeue.
queue.enqueue(3);   // One of the producer threads enqueues 3 to the queue.
queue.size();       // 1 element remaining in the queue.

Since the number of threads for producer/consumer is greater than 1, we do not know how the threads will be scheduled in the operating system, even though the input seems to imply the ordering. Therefore, any of the output [1,0,2] or [1,2,0] or [0,1,2] or [0,2,1] or [2,0,1] or [2,1,0] will be accepted.


Constraints:

1 <= Number of Prdoucers <= 8
1 <= Number of Consumers <= 8
1 <= size <= 30
0 <= element <= 20
The number of calls to enqueue is greater than or equal to the number of calls to dequeue.
At most 40 calls will be made to enque, deque, and size.
Solutions
Python’s threading module provides synchronization primitives that are helpful for such concurrency problems, including Locks, Conditions, and Semaphores. For this particular problem, using a Condition object is a suitable choice because it allows threads to wait for certain conditions to be met.

threading.Semaphore
This problem can be solved using semaphores. In the class, create two semaphores semaphoreEnqueue and semaphoreDequeue for methods enqueue() and dequeue() respectively. Also maintain capacity and queue.

For the constructor, initialize capacity using the given capacity, initialize queue, and initialize semaphoreEnqueue to have capacity permits and semaphoreDequeue to have 0 permits.


For method enqueue, acquire a permit from semaphoreEnqueue, offer element to queue and release a permit back to semaphoreDequeue.

For method dequeue, acquire a permit from semaphoreDequeue, poll one element from queue and release a permit back to semaphoreEnqueue. Finally, return the polled element.

For method size, simply return queue.size().

threading.Condition
__init__(self, capacity: int): Initializes the queue with the given capacity. The self.queue list holds the elements in the queue. self.condition is a Condition object used to manage concurrent access to the queue.

enqueue(self, element: int) -> None: Adds an element to the queue. If the queue is full (i.e., its length equals the capacity), the thread calling this method will wait until another thread makes space by calling dequeue. The with self.condition: context manager acquires a lock associated with the condition. self.condition.wait() releases the lock and blocks until self.condition.notify() is called by another thread. After adding the element to the queue, it calls self.condition.notify() to wake up one of the threads waiting, if any.

dequeue(self) -> int: Removes and returns an element from the front of the queue. If the queue is empty, the thread calling this method will wait until another thread adds an element by calling enqueue. Similar to enqueue, it uses self.condition.wait() to wait for the condition and self.condition.notify() to signal that the condition has been met.

size(self) -> int: Returns the number of elements in the queue. Access to the queue’s length is also synchronized using the condition variable to ensure thread safety.

Key Points:

The use of threading.Condition allows threads to wait for certain conditions (the queue being not full for enqueue, and not empty for dequeue) in a thread-safe way.
self.condition.notify() wakes up one of the threads waiting for the condition, if any. There’s also a notify_all() method that wakes up all waiting threads, but it’s not necessary for this implementation.
This implementation ensures that operations on the queue are blocked appropriately according to the queue’s state (full or empty) and only proceed when the queue is in a state that allows them to complete successfully.
Java
C++
Python
class BoundedBlockingQueue {
private Semaphore s1;
private Semaphore s2;
private Deque<Integer> q = new ArrayDeque<>();

    public BoundedBlockingQueue(int capacity) {
        s1 = new Semaphore(capacity);
        s2 = new Semaphore(0);
    }

    public void enqueue(int element) throws InterruptedException {
        s1.acquire();
        q.offer(element);
        s2.release();
    }

    public int dequeue() throws InterruptedException {
        s2.acquire();
        int ans = q.poll();
        s1.release();
        return ans;
    }

    public int size() {
        return q.size();
    }
}