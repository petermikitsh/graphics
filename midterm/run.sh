#!/bin/bash
javac -sourcepath ../2/:../3/ -classpath ../2/:../3/ cgCanvas.java midtermMain.java Poly.java simpleCanvas.java
cp ../2/Rasterizer.class Rasterizer.class
cp ../2/Rasterizer*.class .
cp ../3/clipper*.class .
java midtermMain