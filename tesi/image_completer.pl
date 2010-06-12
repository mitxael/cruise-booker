#!/usr/bin/env perl

use strict;
use warnings;

use File::Basename;

my $folder = "./NewPlots/";
my @extensions = ("ps", "eps", "pdf");
my $convert = "convert";

open(FILES, "ls -1 $folder |");
while(<FILES>) {
    chomp();

    my $currentfile = $folder.$_;
    my $toconvert = 0;
    my $fileext = "";


    foreach my $ext (@extensions) {
        if($currentfile =~ /.*\.$ext$/) {
            $fileext = $ext;
            $toconvert = 1;
        }    
    }

    if($toconvert) {
        print "I think that '$currentfile' is an image file...\n";
        foreach my $ext (@extensions) {
            my $basename = fileparse($currentfile, qr/\.[^.]*/);
            my $destfile = $folder.$basename.".$ext";
            
            if((!(-e $destfile) || ($ext ne "ps")) && ($fileext eq "ps") ) {
                system("$convert \"$currentfile\" \"$destfile\"");
                print "I just converted '$currentfile' in '$destfile'\n";
            } else {
                print "Destination file '$destfile' already exists, skipping...\n";
            }
        }
    }
    else {
        print "I think that '$currentfile' is not an image file so i will skip it...\n";
    }
    print "\n"
}
close(FILES);

print "\n\nI'm done.\n";
print "Have a nice day.\n";
