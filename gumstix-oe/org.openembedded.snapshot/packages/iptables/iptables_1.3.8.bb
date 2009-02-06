DESCRIPTION = "iptables network filtering tools"
HOMEPAGE = "http://www.netfilter.org/"
SECTION = "console/utils"
LICENSE = "GPL"
RRECOMMENDS = "kernel-module-ip-tables kernel-module-iptable-filter"
PR = "r1"
SRC_URI = "http://netfilter.org/projects/iptables/files/iptables-${PV}.tar.bz2 \
          file://getsockopt-failed.patch;patch=1 \
          "

S = "${WORKDIR}/iptables-${PV}"

PARALLEL_MAKE=""

export COPT_FLAGS = "${CFLAGS}"
export KERNEL_DIR = "${STAGING_INCDIR}"

do_compile () {
	unset CFLAGS
	oe_runmake BINDIR=${D}${sbindir} LIBDIR=${D}${libdir} MANDIR=${D}${mandir} NO_SHARED_LIBS=1
}

do_install () {
	unset CFLAGS
	oe_runmake BINDIR=${D}${sbindir} LIBDIR=${D}${libdir} MANDIR=${D}${mandir} install NO_SHARED_LIBS=1
}

PACKAGES =+ "${PN}-utils"
FILES_${PN}-utils = "${sbindir}/iptables-save ${sbindir}/iptables-restore"
FILES_${PN}-doc += "${mandir}"

