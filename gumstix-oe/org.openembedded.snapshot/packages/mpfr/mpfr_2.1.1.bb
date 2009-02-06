DESCRIPTION = "A C library for multiple-precision floating-point computations with exact rounding"
LICENSE = "LGPL"
SECTION = "libs"
DEPENDS = "gmp"
PR = "r2"

SRC_URI = "http://www.mpfr.org/mpfr-${PV}/mpfr-${PV}.tar.bz2"
S = "${WORKDIR}/mpfr-${PV}"

inherit autotools

do_stage() {
	oe_runmake install prefix=${STAGING_DIR} \
	       bindir=${STAGING_BINDIR} \
	       includedir=${STAGING_INCDIR} \
	       libdir=${STAGING_LIBDIR} \
	       datadir=${STAGING_DATADIR} \
	       infodir=${STAGING_DIR}/${HOST_SYS}/info
}
