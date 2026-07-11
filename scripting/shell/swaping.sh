#!/bin/bash
a=10; b=20
echo "Before: a=$a b=$b"
temp=$a
a=$b
b=$temp
echo "After: a=$a b=$b"
