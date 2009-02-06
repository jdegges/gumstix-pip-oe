#!/bin/bash

# edit if necessary to reflect your setup
. $HOME/gumstix/gumstix-oe/extras/profile

# path to feed directory
OE_FEED="/var/www/feeds"

# list of libc variants to build
BUILD_LIBC=" \
                glibc \
                uclibc \
               "

# list of machines to build
BUILD_MACHINES=" \
                gumstix-custom-verdex \
                gumstix-custom-connex \
                gumstix-custom-basix \
               "

cd $GUMSTIXTOP
svn update

REVISION=`svnversion`

# read list of build targets
BUILD_TARGETS=`cat $HOME/gumstix/gumstix-oe/extras/autobuilder/targets`
              
if [ ! -e $OE_FEED/archive/$REVISION ];
then
  echo "Building revision $REVISION"

  rm -rf tmp

  for libc in $BUILD_LIBC
  do    
    echo "ANGSTROM_MODE = \"$libc\"" >> build/conf/local.conf
    for machine in $BUILD_MACHINES
    do
      echo "MACHINE = \"$machine\"" > build/conf/auto.conf

      for target in $BUILD_TARGETS
      do
        echo "Building $libc $target for $machine"
        bitbake $target
        echo "Completed $libc $target for $machine"
      done

      # hack to prevent link error on bluez-utils on subsequent machines
      bitbake -c clean gettext
      bitbake -c clean glib-2.0
      bitbake -c clean libiconv
      bitbake -c clean libtool-cross
      bitbake -c clean qmake-native
      bitbake -c clean qmake2-native      
    done
    svn revert build/conf/local.conf
  done

  svn revert build/conf/auto.conf

  mkdir -p $OE_FEED/archive/$REVISION
  cp -rf tmp/deploy/* $OE_FEED/archive/$REVISION

  for libc in $BUILD_LIBC
  do
    # Cleanout stuff not needed in feed
    rm     $OE_FEED/archive/$REVISION/$libc/ipk/Packages*
    rm -rf $OE_FEED/archive/$REVISION/$libc/ipk/armv5te/morgue
    rm -rf $OE_FEED/archive/$REVISION/$libc/ipk/i686

    for machine in $BUILD_MACHINES
    do
      rm     $OE_FEED/archive/$REVISION/$libc/images/$machine/modules*
    done

    # Create Packages.gz for feeds
    cd $OE_FEED/archive/$REVISION/$libc/ipk/all
    $GUMSTIXTOP/tmp/staging/i686-linux/bin/ipkg-make-index . > Packages
    gzip -f Packages

    cd $OE_FEED/archive/$REVISION/$libc/ipk/armv5te
    $GUMSTIXTOP/tmp/staging/i686-linux/bin/ipkg-make-index . > Packages
    gzip -f Packages

    for machine in $BUILD_MACHINES
    do
      cd $OE_FEED/archive/$REVISION/$libc/ipk/$machine
      $GUMSTIXTOP/tmp/staging/i686-linux/bin/ipkg-make-index . > Packages
      gzip -f Packages
    done

  done
  
  cd $OE_FEED
  rm -f current
  ln -s $OE_FEED/archive/$REVISION current
  
# use for syncing to remote server
#  ftpsyncup

fi

