package javaPrograms;

public class New_Interview_QuestionsFromGPT {
    //TODO LEVEL 1 — Core Java Logic (Must Clear Round 1)
    //Strings (Very Frequently Asked)
    //Reverse a string (without inbuilt function)
    //Check palindrome string
    //Count vowels & consonants
    //Remove duplicate characters
    //Find first non-repeating character -- using LinkedHashMap
    //Count occurrence of each character
    //Anagram check
    //Reverse words in sentence
    //Longest substring without repeating characters
    //String compression (aaabb → a3b2)
    //
    //Example:
    //
    //public static char firstNonRepeat(String s){
    //    Map<Character,Integer> map = new LinkedHashMap<>();
    //
    //    for(char c : s.toCharArray())
    //        map.put(c,map.getOrDefault(c,0)+1);
    //
    //    for(char c : map.keySet())
    //        if(map.get(c)==1)
    //            return c;
    //
    //    return '_';
    //}
    //✅ LEVEL 2 — Arrays (Automation Favorite)
    //Find duplicates in array
    //Remove duplicates
    //Second largest number
    //Rotate array left/right
    //Merge two arrays
    //Find missing number (1–100)
    //Move zeros to end
    //Sort array without Arrays.sort()
    //Maximum subarray sum (Kadane)
    //Intersection of two arrays
    //
    //Example:
    //
    //int secondLargest(int[] arr){
    //    int first=Integer.MIN_VALUE;
    //    int second=Integer.MIN_VALUE;
    //
    //    for(int n:arr){
    //        if(n>first){
    //            second=first;
    //            first=n;
    //        }else if(n>second && n!=first){
    //            second=n;
    //        }
    //    }
    //    return second;
    //}
    //✅ LEVEL 3 — Collections (VERY IMPORTANT FOR SENIOR QA)
    //Difference between HashMap & ConcurrentHashMap (code demo)
    //Sort HashMap by value
    //Remove duplicates using Set
    //Iterate Map using Streams
    //Count frequency using Map
    //Convert List → Map
    //Find duplicate elements using Stream API
    //Custom Comparator sorting
    //Thread-safe collection example
    //Merge two maps
    //
    //Example:
    //
    //list.stream()
    //    .collect(Collectors.groupingBy(
    //        Function.identity(),
    //        Collectors.counting()
    //    ));
    //✅ LEVEL 4 — Java 8 Streams (HOT TREND 🔥)
    //Find duplicate elements
    //Find max/min from list
    //Convert list to uppercase
    //Filter even numbers
    //Sort employees by salary
    //Group by department
    //Find second highest salary
    //Flatten nested list
    //Remove null values
    //Parallel stream example
    //
    //Example:
    //
    //list.stream()
    //    .filter(n -> n%2==0)
    //    .forEach(System.out::println);
    //✅ LEVEL 5 — OOP Coding (Senior Expectation)
    //Singleton class (Thread-safe)
    //Immutable class creation
    //Interface vs Abstract class example
    //Dependency Injection example
    //Factory Design Pattern coding
    //
    //Singleton:
    //
    //public class Singleton {
    //
    //    private static Singleton instance;
    //
    //    private Singleton(){}
    //
    //    public static synchronized Singleton getInstance(){
    //        if(instance==null)
    //            instance=new Singleton();
    //        return instance;
    //    }
    //}
    //✅ LEVEL 6 — Multithreading (Asked for 6+ yrs)
    //Create thread using Runnable
    //Synchronization example
    //Producer–Consumer problem
    //ThreadLocal usage
    //ExecutorService example
    //
    //Example:
    //
    //ExecutorService service =
    //        Executors.newFixedThreadPool(3);
    //
    //service.submit(() -> {
    //    System.out.println(Thread.currentThread().getName());
    //});
    //⭐ MOST ASKED IN REAL INTERVIEWS (Priority Order)
    //
    //Prepare these FIRST:
    //
    //✅ First non-repeating character
    //✅ Second largest number
    //✅ HashMap frequency problem
    //✅ Java Streams filtering/grouping
    //✅ Singleton pattern
    //✅ ThreadLocal usage
    //✅ Comparator sorting
    //✅ Duplicate detection
    //✅ Immutable class
    //✅ Stream API salary problem
    //
    //🔥 Senior-Level Follow-up Questions (Interview Trap)
    //
    //After coding they ask:
    //
    //👉 Time Complexity?
    //👉 Memory Optimization?
    //👉 Thread Safe version?
    //👉 Stream vs Loop performance?
    //👉 Can this fail in parallel execution?
    //
    //🚀
}
