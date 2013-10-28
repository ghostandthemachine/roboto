# Mr. Roboto

This is a Chico State CSCI 580 (A.I.) homework assignment

#### The problem

Using a predefined grid world of available and blocked spaces, this program will make a best guess of a robot's location. The user enters sensory data in the form of movement directions consisting of binary flags (NSWE). Intitally, the user enters the number of iterations (directional transitions) to perform.

## To run

`lein run`

then the user will be prompted to input the number of iterations followed by the sensory input in binary form

NOTE: *currently the grid world is predefined*

#### Grid World
````clojure
 [1  2  3  4  5
  6  X  7  X  8
  9  10 11 12 13]
````

*where x denotes a barrier*

#### Sensory input (robot movement)

User input is in the form of binary flags in the order: north, south, west and east (NSWE)

for example:

north, south (NS)
`1100`

north, south, west (NSW)
`1110`

#### Example

````clojure

lein run

=> Enter number of iterations to calculate:
2

=> Calculate with <2> iterationsn

=> Enter next command
1100
 
=> Enter next command
1110


=> {:indexes [0 8],
=>  :Y
=>  [0.11213448893384917
=>   0.016609479022843165
=>   0.2242416371614879
=>   0.016609479022843165
=>   0.11213448893384917
=>   0.003321895804568632
=>   0.0298970622411177
=>   0.003321895804568632
=>   0.11213448893384917
=>   0.016609479022843165
=>   0.22424163716148787
=>   0.016609479022843165
=>   0.11213448893384917],
=>  :Z
=>  [0.008174604243277605
=>   0.0012108310207652668
=>   0.001816357261008052
=>   0.0012108310207652668
=>   1.0092104004046425E-4
=>   2.9897062241117686E-6
=>   2.690735601700593E-5
=>   2.9897062241117686E-6
=>   0.008174604243277605
=>   0.0012108310207652668
=>   0.0018163572610080518
=>   0.0012108310207652668
=>   1.0092104004046425E-4],
=>  :D [1 1 2 1 3 3 3 3 1 1 2 1 3],
=>  :O
=>  [[0.0729 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0]
=>   [0.0 0.0729 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0]
=>   [0.0 0.0 0.0081 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0]
=>   [0.0 0.0 0.0 0.0729 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0]
=>   [0.0 0.0 0.0 0.0 9.0E-4 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0]
=>   [0.0 0.0 0.0 0.0 0.0 9.0E-4 0.0 0.0 0.0 0.0 0.0 0.0 0.0]
=>   [0.0 0.0 0.0 0.0 0.0 0.0 9.0E-4 0.0 0.0 0.0 0.0 0.0 0.0]
=>   [0.0 0.0 0.0 0.0 0.0 0.0 0.0 9.0E-4 0.0 0.0 0.0 0.0 0.0]
=>   [0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0729 0.0 0.0 0.0 0.0]
=>   [0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0729 0.0 0.0 0.0]
=>   [0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0081 0.0 0.0]
=>   [0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0729 0.0]
=>   [0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 9.0E-4]],
=>  :F
=>  [0.3262015998256128
=>   0.04831732574906216
=>   0.07248040721762605
=>   0.04831732574906216
=>   0.004027180244760651
=>   1.1930203888657318E-4
=>   0.0010737183499791591
=>   1.1930203888657318E-4
=>   0.3262015998256128
=>   0.04831732574906216
=>   0.07248040721762604
=>   0.04831732574906216
=>   0.004027180244760651]}

=> Most likely positions: [0 8]
````
