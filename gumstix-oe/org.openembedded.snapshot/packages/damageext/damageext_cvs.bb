PV = "0.0+cvs${SRCDATE}"
LICENSE = "MIT-X"
SECTION = "libs"
DEPENDS = "xextensions fixesext"
DESCRIPTION = "X Damage extension headers and specification"
PR = "r1"

SRC_URI = "${FREEDESKTOP_CVS}/xlibs;module=DamageExt"
S = "${WORKDIR}/DamageExt"

inherit autotools pkgconfig

do_stage() {
	oe_runmake install prefix=${STAGING_DIR} \
	       bindir=${STAGING_BINDIR} \
	       includedir=${STAGING_INCDIR} \
	       libdir=${STAGING_LIBDIR} \
	       datadir=${STAGING_DATADIR}
}
