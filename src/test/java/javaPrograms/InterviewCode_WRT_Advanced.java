package javaPrograms;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class InterviewCode_WRT_Advanced {

    // ============================================
    // LEVEL 1 — STRINGS (MISSING)
    // ============================================

    @Test
    public void firstNonRepeatingCharacter() {
        String input = "swiss";
        char result = firstNonRepeat(input);
        System.out.println("Input: " + input);
        System.out.println("First non-repeating character: " + result);
        // Output: w
    }

    public static char firstNonRepeat(String s) {
        Map<Character, Integer> map = new LinkedHashMap<>();

        for (char c : s.toCharArray())
            map.put(c, map.getOrDefault(c, 0) + 1);

        for (char c : map.keySet())
            if (map.get(c) == 1)
                return c;

        return '_';
    }

    // ============================================
    // LEVEL 2 — ARRAYS (MISSING)
    // ============================================

    @Test
    public void rotateArrayLeft() {
        int[] arr = {1, 2, 3, 4, 5};
        int k = 2;
        int[] result = rotateLeft(arr, k);
        System.out.println("Input: " + Arrays.toString(arr) + ", k=" + k);
        System.out.println("Rotated left: " + Arrays.toString(result));
        // Output: [3, 4, 5, 1, 2]
    }

    public int[] rotateLeft(int[] arr, int k) {
        int n = arr.length;
        k = k % n;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = arr[(i + k) % n];
        }
        return result;
    }

    @Test
    public void rotateArrayRight() {
        int[] arr = {1, 2, 3, 4, 5};
        int k = 2;
        int[] result = rotateRight(arr, k);
        System.out.println("Input: " + Arrays.toString(arr) + ", k=" + k);
        System.out.println("Rotated right: " + Arrays.toString(result));
        // Output: [4, 5, 1, 2, 3]
    }

    public int[] rotateRight(int[] arr, int k) {
        int n = arr.length;
        k = k % n;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[(i + k) % n] = arr[i];
        }
        return result;
    }

    @Test
    public void mergeTwoArrays() {
        int[] arr1 = {1, 3, 5, 7};
        int[] arr2 = {2, 4, 6, 8};
        int[] result = mergeArrays(arr1, arr2);
        System.out.println("Array1: " + Arrays.toString(arr1));
        System.out.println("Array2: " + Arrays.toString(arr2));
        System.out.println("Merged: " + Arrays.toString(result));
        // Output: [1, 2, 3, 4, 5, 6, 7, 8]
    }

    public int[] mergeArrays(int[] arr1, int[] arr2) {
        int[] merged = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, merged, 0, arr1.length);
        System.arraycopy(arr2, 0, merged, arr1.length, arr2.length);
        Arrays.sort(merged);
        return merged;
    }

    @Test
    public void maximumSubarraySum_Kadane() {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        int result = kadaneAlgorithm(arr);
        System.out.println("Input: " + Arrays.toString(arr));
        System.out.println("Maximum subarray sum: " + result);
        // Output: 6 (subarray [4, -1, 2, 1])
    }

    public int kadaneAlgorithm(int[] arr) {
        int maxSoFar = arr[0];
        int maxEndingHere = arr[0];

        for (int i = 1; i < arr.length; i++) {
            maxEndingHere = Math.max(arr[i], maxEndingHere + arr[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        return maxSoFar;
    }

    // ============================================
    // LEVEL 3 — COLLECTIONS (MISSING)
    // ============================================

    @Test
    public void sortHashMapByValue() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Apple", 5);
        map.put("Banana", 2);
        map.put("Orange", 8);
        map.put("Mango", 1);

        Map<String, Integer> sorted = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));

        System.out.println("Original: " + map);
        System.out.println("Sorted by value: " + sorted);
        // Output: {Mango=1, Banana=2, Apple=5, Orange=8}
    }

    @Test
    public void removeDuplicatesUsingSet() {
        List<Integer> list = Arrays.asList(1, 2, 2, 3, 4, 4, 5, 5, 5);
        Set<Integer> uniqueSet = new LinkedHashSet<>(list);
        List<Integer> uniqueList = new ArrayList<>(uniqueSet);

        System.out.println("Original: " + list);
        System.out.println("After removing duplicates: " + uniqueList);
        // Output: [1, 2, 3, 4, 5]
    }

    @Test
    public void iterateMapUsingStreams() {
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);

        System.out.println("Iterating using Streams:");
        map.entrySet().stream()
                .forEach(entry -> System.out.println(entry.getKey() + " -> " + entry.getValue()));
        // Output: A -> 1, B -> 2, C -> 3
    }

    @Test
    public void countFrequencyUsingMap() {
        List<String> words = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");

        Map<String, Long> frequency = words.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        System.out.println("Words: " + words);
        System.out.println("Frequency: " + frequency);
        // Output: {apple=3, banana=2, orange=1}
    }

    @Test
    public void convertListToMap() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");

        Map<String, Integer> map = list.stream()
                .collect(Collectors.toMap(Function.identity(), String::length));

        System.out.println("List: " + list);
        System.out.println("Map (word -> length): " + map);
        // Output: {apple=5, banana=6, cherry=6}
    }

    @Test
    public void findDuplicateElementsUsingStream() {
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 4, 5, 3, 6);

        Set<Integer> duplicates = list.stream()
                .filter(n -> Collections.frequency(list, n) > 1)
                .collect(Collectors.toSet());

        System.out.println("List: " + list);
        System.out.println("Duplicates: " + duplicates);
        // Output: [2, 3]
    }

    @Test
    public void customComparatorSorting() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        // Sort by length
        List<String> sortedByLength = names.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        // Sort by length descending
        List<String> sortedByLengthDesc = names.stream()
                .sorted(Comparator.comparingInt(String::length).reversed())
                .collect(Collectors.toList());

        System.out.println("Original: " + names);
        System.out.println("Sorted by length: " + sortedByLength);
        System.out.println("Sorted by length desc: " + sortedByLengthDesc);
        // Output: [Bob, Alice, David, Charlie] / [Charlie, Alice, David, Bob]
    }

    @Test
    public void threadSafeCollectionExample() {
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        CopyOnWriteArrayList<Integer> copyOnWriteList = new CopyOnWriteArrayList<>();

        synchronizedList.add(1);
        synchronizedList.add(2);
        copyOnWriteList.add(3);
        copyOnWriteList.add(4);

        System.out.println("Synchronized List: " + synchronizedList);
        System.out.println("CopyOnWriteArrayList: " + copyOnWriteList);
        // Output: [1, 2] / [3, 4]
    }

    @Test
    public void mergeTwoMaps() {
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("B", 3);
        map2.put("C", 4);

        Map<String, Integer> merged = new HashMap<>(map1);
        map2.forEach((k, v) -> merged.merge(k, v, Integer::sum));

        System.out.println("Map1: " + map1);
        System.out.println("Map2: " + map2);
        System.out.println("Merged (sum values): " + merged);
        // Output: {A=1, B=5, C=4}
    }

    // ============================================
    // LEVEL 4 — JAVA 8 STREAMS (MISSING)
    // ============================================

    @Test
    public void findDuplicateElementsStream() {
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 4, 5, 3, 6);

        Set<Integer> seen = new HashSet<>();
        List<Integer> duplicates = list.stream()
                .filter(n -> !seen.add(n))
                .distinct()
                .collect(Collectors.toList());

        System.out.println("List: " + list);
        System.out.println("Duplicates: " + duplicates);
        // Output: [2, 3]
    }

    @Test
    public void findMaxMinFromList() {
        List<Integer> list = Arrays.asList(5, 2, 8, 1, 9, 3);

        int max = list.stream().max(Integer::compareTo).orElse(-1);
        int min = list.stream().min(Integer::compareTo).orElse(-1);

        System.out.println("List: " + list);
        System.out.println("Max: " + max + ", Min: " + min);
        // Output: Max: 9, Min: 1
    }

    @Test
    public void convertListToUppercase() {
        List<String> list = Arrays.asList("apple", "banana", "cherry");

        List<String> upperCase = list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("Original: " + list);
        System.out.println("Uppercase: " + upperCase);
        // Output: [APPLE, BANANA, CHERRY]
    }

    @Test
    public void filterEvenNumbers() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> evenNumbers = list.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("Original: " + list);
        System.out.println("Even numbers: " + evenNumbers);
        // Output: [2, 4, 6, 8, 10]
    }

    @Test
    public void sortEmployeesBySalary() {
        class Employee {
            String name;
            int salary;
            Employee(String name, int salary) { this.name = name; this.salary = salary; }
            @Override public String toString() { return name + "=" + salary; }
        }

        List<Employee> employees = Arrays.asList(
                new Employee("Alice", 50000),
                new Employee("Bob", 60000),
                new Employee("Charlie", 45000)
        );

        List<Employee> sorted = employees.stream()
                .sorted(Comparator.comparingInt(e -> e.salary))
                .collect(Collectors.toList());

        System.out.println("Original: " + employees);
        System.out.println("Sorted by salary: " + sorted);
        // Output: [Charlie=45000, Alice=50000, Bob=60000]
    }

    @Test
    public void groupByDepartment() {
        class Employee {
            String name;
            String department;
            Employee(String name, String department) { this.name = name; this.department = department; }
            @Override public String toString() { return name; }
        }

        List<Employee> employees = Arrays.asList(
                new Employee("Alice", "IT"),
                new Employee("Bob", "HR"),
                new Employee("Charlie", "IT"),
                new Employee("David", "Finance")
        );

        Map<String, List<Employee>> grouped = employees.stream()
                .collect(Collectors.groupingBy(e -> e.department));

        System.out.println("Grouped by department: " + grouped);
        // Output: {IT=[Alice, Charlie], HR=[Bob], Finance=[David]}
    }

    @Test
    public void findSecondHighestSalary() {
        List<Integer> salaries = Arrays.asList(50000, 60000, 45000, 70000, 55000);

        Integer secondHighest = salaries.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .orElse(null);

        System.out.println("Salaries: " + salaries);
        System.out.println("Second highest: " + secondHighest);
        // Output: 60000
    }

    @Test
    public void flattenNestedList() {
        List<List<Integer>> nested = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4),
                Arrays.asList(5, 6)
        );

        List<Integer> flattened = nested.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        System.out.println("Nested: " + nested);
        System.out.println("Flattened: " + flattened);
        // Output: [1, 2, 3, 4, 5, 6]
    }

    @Test
    public void removeNullValues() {
        List<String> list = Arrays.asList("apple", null, "banana", null, "cherry");

        List<String> withoutNulls = list.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        System.out.println("Original: " + list);
        System.out.println("Without nulls: " + withoutNulls);
        // Output: [apple, banana, cherry]
    }

    @Test
    public void parallelStreamExample() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        long start = System.currentTimeMillis();
        List<Integer> evenNumbers = list.parallelStream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        long end = System.currentTimeMillis();

        System.out.println("Even numbers (parallel): " + evenNumbers);
        System.out.println("Time taken: " + (end - start) + "ms");
        // Output: [2, 4, 6, 8, 10]
    }

    // ============================================
    // LEVEL 5 — OOP PATTERNS (MISSING)
    // ============================================

    @Test
    public void singletonPattern() {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();

        System.out.println("Instance1: " + instance1);
        System.out.println("Instance2: " + instance2);
        System.out.println("Same instance: " + (instance1 == instance2));
        // Output: Same instance: true
    }

    static class Singleton {
        private static Singleton instance;

        private Singleton() {}

        public static synchronized Singleton getInstance() {
            if (instance == null)
                instance = new Singleton();
            return instance;
        }
    }

    @Test
    public void immutableClassExample() {
        ImmutablePerson person = new ImmutablePerson("John", 30);
        System.out.println("Name: " + person.getName());
        System.out.println("Age: " + person.getAge());
        // Output: Name: John, Age: 30
    }

    static final class ImmutablePerson {
        private final String name;
        private final int age;

        public ImmutablePerson(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
    }

    @Test
    public void interfaceVsAbstractClass() {
        Animal dog = new Dog();
        Animal cat = new Cat();

        dog.makeSound();
        cat.makeSound();
        // Output: Dog barks, Cat meows
    }

    interface Animal {
        void makeSound();
    }

    static class Dog implements Animal {
        public void makeSound() { System.out.println("Dog barks"); }
    }

    static class Cat implements Animal {
        public void makeSound() { System.out.println("Cat meows"); }
    }

    @Test
    public void dependencyInjectionExample() {
        Service service = new Service(new DatabaseRepository());
        service.saveData("Test data");
        // Output: Saving to database: Test data
    }

    interface Repository {
        void save(String data);
    }

    static class DatabaseRepository implements Repository {
        public void save(String data) {
            System.out.println("Saving to database: " + data);
        }
    }

    static class Service {
        private final Repository repository;

        public Service(Repository repository) {
            this.repository = repository;
        }

        public void saveData(String data) {
            repository.save(data);
        }
    }

    @Test
    public void factoryDesignPattern() {
        Shape circle = ShapeFactory.getShape("CIRCLE");
        Shape rectangle = ShapeFactory.getShape("RECTANGLE");

        circle.draw();
        rectangle.draw();
        // Output: Drawing Circle, Drawing Rectangle
    }

    interface Shape {
        void draw();
    }

    static class Circle implements Shape {
        public void draw() { System.out.println("Drawing Circle"); }
    }

    static class Rectangle implements Shape {
        public void draw() { System.out.println("Drawing Rectangle"); }
    }

    static class ShapeFactory {
        public static Shape getShape(String shapeType) {
            if (shapeType.equalsIgnoreCase("CIRCLE")) {
                return new Circle();
            } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
                return new Rectangle();
            }
            return null;
        }
    }

    // ============================================
    // LEVEL 6 — MULTITHREADING (MISSING)
    // ============================================

    @Test
    public void createThreadUsingRunnable() throws InterruptedException {
        Thread thread = new Thread(new MyRunnable());
        thread.start();
        thread.join();
        // Output: Thread is running
    }

    static class MyRunnable implements Runnable {
        public void run() {
            System.out.println("Thread is running");
        }
    }

    @Test
    public void synchronizationExample() throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Counter: " + counter.getCount());
        // Output: Counter: 2000
    }

    static class Counter {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public int getCount() { return count; }
    }

    @Test
    public void producerConsumerProblem() throws InterruptedException {
        Buffer buffer = new Buffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    buffer.produce(i);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) { e.printStackTrace(); }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    buffer.consume();
                    Thread.sleep(150);
                }
            } catch (InterruptedException e) { e.printStackTrace(); }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        // Output: Produced: 0, Consumed: 0, Produced: 1, etc.
    }

    static class Buffer {
        private int data;
        private boolean empty = true;

        public synchronized void produce(int value) throws InterruptedException {
            while (!empty) wait();
            data = value;
            empty = false;
            System.out.println("Produced: " + data);
            notifyAll();
        }

        public synchronized void consume() throws InterruptedException {
            while (empty) wait();
            System.out.println("Consumed: " + data);
            empty = true;
            notifyAll();
        }
    }

    @Test
    public void threadLocalUsage() {
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        Thread t1 = new Thread(() -> {
            threadLocal.set("Thread-1 value");
            System.out.println("Thread 1: " + threadLocal.get());
        });

        Thread t2 = new Thread(() -> {
            threadLocal.set("Thread-2 value");
            System.out.println("Thread 2: " + threadLocal.get());
        });

        t1.start();
        t2.start();
        // Output: Thread 1: Thread-1 value, Thread 2: Thread-2 value
    }

    @Test
    public void executorServiceExample() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            final int taskNum = i;
            executor.submit(() -> {
                System.out.println("Task " + taskNum + " executed by " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        // Output: Task 0 executed by pool-1-thread-1, etc.
    }
}
