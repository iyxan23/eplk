# EPLK

[![Hits-of-Code](https://hitsofcode.com/github/iyxan23/eplk?branch=main)](https://hitsofcode.com/github/iyxan23/eplk/view?branch=main)
[![Java CI with Gradle](https://github.com/Iyxan23/eplk/actions/workflows/gradle.yml/badge.svg)](https://github.com/Iyxan23/eplk/actions/workflows/gradle.yml)

Epic programming language named Eplk, short for Extensible Programming Language in Kotlin

Created with the help of CodePulse's "Make YOUR OWN Programming Language" series, many thanks to him! Here is his [playlist](https://www.youtube.com/watch?v=r46EONXC1i0). Also, check his [github repository](https://github.com/davidcallanan/py-myopl-code) and [profile](https://github.com/davidcallanan)

## Notice
Project halted because Iyxan23 decides to move on to something else instead, feel free to fork this

## How to run
Go to [`eplk/src/main/java/com/iyxan23/eplk`](https://github.com/Iyxan23/eplk/tree/main/eplk/src/main/java/com/iyxan23/eplk). Run `Runner.kt` to run .eplk files, and `Shell.kt` to run the eplk shell

## Syntax
Syntax is derived mostly from python, java and kotlin (major languages I personally use). Note that this syntax can change overtime as I progress the language further.
```
// Hello comments!
var number = 10
var string = "Hello World"
var myList = ["hello", 1, -1, 50 * 50, 10 + 10 * 2]

fun greet(name) -> "Hello, " + name + "!"

println(string)
println(greet("Iyxan23"))

if (number > 50) {
    println("Broken!")
} elif (number > 30) {
    println("Also Broken!")
} else {
    println("Yes!")
}

number--
number++

for (var index = 0; index < 5; index++) {
    number++
    print("Loop at index ")
    println(index)
}

while (number > 0) {
    println(myList[number])
    number--
}

println(if (true) "Of course true!" else "What just happened")
```

## FAQs

### Why make an interpreted language in kotlin?
Well, yes I do know that kotlin is not the best choice as it's quite slow (+ eplk is literally an interpreted language), but I'm targeting EPLK to be intergrated with android apps very easily. And also this is a for-fun project in which so I can learn stuff about making an interpreted language, therefore I do whatever I want here <img width=20px src="https://cdn.discordapp.com/emojis/846626029535625216.png"/>

### What's the point of making this?
First of all, I always wanted to learn on how a programming language works, and also building my own!. Second, I wanted a language that is very easily integrated to android. With EPLK, you can create your own Object, Classes, Functions in kotlin / java without messing around with EPLK itself (Coming soon). Third, This project gives me more experience in using kotlin, I'm quite new to kotlin afterall.
