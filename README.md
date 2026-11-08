# Assignment 4. Patient Zero

Student ID: **253053**  
Name: Alisher Bakhridenov 
SE - 2510

## Program output

Generated from student ID `253053` with the provided `tools/MakeVariant.java`:

```text
Mutations: 71
Shared core: 1163
Marker hits: 8
First hit: 648
Border length: 3
Shared words: 615
Tests needed: 33
Cheapest testing: 145
Longest chain: 27
```

## Questions

### Mission 1
`d[i][j]` is the minimum number of single-character insertions, deletions and replacements needed to transform the first `i` characters of A into the first `j` characters of B. The first row and column are initialized with `j` and `i`; each other cell is the minimum of replacement/match from the diagonal, deletion from above, and insertion from the left.

### Mission 2
A subsequence keeps the relative order of characters but may skip characters; a substring must be contiguous. Therefore the LCS can be longer than the number of equal characters at identical positions: matching characters may occur at different positions while preserving order.

### Mission 3
The prefix function stores, for each prefix ending at position `i`, the length of its longest proper prefix that is also a suffix. KMP uses this value after a mismatch to reuse the already matched part, so the text index only moves forward. For my marker, Border length is **3**: its longest proper prefix that is also its suffix has three characters.

### Mission 4
Equal hashes do not guarantee equal strings because different windows can collide under a finite modulo. The implementation therefore compares the actual characters for candidate windows before counting a match. If many hashes collide, more character comparisons are needed; the worst-case running time can approach O((|A|+|B|)k), while ordinary behavior is near linear for fixed k.

### Mission 5
For each vertex there are two DP states: `dp[v][1]` is the minimum subtree cost when `v` is tested, and `dp[v][0]` is the minimum when `v` is not tested. If `v` is not tested, every child must be tested; if `v` is tested, each child can use its cheaper state. The solution is reconstructed by walking from root 0 using these states. A greedy choice based only on the number of links can fail because it ignores test costs and how choices affect neighboring vertices.

## AI use

I used ChatGPT to help implement and review the algorithms in `Strains.java`, `Marker.java` and `Outbreak.java`, explain the dynamic-programming states and string algorithms, and help prepare this report. I ran the provided checker on all three supplied examples and tested the program on my generated ID data. I will review and understand the code before submission.

## Timing (Mission 7)

Milliseconds, average of 3 runs, measured in this environment:

| test | size | time |
|---|---:|---:|
| naive search | 20 000 | 58.5 |
| naive search | 40 000 | 70.9 |
| naive search | 80 000 | 110.2 |
| KMP | 20 000 | 3.0 |
| KMP | 40 000 | 4.9 |
| KMP | 80 000 | 1.3 |
| edit distance | 500 | 13.2 |
| edit distance | 1 000 | 15.3 |
| edit distance | 2 000 | 59.9 |
| edit distance | 4 000 | 297.1 |

The KMP measurements are noisy at these short runtimes and should not be interpreted as a decrease in algorithmic complexity; KMP is O(n+m), while the naive search can repeatedly compare much of the pattern. Edit distance uses a two-dimensional table and grows quadratically when both strings double in length; runtime is also affected by JVM warm-up and machine load.

## Complexity

Let `n` and `m` be the lengths of the two strings, `t` the genome length, `p` the marker length, `k` the word-window length, and `v` the number of people.

| algorithm | time | space | assumptions |
|---|---|---|---|
| Edit distance | O(nm) | O(nm) | Full DP table |
| LCS | O(nm) | O(nm) | Full DP table |
| Prefix function and KMP | O(p+t) | O(p+h) | `h` is number of reported hits |
| Rabin-Karp (shared words) | Expected O(n+m), worst O((n+m)k) | O(m) | Fixed-size rolling hash; candidates are verified character by character |
| Tree DP | O(v) | O(v) | Adjacency lists for a tree |

## Before submission

- [x] Personal `data` files generated for ID 253053.
- [x] `java tools/Check.java` passes `tiny`, `small` and `dense`.
- [x] Program compiles and runs on personal data.
- [ ] Open `output/outbreak.html` in a browser and save a screenshot as `output/outbreak.png`.
- [ ] Review and understand all code and report answers.
- [ ] Add your name and group.
- [ ] Create a public GitHub repository and push the project. Make mission-by-mission commits as required by the course.
- [ ] Submit the GitHub link in Moodle.
