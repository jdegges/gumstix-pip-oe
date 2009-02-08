DESCRIPTION = "Image Analyzer"
HOMEPAGE = "http://github.com/jdegges/image-analyzer/tree/master"
LICENSE = "MIT"

PR = "r0"

RDEPENDS = "FreeImage"
DEPENDS = "FreeImage"

SRC_URI = " \
    http://jdegges.googlepages.com/image-analyzer-1.0.0.tar.gz \
    file://cross_compile_without_sdl.patch;patch=1 \
    "

do_install () {
    install -d ${D}${bindir}

    install -m 0755 ${S}/ia ${D}${bindir}
}

FILES_${PN} = "${bindir}/ia"

