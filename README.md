# Requirements

* Java JDK
* Leiningen
* ClojureCLR
* .NET or Mono

## Important Note about OSX and ClojureCLR
There is a bug for OSX users that causing Clojure.Main.exe. to not work (it crashes immediately) (http://dev.clojure.org/jira/browse/CLJCLR-48?page=com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel)

# Building/Running

__Note__ leiningen is smart about dependencies and will download them when they are needed (usually the first run). This will delay actions, but should be a one-time operation.

From the application directory you can run several commands

`lein clr compile` will compile the project

`lein clr test` will run tests

`lein clr run -m foo.core "src/foo/test.txt"` will load the values from 'test.txt'

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
* Download latest binary
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
