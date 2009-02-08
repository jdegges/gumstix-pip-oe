DESCRIPTION = "FreeImage Library"
PRIORITY = "optional"
HOMEPAGE = "http://freeimage.sourceforge.net/"
LICENSE = "GNU General Public License (GPL) and the FreeImage Public License (FIPL)"
SECTION = "libs"

PR = "r0"

RDEPENDS = "libstdc++"

SRC_URI = " \
    http://jdegges.googlepages.com/FreeImage-3.11.0.tar.gz \
    file://cross-compile-makefile.patch;patch=1 \
    "

do_install () {
    install -d ${D}${includedir} ${D}${libdir}

    install -m 0644 ${S}/Source/FreeImage.h ${STAGING_INCDIR}/FreeImage.h

    install -m 0644 ${S}/libfreeimage.a ${STAGING_LIBDIR}/libfreeimage.a
    install -m 0755 ${S}/libfreeimage-${PV}.so ${STAGING_LIBDIR}/libfreeimage-${PV}.so
    install -m 0755 ${S}/libfreeimage-${PV}.so ${STAGING_LIBDIR}/libfreeimage.so
    install -m 0755 ${S}/libfreeimage-${PV}.so ${STAGING_LIBDIR}/libfreeimage.so.3

    install -m 0644 ${S}/libfreeimage.a ${D}${libdir}
    install -m 0755 ${S}/libfreeimage-${PV}.so ${D}${libdir}/libfreeimage-${PV}.so
    install -m 0755 ${S}/libfreeimage-${PV}.so ${D}${libdir}/libfreeimage.so
    install -m 0755 ${S}/libfreeimage-${PV}.so ${D}${libdir}/libfreeimage.so.3
}

FILES_${PN} = "${libdir}/libfreeimage-${PV}.so ${libdir}/libfreeimage.so ${libdir}/libfreeimage.so.3"

