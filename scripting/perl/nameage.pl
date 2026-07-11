#!/usr/bin/perl
use strict; 
use warnings;

print 'Enter your name: ';

# You were missing the <STDIN> handle here!
my $name = <STDIN>; 

chomp $name;
print "Hello, $name!\n";
