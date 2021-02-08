# 题目要求：返回最短路径长度
- Given two words, beginWord and endWord, and a dictionary wordList, 
- return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.
- Only one letter is different between each adjacent pair of words in the sequence.
## 解题思路：
   ### 建图： build the graph
- vertex: (点) word
- edge: (边) if one word can transform to another one, there will be an edge between them.
- undirected: beacuse the two words are interconvertibility(相互转换).
- weight = 1，等权值
- 建图： ```HashMap<from_v, List<to_v>>```
- 同一个点，可以到达多个点，正向建图。
  ### BFS：
  - 具有分叉特点的，多种尝试得到各种解的问题，就是分叉搜索
  - 在这种问题里关于 shortest path 的，就可以用BFS
  - BFS 用于解决等权值的最短路径问题。（we can use BFS on the equal weight graph to find the shortest path）
  ### List of Words:
  - This is out dictionary, and it may have the following properties:
    1. huge size 
    2. no duplicate
    3. O(1) Look up: hash set
  - We can use the hash set to store all the words in the wordList
  ### Overall 解题思路：
  - Start from the start word, use the bfs, find the next word it can transform to. 
  - We want to find the shortest path, we can use minLen to count each step.
  - When we move to next step, that means we already traverse all the words, the current word can transform to.
  - So we add one to our minLen. As long as we meet the end word, the minLen must be shortest.
  - In addition, we need to check visited to advoid the same word transform back with enter the queue twice.
## Code:
```java
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // c.c. If there is an invaild input, we can just throw new IllegalArgumentException("bad input");
        if (beginWord == null || endWord == null || wordList == null || wordList.size() == 0) {
            return -1;
        }
        // add all words form wordList to Hash set, it helps us to O(1) look up and avoid duplicate
        HashSet<String> wordSet = new HashSet<>();
        for (String word: wordList) {
            wordSet.add(word);
        }
        // use a Hash Set to check visited
        HashSet<String> visited = new HashSet<>();
        // use bfs by a queue
        Queue<String> queue = new LinkedList<>();
        // start from the beginWord:
        queue.offer(beginWord);
        visited.add(beginWord);
        // count the shoetest path, a -> b, return 2, the number of words
        // so we need to initialize minLen by 1
        int minLen = 1;
        while (!queue.isEmpty()) {
            // we need to distinguish the different level, therefore we use the size of queue
            int size = queue.size();
            while (size-- > 0) { // there are still some words in this level have not be 
                String curWord = queue.poll();
                // To get all the words, which can be transformed by the curWord
                List<String> nexts = convert(curWord, wordSet);
                for (String next: nexts) {
                    if (next.equals(endWord)) { // we find the endword, stop here
                        return minLen + 1; // a -> b, now minLen = 1, we need to return 2, so at here minLen shoud add one
                    }
                    if (visited.add(next)) { // check visited
                        queue.offer(next); // if it is a new word, just add it into the queue
                    }
                }
            }
            minLen++; // after finish the whole level, add one the minLen
        }
        return 0; // I can not find a way to transform
    }
    private List<String> convert(String curWord, HashSet wordSet) {
        List<String> res = new LinkedList<>();
        int len = curWord.length();
        char[] curArr = curWord.toCharArray();
        for (int i = 0; i < len; i++) {
            char temp = curArr[i]; // just change one letter each transform
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != temp) { 
                    // we can also delete this part. 
                    // Even the res can contain the curWord itself,
                    // the curWord has be marked visited, is not vaild result
                    curArr[i] = c;
                    String next = String.valueOf(curArr);// String.valueOf(curArr) 和 curArr.toString()的区别
                    if (wordSet.contains(next)) {
                        res.add(next);
                    }
                }
            }
            // after change this position, we need to change the position back to the original letter
            curArr[i] = temp;
        }
        return res;
    }
}

```
## String.valueOf(curArr) 和 curArr.toString()的区别
 - arr.toString()一般用于的打印，转化成的是地址值
 1. toString():在API文档中是这样说的，返回此对象本身（它已经是一个字符串了！！！）。
 - 按照它的意思就是说一般的对象或者参数都是有toString()的方法的，只是要注意在一个参数定义为int类型是就没有这个方法了。
 - 还有就是当参数为空的时候.toString()方法就会报出空指针异常，这是这个方法的不好的地方使用时需要仔细斟酌一下。比如：
  ``` 
  Object obj = getObject();
  Syystem.out.println(obj.toString());
  ```
 2. 最重要的角色出场了String.valueOf()：这个方法是静态的，直接通过String调用，
 - 可以说是完美，只是平时不习惯这样写而已，这样的实现避免了前面两个的不足和缺点。首先来看看他内部的实现机制：
 ```
 public static String valueOf(Object obj){return (obj==null) ? "null" : obj.toString()};
 ```
 
 # Follow Up: 1.改变 transform rule
 词语接龙 - 这个词只能转化成以它尾字母开头的词
 
