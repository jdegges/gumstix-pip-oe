#
# checkroot.sh	Check to root filesystem.
#
# Version:	@(#)checkroot.sh  2.84  25-Jan-2002  miquels@cistron.nl
#

. /etc/default/rcS

#
# Set SULOGIN in /etc/default/rcS to yes if you want a sulogin to be spawned
# from this script *before anything else* with a timeout, like SCO does.
#
test "$SULOGIN" = yes && sulogin -t 30 $CONSOLE

#
# Ensure that bdflush (update) is running before any major I/O is
# performed (the following fsck is a good example of such activity :).
#
test -x /sbin/update && update

#
# Read /etc/fstab.
#
exec 9>&0 </etc/fstab
rootmode=rw
rootopts=rw
test "$ENABLE_ROOTFS_FSCK" = yes && rootcheck="yes" || rootcheck="no"
swap_on_md=no
devfs=
while read fs mnt type opts dump pass junk
do
	case "$fs" in
		""|\#*)
			continue;
			;;
		/dev/md*)
			# Swap on md device.
			test "$type" = swap && swap_on_md=yes
			;;
		/dev/*)
			;;
		*)
			# Might be a swapfile.
			test "$type" = swap && swap_on_md=yes
			;;
	esac
	
	test "$type" = devfs && devfs="$fs"

	# Currently we do not care about the other entries
	if test "$mnt" = "/"
	then
		#echo "[$fs] [$mnt] [$type] [$opts] [$dump] [$pass] [$junk]"

		rootopts="$opts"		
		roottype="$type"

		#The "spinner" is broken on busybox sh	
		TERM=dumb
			
		test "$pass" = 0 -o "$pass" = "" && rootcheck=no
		
		# Enable fsck for ext2 and ext3 rootfs, disable for everything else				
		case "$type" in
		ext2|ext3)	rootcheck=yes;;
		*)		rootcheck=no;;
		esac
		
		if test "$rootcheck" = yes
		then
			if ! test -x "/sbin/fsck.${roottype}"
			then
				echo -e "\n * * * WARNING: /sbin/fsck.${roottype} is missing! * * *\n"
				rootcheck=no
			fi
		fi
		
		case "$opts" in
			ro|ro,*|*,ro|*,ro,*)
				rootmode=ro
				;;
		esac
	fi
done
exec 0>&9 9>&-

#
# Activate the swap device(s) in /etc/fstab. This needs to be done
# before fsck, since fsck can be quite memory-hungry.
#
doswap=no
test -d /proc/1 || mount -n /proc
case "`uname -r`" in
	2.[0123].*)
		if test $swap_on_md = yes && grep -qs resync /proc/mdstat
		then
			test "$VERBOSE" != no && echo "Not activating swap - RAID array resyncing"
		else
			doswap=yes
		fi
		;;
	*)
		doswap=yes
		;;
esac
if test $doswap = yes
then
	test "$VERBOSE" != no && echo "Activating swap"
	swapon -a 2> /dev/null
fi

#
# Check the root filesystem.
#
if test -f /fastboot || test $rootcheck = no
then
  test $rootcheck = yes && echo "Fast boot, no filesystem check"
else
  #
  # Ensure that root is quiescent and read-only before fsck'ing.
  #
  mount -n -o remount,ro /
  if test $? = 0
  then
    if test -f /forcefsck
    then
	force="-f"
    else
	force=""
    fi
    if test "$FSCKFIX" = yes
    then
	fix="-y"
    else
	fix="-a"
    fi
    spinner="-C"
    case "$TERM" in
        dumb|network|unknown|"") spinner="" ;;
    esac
    test `uname -m` = s390 && spinner="" # This should go away
    test "$VERBOSE" != no && echo "Checking root filesystem..."
    fsck $spinner $force $fix /
    RTC=$?
    #
    # If there was a failure, drop into single-user mode.
    #
    # NOTE: "failure" is defined as exiting with a return code of
    # 2 or larger.  A return code of 1 indicates that filesystem
    # errors were corrected but that the boot may proceed.
    #
    
    echo "RETURNCODE: [$RTC]"
    
    if test "$RTC" -gt 3
    then
    
      # Since this script is run very early in the boot-process, it should be safe to assume that the
      # output is printed to VT1. However, some distributions use a bootsplash to hide the "ugly" boot
      # messages and having the bootsplash "hang" due to a waiting fsck prompt is less than ideal
      chvt 1
    
      # Surprise! Re-directing from a HERE document (as in
      # "cat << EOF") won't work, because the root is read-only.
      echo
      echo "fsck failed.  Please repair manually and reboot. " 
      echo "Please note that the root filesystem is currently "
      echo "mounted read-only.  To remount it read-write:"
      echo
      echo "   # mount -n -o remount,rw /"
      echo
      echo "CONTROL-D will exit from this shell"
      echo "and REBOOT the system."
      echo
      # Start a single user shell on the console
      /sbin/sulogin $CONSOLE
      reboot -f
    fi
  else
    echo "*** ERROR!  Cannot fsck root fs because it is not mounted read-only!"
    echo
  fi
fi

#
#	If the root filesystem was not marked as read-only in /etc/fstab,
#	remount the rootfs rw but do not try to change mtab because it
#	is on a ro fs until the remount succeeded. Then clean up old mtabs
#	and finally write the new mtab.
#	This part is only needed if the rootfs was mounted ro.
#
ROOTFSDEV="/dev/root"
if ! grep -q "^$ROOTFSDEV\w" /proc/mounts; then
  ROOTFSDEV="rootfs"
fi
if [ x$(grep "^$ROOTFSDEV\w" /proc/mounts | awk '{print $4}') = "xrw" ]; then
	echo "Root filesystem already read-write, not remounting"
	exit 0
fi

echo "Remounting root file system..."
mount -n -o remount,$rootmode /
if test "$rootmode" = rw
then
	if test ! -L /etc/mtab
	then
		rm -f /etc/mtab~ /etc/nologin
		: > /etc/mtab
	fi
	mount -f -o remount /
	mount -f /proc
	test "$devfs" && grep -q '^devfs /dev' /proc/mounts && mount -f "$devfs"
fi

: exit 0
