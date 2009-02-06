DESCRIPTION = "SlingBox is a minimal version of BusyBox with just enough functionality \
to enable ipkg to run on an Unslung NSLU2 device."
HOMEPAGE = "http://www.busybox.net"
LICENSE = "GPL"
SECTION = "base"
PRIORITY = "required"
PR = "r3"
COMPATIBLE_MACHINE = "nslu2"

SRC_URI = "http://www.busybox.net/downloads/busybox-${PV}.tar.gz \
           file://defconfig \
           file://Makefile_args.patch;patch=1 \
           file://lazy_umount.patch;patch=1 \
           file://slingbox_name.patch;patch=1 \
           file://slingbox.patch;patch=1"

S = "${WORKDIR}/busybox-${PV}"

export EXTRA_CFLAGS = "${CFLAGS}"
EXTRA_OEMAKE_append = " CROSS=${HOST_PREFIX}"

PACKAGES = "${PN}"
FILES_${PN} = "/"
FILES_${PN}-doc = ""
FILES_${PN}-dev = ""
FILES_${PN}-locale = ""

inherit cml1

do_configure () {
	install -m 0644 ${WORKDIR}/defconfig ${S}/.config
	cml1_do_configure
}

do_compile () {
	unset CFLAGS
	base_do_compile
	# Just in case fdisk is compiled in, do not overwrite the Linksys one
	sed -i -e '/fdisk/d' ${S}/busybox.links
}

do_install () {
	oe_runmake 'PREFIX=${D}' install
}
