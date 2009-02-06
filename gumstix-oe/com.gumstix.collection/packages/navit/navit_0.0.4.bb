require navit.inc

PR = "r2"

SRC_URI = " \
  ${SOURCEFORGE_MIRROR}/navit/navit-${PV}.tar.gz \
  file://maxroute.patch;patch=1 \
 "
