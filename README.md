# Specifications

Write a function in the Clojure programming language which given:

a set of dolls with a name and unique weight and value combination
an overall weight restriction
Produces the optimal set of drug-packed porcelain dolls which:

are within the total weight restriction
maximize the total street value of drugs delivered
Requirements:

use leiningen - https://github.com/technomancy/leiningen
include multiple high-level test cases to validate your solution (like the one included below)
provide instructions in a README for running your test suite from the command line

# Requirements

* Java JDK
* Leiningen
* ClojureCLR
* .NET or Mono

## Important Note about OSX and ClojureCLR
There is a bug for OSX users that causes Clojure.Main.exe. to immediately crash on load (http://dev.clojure.org/jira/browse/CLJCLR-48?page=com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel)

# Building/Running

__Note__ leiningen is smart about dependencies and will download them when they are needed (usually the first run). This will delay actions, but should be a one-time operation.

The lein clr plugin is used to automate building with ClojureCLR (https://github.com/kumarshantanu/lein-clr)

From the application directory you can run several commands

`lein clr compile` will compile the project

`lein clr test` will run tests

`lein clr run -m foo.core "src/foo/test.txt"` will load the values from 'test.txt'

included test files (found in src/foo)

* test.txt
* test3.txt (demonstrated @ https://www.youtube.com/watch?v=kH7weFvjLPY)
* test4.txt (faulty version of test3.txt)
* test5.txt (faulty version)
* test6.txt (faulty version)
* test7.txt (demonstrated @ https://www.youtube.com/watch?v=EH6h7WA7sDw)

# Test

Currently all tests pass

# Issues

* Console output does not always print everything
* Reflection warning. It does not appear to break anything

# Setup

## Clojure

This project does not require Clojure to be manually downloaded. Leiningen will automatically manage dependencies.

## Java JDK

The latest Java JDK is __required__ for Leiningen to run.

Goto http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

## Leiningen

* Follow the four steps (http://leiningen.org/)
* Use a package manager (https://github.com/technomancy/leiningen/wiki/Packaging)
* Use an installer on Windows (http://leiningen-win-installer.djpowell.net/)

## ClojureCLR

* Create a "ClojureCLR" folder in the app directory (e.g. the directory where project.clj is)
* Download latest binary (http://sourceforge.net/projects/clojureclr/files/)
* Extract contents to "ClojureCLR" folder

## .NET

Windows comes pre-installed with .NET, but support varies depending on edition and bit-version. This project has been tested __only__ with .NET 4.0.

Requirements for .NET 3.5 : https://msdn.microsoft.com/en-us/library/bb882520(v=vs.90).aspx

Requirements for .NET 4.0 : https://msdn.microsoft.com/en-us/library/8z6watww(v=vs.110).aspx

### Mono

For non-Windows systems, installing mono should be done through a package manager

```
brew install mono
```
