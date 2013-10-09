#!/bin/bash
javac -sourcepath ../2/ -classpath ../2/ cgCanvas.java midtermMain.java Poly.java simpleCanvas.java
cp ../2/Rasterizer.class Rasterizer.class
cp ../2/Rasterizer$Bucket.class Rasterizer$Bucket.class
java midtermMain